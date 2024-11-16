package ru.se.info.tinder.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateUserRequestDto {

    @NotNull(message = "User spacesuit data ID must not be null")
    @Positive(message = "User spacesuit data ID must be a positive number")
    private Integer userSpacesuitDataId;
}

