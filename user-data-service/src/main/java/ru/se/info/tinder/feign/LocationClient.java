package ru.se.info.tinder.feign;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.se.info.tinder.dto.LocationDto;

import java.util.List;

@FeignClient("location-service")
public interface LocationClient {
    @GetMapping("/api/v1/locations/list")
    ResponseEntity<List<LocationDto>> getLocationsByIds(@NotNull @RequestParam List<Long> locations,
                                                        @RequestHeader("Authorization") String token);
}