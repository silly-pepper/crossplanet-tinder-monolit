package ru.se.info.tinder.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import ru.se.info.tinder.dto.LocationDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@CircuitBreaker(name = "location-service-cb")
@ReactiveFeignClient("location-service")
public interface LocationClient {
    @GetMapping("/api/v1/locations/list")
    Flux<LocationDto> getLocationsByIds(@NotNull @RequestParam List<Long> locations,
                                        @RequestHeader("Authorization") String token);
}
