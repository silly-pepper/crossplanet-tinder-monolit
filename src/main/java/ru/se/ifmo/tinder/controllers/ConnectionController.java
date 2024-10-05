package ru.se.ifmo.tinder.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.se.ifmo.tinder.dto.UserConnectionsDto;
import ru.se.ifmo.tinder.model.UserData;
import ru.se.ifmo.tinder.service.UserDataService;
import ru.se.ifmo.tinder.service.UserService;

import java.security.Principal;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.intValue;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/connection")
@SecurityRequirement(name = "basicAuth")
public class ConnectionController {
    private final UserDataService userDataService;
    private final UserService userService;

    @PostMapping("connectUsers")
    public ResponseEntity<Integer> connectUsers(@RequestBody UserConnectionsDto userConnectionsDto, Principal principal){

        Integer id = userService.addConnection(principal, (intValue(userConnectionsDto.getUser_data_id_2())));
        return ResponseEntity.ok(id);
    }

    @PostMapping("getConnections")
    public ResponseEntity<List<UserData>> getConnections(Principal principal){
        List<UserData> list = userService.getConnections(principal);
        return ResponseEntity.ok(list);
    }


}
