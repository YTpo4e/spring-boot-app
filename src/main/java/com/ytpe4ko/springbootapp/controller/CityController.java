package com.ytpe4ko.springbootapp.controller;

import com.ytpe4ko.springbootapp.entities.City;
import com.ytpe4ko.springbootapp.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping(path = "/cities")
    public ArrayList<City> getAllCities() {
        return new ArrayList<>(cityRepository.findAll());
    }

    @PostMapping(path = "/cities", consumes = "application/json")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        cityRepository.save(city);
        return new ResponseEntity<City>(city, HttpStatus.OK);
    }

    @GetMapping(path = "/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable("id") Long id) {
        try {
            City city = cityRepository.getOne(id);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
