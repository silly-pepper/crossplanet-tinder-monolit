package ru.se.info.tinder.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.se.info.tinder.dto.FabricTextureDto;
@CircuitBreaker(name = "fabric-texture-cb")
@FeignClient("fabric-texture-service")
public interface FabricTextureClient {
    @GetMapping("/api/v1/fabric-textures/{fabricTextureId}")
    ResponseEntity<FabricTextureDto> getFabricTextureById(@NotNull @PathVariable Long fabricTextureId,
                                                          @RequestHeader("Authorization") String token);
}
