package ru.se.info.tinder.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import ru.se.info.tinder.dto.LocationDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@ReactiveFeignClient("location-service")
public interface LocationClient {
    @GetMapping("/api/v1/locations/list")
    ResponseEntity<List<LocationDto>> getLocationsByIds(@NotNull @RequestParam List<Long> locations,
                                                        @RequestHeader("Authorization") String token);
}
