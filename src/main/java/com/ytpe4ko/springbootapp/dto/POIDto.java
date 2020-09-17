package com.ytpe4ko.springbootapp.dto;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class POIDto {

    private String name;
    private Long typeOfPlace;
    private Long city;
    private String address;
    private Float rating;
}
