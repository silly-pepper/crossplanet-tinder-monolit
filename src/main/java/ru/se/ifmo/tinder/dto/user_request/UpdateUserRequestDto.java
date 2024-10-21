package ru.se.ifmo.tinder.dto.user_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    @NotNull(message = "User spacesuit data ID must not be null")
    @Positive(message = "User spacesuit data ID must be a positive number")
    private Integer user_spacesuit_data_id;
}

