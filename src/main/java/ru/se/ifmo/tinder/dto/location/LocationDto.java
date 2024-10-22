package ru.se.ifmo.tinder.dto.location;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.se.ifmo.tinder.model.UserData;

import java.util.Set;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LocationDto {
    @Positive
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Double temperature;
}
