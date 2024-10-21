package ru.se.ifmo.tinder.dto.user_data;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import ru.se.ifmo.tinder.model.enums.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDataDto {

    @NotNull
    private Long id;

    @NotNull
    private Long ownerUserId;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 50, message = "Lastname name must be between 2 and 50 characters")
    private String lastname;

    @NotNull(message = "Sex is required")
    private Sex sex;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be a positive number")
    private Integer weight;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be a positive number")
    @Max(value = 300, message = "Height must be less than or equal to 300")
    private Integer height;

    @NotBlank(message = "Hair color is required")
    private String hairColor;

    @NotNull(message = "Locations is required")
    @Size(min = 1, message = "Specify at least one location")
    private List<Long> locations;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;
}
