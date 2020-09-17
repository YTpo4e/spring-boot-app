package com.ytpe4ko.springbootapp.service;

import com.ytpe4ko.springbootapp.dto.POIDto;
import com.ytpe4ko.springbootapp.entities.City;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.TypeOfPlace;
import com.ytpe4ko.springbootapp.repositories.CityRepository;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.repositories.TOPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoiService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TOPRepository topRepository;

    @Autowired
    private POIRepository poiRepository;


    public void savePOI(POIDto poiDto) {
        City city = cityRepository.getOne(poiDto.getCity());
        TypeOfPlace typeOfPlace = topRepository.getOne(poiDto.getTypeOfPlace());
        POI poi = new POI(poiDto.getName(), typeOfPlace, city, poiDto.getAddress(), poiDto.getRating(), null);
        city.addPOI(poi);
        typeOfPlace.addPOI(poi);
        cityRepository.save(city);
        topRepository.save(typeOfPlace);
        poiRepository.save(poi);
    }

}
