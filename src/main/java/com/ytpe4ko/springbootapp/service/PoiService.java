package com.ytpe4ko.springbootapp.service;

import com.ytpe4ko.springbootapp.dto.CommentDto;
import com.ytpe4ko.springbootapp.dto.POIDto;
import com.ytpe4ko.springbootapp.entities.*;
import com.ytpe4ko.springbootapp.repositories.CityRepository;
import com.ytpe4ko.springbootapp.repositories.CommentRepository;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.repositories.TOPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TOPRepository topRepository;

    @Autowired
    private POIRepository poiRepository;

    public ResponseEntity addComment(User user, CommentDto dto, Long poiId) {
        try {
            POI poi = poiRepository.findById(poiId).get();
            float rating = (dto.getRating() + poi.getRating()) / (commentRepository.countByPoiId(poiId) + 1);
            Comment comment = dto.toComment();
            comment.setUser(user);
            comment.setPoi(poi);
            poi.setRating(rating);
            poiRepository.save(poi);
            commentRepository.save(comment);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteComment(Long poiId, Long commentId) {
        try {
            POI poi = poiRepository.findById(poiId).get();
            Long countComment = commentRepository.countByPoiId(poiId);
            Comment comment = commentRepository.findById(commentId).get();
            float rating;
            if (countComment != 1) {
                rating = (poi.getRating() * countComment - comment.getRating()) / (countComment - 1);
            } else {
                rating = 0f;
            }
            poi.setRating(rating);
            poiRepository.save(poi);
            commentRepository.deleteById(comment.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public List<Comment> getCommentsByPOI(Long id) {
        return poiRepository.getCommentsByPOI(id);
    }

    public void savePOI(POIDto poiDto) {
        City city = cityRepository.findByName(poiDto.getCity());
        TypeOfPlace typeOfPlace = topRepository.findByName(poiDto.getTypeOfPlace());
        POI poi = new POI(poiDto.getName(), typeOfPlace, city, poiDto.getAddress(), poiDto.getRating(), null);
        poiRepository.save(poi);
    }

    public ResponseEntity updatePoi(POIDto poiDto, Long id) {
        City city = cityRepository.findByName(poiDto.getCity());
        TypeOfPlace typeOfPlace = topRepository.findByName(poiDto.getTypeOfPlace());
        try {
            POI poi = poiRepository.getOne(id);
            poi.setCity(city);
            poi.setTypeOfPlace(typeOfPlace);
            poi.setRating(poiDto.getRating());
            poi.setName(poiDto.getName());
            poi.setAddress(poiDto.getAddress());
            poiRepository.save(poi);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }
}
