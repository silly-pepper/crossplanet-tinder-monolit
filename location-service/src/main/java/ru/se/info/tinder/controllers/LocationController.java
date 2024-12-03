package ru.se.info.tinder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.se.info.tinder.dto.LocationDto;
import ru.se.info.tinder.dto.RequestLocationDto;
import ru.se.info.tinder.service.LocationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("new")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Mono<LocationDto> createLocation(@Valid @RequestBody RequestLocationDto dto) {
        return locationService.createLocation(dto);
    }

    @PutMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Mono<LocationDto> updateLocation(@NotNull @PathVariable Long locationId,
                                            @Valid @RequestBody RequestLocationDto dto) {
        return locationService.updateLocationById(locationId, dto);
    }

    @DeleteMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Mono<Void> deleteLocationById(@NotNull @PathVariable Long locationId) {
        return locationService.deleteLocationById(locationId);
    }

    @GetMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Mono<LocationDto> getLocationById(@NotNull @PathVariable Long locationId) {
        return locationService.getLocationDtoById(locationId);
    }

    @GetMapping("list")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Flux<LocationDto> getLocationsByIds(@NotNull @RequestParam List<Long> locationIds) {
        return locationService.getLocationsListByIds(Flux.fromIterable(locationIds));
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public Flux<LocationDto> getAllLocations(@RequestParam @Min(0) int page,
                                             @RequestParam @Min(1) @Max(50) int size) {
        return locationService.getAllLocations()
                .skip((long) page * size)  // Пропускаем элементы для заданной страницы
                .take(size);              // Ограничиваем количество возвращаемых элементов
    }
}
