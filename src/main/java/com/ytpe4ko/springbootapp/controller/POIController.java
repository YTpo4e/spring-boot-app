package com.ytpe4ko.springbootapp.controller;


import com.ytpe4ko.springbootapp.dto.POIDto;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class POIController {

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private PoiService poiService;


    @PostMapping(path = "/poi", consumes = "application/json")
    public ResponseEntity<POIDto> createPoi(@RequestBody POIDto poi) {
        poiService.savePOI(poi);
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    @GetMapping(path = "/poi")
    public ArrayList<POI> getAllPoi() {
        return new ArrayList<>(poiRepository.findAll());
    }

    @GetMapping(path = "/poi/{id}")
    public ResponseEntity<POI> getPOIById(@PathVariable Long id) {
        try {
            Optional<POI> poi = poiRepository.findById(id);
            return new ResponseEntity<>(poi.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
