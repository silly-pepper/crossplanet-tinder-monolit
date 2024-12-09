package ru.se.info.tinder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.se.info.tinder.dto.LocationDto;
import ru.se.info.tinder.dto.RequestLocationDto;
import ru.se.info.tinder.mapper.LocationMapper;
import ru.se.info.tinder.model.Location;
import ru.se.info.tinder.repository.LocationRepository;
import ru.se.info.tinder.service.exception.NoEntityWithSuchIdException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void testCreateLocation() {
        RequestLocationDto request = new RequestLocationDto("New City", "New description", 30.0);
        Location savedLocation = new Location(1L, "New City", "New description", 30.0);
        LocationDto expectedDto = new LocationDto(1L, "New City", "New description", 30.0);

        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(savedLocation));

        Mono<LocationDto> result = locationService.createLocation(request);

        StepVerifier.create(result)
                .expectNext(expectedDto)
                .verifyComplete();
    }

    @Test
    public void testUpdateLocationById_Found() {
        Long locationId = 1L;
        RequestLocationDto request = new RequestLocationDto("Updated City", "Updated description", 32.0);
        Location existingLocation = new Location(locationId, "Old City", "Old description", 30.0);
        Location updatedLocation = new Location(locationId, "Updated City", "Updated description", 32.0);
        LocationDto expectedDto = new LocationDto(locationId, "Updated City", "Updated description", 32.0);

        when(locationRepository.findById(locationId)).thenReturn(Mono.just(existingLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(Mono.just(updatedLocation));

        Mono<LocationDto> result = locationService.updateLocationById(locationId, request);

        StepVerifier.create(result)
                .expectNext(expectedDto)
                .verifyComplete();
    }

    @Test
    public void testGetAllLocations() {
        Location location1 = new Location(1L, "City1", "Description1", 30.0);
        Location location2 = new Location(2L, "City2", "Description2", 25.0);
        LocationDto locationDto1 = new LocationDto(1L, "City1", "Description1", 30.0);
        LocationDto locationDto2 = new LocationDto(2L, "City2", "Description2", 25.0);

        when(locationRepository.findAll()).thenReturn(Flux.just(location1, location2));

        Flux<LocationDto> result = locationService.getAllLocations();

        StepVerifier.create(result)
                .expectNext(locationDto1)
                .expectNext(locationDto2)
                .verifyComplete();
    }

    @Test
    public void testDeleteLocationById() {
        Long locationId = 1L;

        when(locationRepository.deleteById(locationId)).thenReturn(Mono.empty());

        Mono<Void> result = locationService.deleteLocationById(locationId);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void testGetLocationDtoById_Found() {
        Long locationId = 1L;
        Location location = new Location(locationId, "City", "Description", 30.0);
        LocationDto expectedDto = new LocationDto(locationId, "City", "Description", 30.0);

        when(locationRepository.findById(locationId)).thenReturn(Mono.just(location));

        Mono<LocationDto> result = locationService.getLocationDtoById(locationId);

        StepVerifier.create(result)
                .expectNext(expectedDto)
                .verifyComplete();
    }
}
