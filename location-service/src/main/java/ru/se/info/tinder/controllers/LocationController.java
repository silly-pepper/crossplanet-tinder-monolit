package ru.se.info.tinder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody RequestLocationDto dto) {
        LocationDto locationDto = locationService.createLocation(dto);
        return new ResponseEntity<>(locationDto, HttpStatus.CREATED);
    }

    @PutMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<LocationDto> updateLocation(@NotNull @PathVariable Long locationId,
                                                      @Valid @RequestBody RequestLocationDto dto) {
        LocationDto locationDto = locationService.updateLocationById(locationId, dto);
        return ResponseEntity.ok(locationDto);
    }

    @DeleteMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> deleteLocationById(@NotNull @PathVariable Long locationId) {
        locationService.deleteLocationById(locationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{locationId}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<LocationDto> getLocationById(@NotNull @PathVariable Long locationId) {
        LocationDto locationDto = locationService.getLocationDtoById(locationId);
        return ResponseEntity.ok(locationDto);
    }

    @GetMapping("list")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<LocationDto>> getLocationsByIds(@NotNull @RequestParam List<Long> locations) {
        List<LocationDto> locationDtoList = locationService.getLocationsListByIds(locations);
        return ResponseEntity.ok(locationDtoList);
    }

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Page<LocationDto>> getAllLocations(@RequestParam int page,
                                                             @RequestParam @Min(1) @Max(50) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LocationDto> locationDtoPage = locationService.getAllLocations(pageable);
        return ResponseEntity.ok(locationDtoPage);
    }
}
