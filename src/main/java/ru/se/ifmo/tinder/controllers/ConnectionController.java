package ru.se.ifmo.tinder.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.se.ifmo.tinder.dto.UserConnectionsDto;
import ru.se.ifmo.tinder.model.User;
import ru.se.ifmo.tinder.model.UserData;
import ru.se.ifmo.tinder.service.UserService;

import java.security.Principal;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.intValue;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/connection-management/connections")
@SecurityRequirement(name = "basicAuth")
public class ConnectionController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Integer> connectUsers(@RequestBody UserConnectionsDto userConnectionsDto, Principal principal) {
        Integer id = userService.addConnection(principal, (intValue(userConnectionsDto.getUser_data_id_2())));
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<List<User>> getConnections(Principal principal) {
        List<User> list = userService.getConnections(principal);
        return ResponseEntity.ok(list);
    }
}
