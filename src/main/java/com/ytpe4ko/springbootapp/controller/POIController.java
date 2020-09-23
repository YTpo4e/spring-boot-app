package com.ytpe4ko.springbootapp.controller;


import com.ytpe4ko.springbootapp.dto.CommentDto;
import com.ytpe4ko.springbootapp.dto.POIDto;
import com.ytpe4ko.springbootapp.entities.Comment;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class POIController {

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private PoiService poiService;

    @GetMapping(path = "/poi/{id}/comments")
    public List<Comment> getCommentByPOI(@PathVariable Long id) {
        return poiService.getCommentsByPOI(id);
    }

    @PostMapping(path = "/poi", consumes = "application/json")
    public ResponseEntity<POIDto> createPoi(@RequestBody POIDto poi) {
        poiService.savePOI(poi);
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    @DeleteMapping(path = "/poi/{id}")
    public ResponseEntity<POI> deletePOI(@PathVariable Long id) {
        try {
            poiRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PutMapping(path = "/poi/{id}")
    public ResponseEntity updatePOI(@PathVariable Long id, @RequestBody POIDto poiDto) {
        return poiService.updatePoi(poiDto, id);
    }

    @PostMapping("/poi/{id}/comments")
    public ResponseEntity addComment(@PathVariable Long id, @RequestBody CommentDto dto,
                                     @AuthenticationPrincipal User user) {
        return poiService.addComment(user, dto, id);
    }

    @DeleteMapping("/poi/{id}/comments{id1}")
    public ResponseEntity deleteComment(@PathVariable Long id, @PathVariable Long id1, @AuthenticationPrincipal User user){
        return poiService.deleteComment(id, id1);
    }
}
