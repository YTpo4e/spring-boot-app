package com.ytpe4ko.springbootapp.controller;


import com.ytpe4ko.springbootapp.dto.AddPlaceDto;
import com.ytpe4ko.springbootapp.dto.RegistrationForm;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User newUser(@RequestBody RegistrationForm form) {
        return userService.create(form);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user/{id}/place")
    public ResponseEntity<User> savePlace(@PathVariable Long id, @RequestBody AddPlaceDto addPlaceDto) {
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
