package ru.se.ifmo.tinder.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.se.ifmo.tinder.dto.ShootingDto;
import ru.se.ifmo.tinder.model.UserData;
import ru.se.ifmo.tinder.model.UserSpacesuitData;
import ru.se.ifmo.tinder.model.enums.Status;
import ru.se.ifmo.tinder.service.UserDataService;
import ru.se.ifmo.tinder.service.UserSpacesuitDataService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@SecurityRequirement(name = "basicAuth")
public class InformationController {
    private final UserDataService userDataService;
    private final UserSpacesuitDataService userSpacesuitDataService;


    @PostMapping("post")
    public ResponseEntity<String> post(){
        return ResponseEntity.ok("sjdh");
    }
    @PostMapping("get")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("sjdh");
    }


//    @PostMapping("getMars")
//    public ResponseEntity<List<UserData>> getMars(){
//        List<UserData> list = userDataService.getMars();
//        return ResponseEntity.ok(list);
//    }
//
//    @PostMapping("getEarth")
//    public ResponseEntity<List<UserData>> getEarth(){
//        List<UserData> list = userDataService.getEarth();
//        return ResponseEntity.ok(list);
//    }

    @PostMapping("getAllUserData")
    public  ResponseEntity<List<UserData>> getAllUserData(Principal principal){
        List<UserData> list = userDataService.getAllUserData(principal);
        return ResponseEntity.ok(list);
    }

    @PostMapping("getCurrUserData")
    public  ResponseEntity<List<UserData>> getCurrUserData(Principal principal){
        List<UserData> list = userDataService.getCurrUserData(principal);
        return ResponseEntity.ok(list);
    }

    @PostMapping("getCurrUserSpacesuitData")
    public ResponseEntity<List<Status>> getCurrUserSpacesuitData(Principal principal){
        List<UserSpacesuitData> list = userSpacesuitDataService.getCurrUserSpacesuitData(principal);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list.stream().map(UserSpacesuitData::getStatus).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(List.of());
    }
}
