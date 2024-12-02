package ru.se.info.tinder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.se.info.tinder.dto.UserRequestDto;
import ru.se.info.tinder.model.enums.SearchStatus;
import ru.se.info.tinder.model.enums.UpdateRequestStatus;
import ru.se.info.tinder.service.UserRequestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-requests")
@Slf4j
public class UserRequestController {
    private final UserRequestService userRequestService;

    @PatchMapping("{userRequestId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateUserRequestStatus(@NotNull @RequestParam UpdateRequestStatus status,
                                                     @NotNull @PathVariable Long userRequestId) {
        return ResponseEntity.ok(userRequestService.updateUserRequestStatus(userRequestId, status));
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Page<UserRequestDto>> getUsersRequests(@NotNull @RequestParam SearchStatus status,
                                                                 @NotNull @RequestParam int page,
                                                                 @NotNull @RequestParam @Min(1) @Max(50) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserRequestDto> requestPage = userRequestService.getUsersRequestsByStatus(status, pageable);

        return ResponseEntity.ok().body(requestPage);
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<?> handleIllegalStateExceptions(RuntimeException ex) {
        log.error("Incorrect request: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Incorrect request: " + ex.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleIncorrectStatusExceptions() {
        log.error("Incorrect request param 'status'");
        return ResponseEntity.badRequest().body("Incorrect request param 'status'");
    }
}
