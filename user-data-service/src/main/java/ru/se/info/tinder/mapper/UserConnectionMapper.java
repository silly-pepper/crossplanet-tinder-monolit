package ru.se.info.tinder.mapper;

import ru.se.info.tinder.dto.UserConnectionDto;
import ru.se.info.tinder.model.UserConnection;

public class UserConnectionMapper {
    public static UserConnectionDto toDtoUserConnection(UserConnection userConnection) {
        return UserConnectionDto.builder()
                .userDto1(UserMapper.toDtoUser(userConnection.getUser1()))
                .userDto2(UserMapper.toDtoUser(userConnection.getUser2()))
                .matchDate(userConnection.getMatchDate())
                .id(userConnection.getId())
                .build();
    }
}
