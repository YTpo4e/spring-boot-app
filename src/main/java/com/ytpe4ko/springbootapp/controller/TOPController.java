package com.ytpe4ko.springbootapp.controller;

import com.ytpe4ko.springbootapp.entities.City;
import com.ytpe4ko.springbootapp.entities.TypeOfPlace;
import com.ytpe4ko.springbootapp.repositories.CityRepository;
import com.ytpe4ko.springbootapp.repositories.TOPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class TOPController {

    @Autowired
    private TOPRepository topRepository;

    @GetMapping(path = "/typeofplace")
    public ArrayList<TypeOfPlace> getAllCities() {
        return new ArrayList<>(topRepository.findAll());
    }

    @PostMapping(path = "/typeofplace", consumes = "application/json")
    public ResponseEntity<TypeOfPlace> createCity(@RequestBody TypeOfPlace typeOfPlace) {
        topRepository.save(typeOfPlace);
        return new ResponseEntity<>(typeOfPlace, HttpStatus.OK);
    }

    @GetMapping(path = "/typeofplace/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") Long id) {
        try {
            Optional<TypeOfPlace> typeOfPlace = topRepository.findById(id);
            return new ResponseEntity<>(typeOfPlace.get(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }
}
