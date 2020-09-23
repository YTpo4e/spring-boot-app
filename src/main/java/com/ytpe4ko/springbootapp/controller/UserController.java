package com.ytpe4ko.springbootapp.controller;


import com.ytpe4ko.springbootapp.dto.AddPlaceDto;
import com.ytpe4ko.springbootapp.dto.RegistrationForm;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public User newUser(@RequestBody RegistrationForm form) {
        return userService.create(form);
    }

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("/user/{id}/place")
    public ResponseEntity<String> savePlace(@PathVariable Long id, @RequestBody AddPlaceDto addPlaceDto) {
        return userService.savePlace(id, addPlaceDto);
    }

    @GetMapping(path = "/user/{id}/place")
    public List<POI> getPOIByUser(@PathVariable Long id) {
        return userService.getPOIByUser(id);
    }

    @DeleteMapping("/user/{id}/place")
    public ResponseEntity<String> deletePlace(@PathVariable Long id, @RequestBody AddPlaceDto addPlaceDto) {
        return userService.deletePlace(id, addPlaceDto);
    }


}
