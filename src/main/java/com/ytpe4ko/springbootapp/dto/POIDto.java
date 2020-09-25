package com.ytpe4ko.springbootapp.dto;

import com.ytpe4ko.springbootapp.entities.POI;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class POIDto {

    private String name;
    private String typeOfPlace;
    private String city;
    private String address;
    private Float rating;
}
