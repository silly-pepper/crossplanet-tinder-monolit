package ru.se.info.tinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.se.info.tinder.dto.SpacesuitRequestMessage;
import ru.se.info.tinder.dto.UserRequestDto;
import ru.se.info.tinder.kafka.SpacesuitRequestProducer;
import ru.se.info.tinder.mapper.UserRequestMapper;
import ru.se.info.tinder.model.UserRequest;
import ru.se.info.tinder.model.SpacesuitData;
import ru.se.info.tinder.model.enums.SearchStatus;
import ru.se.info.tinder.model.enums.RequestStatus;
import ru.se.info.tinder.model.enums.UpdateRequestStatus;
import ru.se.info.tinder.repository.UserRequestRepository;
import ru.se.info.tinder.service.exception.NoEntityWithSuchIdException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserRequestService {
    private final UserRequestRepository userRequestRepository;
    private final SpacesuitRequestProducer spacesuitRequestProducer;

    public Flux<UserRequestDto> getUsersRequestsByStatus(SearchStatus status) {
        Flux<UserRequest> userRequestPage = switch (status) {
            case ALL -> Flux.fromStream(() -> userRequestRepository.findAll().stream());
            case NEW -> Flux.fromStream(() -> userRequestRepository.findNew().stream());
            case DECLINED -> Flux.fromStream(() -> userRequestRepository.findDeclined().stream());
            case READY -> Flux.fromStream(() -> userRequestRepository.findReady().stream());
            case IN_PROGRESS -> Flux.fromStream(() -> userRequestRepository.findInProgress().stream());
        };
        return userRequestPage.map(UserRequestMapper::toUserRequestDto);
    }

    public Mono<UserRequestDto> updateUserRequestStatus(Long userRequestId, UpdateRequestStatus status) {
        return Mono.fromCallable(
                () -> userRequestRepository.findById(userRequestId)
                        .orElseThrow(() -> new NoEntityWithSuchIdException("User request", userRequestId))
        ).flatMap(
                (userRequest) -> {
                    switch (status) {
                        case IN_PROGRESS -> {
                            if (userRequest.getStatus() != RequestStatus.NEW) {
                                return Mono.error(new IllegalStateException("Incorrect status for request"));
                            }
                        }
                        case READY, DECLINED -> {
                            if (userRequest.getStatus() != RequestStatus.IN_PROGRESS) {
                                return Mono.error(new IllegalStateException("Incorrect status for request"));
                            }
                        }
                    }
                    return updateStatus(userRequest, RequestStatus.valueOf(status.name()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .doOnSuccess(
                                    (userRequestDto) -> {
                                        SpacesuitRequestMessage message = UserRequestMapper.toSpacesuitRequestMsg(userRequestDto);
                                        Mono.fromRunnable(
                                                        () -> spacesuitRequestProducer.sendMessageToSpacesuitRequestChangedTopic(message)
                                                ).subscribeOn(Schedulers.boundedElastic())
                                                .onErrorContinue(
                                                        (error, n) -> log.error("Failed to send message to Kafka: ${error.message}")
                                                ).subscribe();
                                    }
                            );
                }
        );
    }

    private Mono<UserRequestDto> updateStatus(UserRequest userRequest, RequestStatus status) {
        userRequest.setStatus(status);
        userRequest.setUpdatedAt(LocalDateTime.now());

        return Mono.fromCallable(
                () -> userRequestRepository.save(userRequest)
        ).map(UserRequestMapper::toUserRequestDto);
    }

    public Mono<UserRequest> createUserRequest(SpacesuitData spacesuitData) {
        return Mono.fromCallable(
                () -> userRequestRepository.save(UserRequest.builder()
                        .spacesuitData(spacesuitData)
                        .status(RequestStatus.NEW)
                        .createdAt(LocalDateTime.now())
                        .build())
        );
    }
}
