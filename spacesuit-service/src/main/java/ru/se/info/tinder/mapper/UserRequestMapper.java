package ru.se.info.tinder.mapper;

import ru.se.info.tinder.dto.UserRequestDto;
import ru.se.info.tinder.model.UserRequest;

public class UserRequestMapper {

    public static UserRequestDto toUserRequestDto(UserRequest userRequest) {
        return UserRequestDto.builder()
                .userSpacesuitData(SpacesuitDataMapper.toSpacesuitDataDto(userRequest.getSpacesuitData()))
                .userRequestId(userRequest.getUserRequestId())
                .status(userRequest.getStatus())
                .createdAt(userRequest.getCreatedAt())
                .updatedAt(userRequest.getUpdatedAt())
                .build();
    }
}
