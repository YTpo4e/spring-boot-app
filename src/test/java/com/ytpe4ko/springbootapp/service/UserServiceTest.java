package com.ytpe4ko.springbootapp.service;

import com.ytpe4ko.springbootapp.dto.AddPlaceDto;
import com.ytpe4ko.springbootapp.dto.RegistrationForm;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.repositories.UserRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    private final String LOGIN = "test";
    private final String EMAIL = "test@gmail.com";
    private final String PASSWORD = "1234acd";

    private final String POINAME = "museum1";
    private final String POIADDERESS = "eeeeee12";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private POIRepository poiRepository;

    @BeforeEach
    public  void inti() {
        POI poi = new POI();
        poi.setName(POINAME);
        poi.setAddress(POIADDERESS);
        poi.setRating(0f);

        poiRepository.save(poi);

        RegistrationForm form = new RegistrationForm();
        form.setEmail(EMAIL);
        form.setLogin(LOGIN);
        form.setPassword(PASSWORD);

        User user = userService.create(form);
    }

    @Test
    void create() {
        User user = userRepository.findByLogin(LOGIN);

        assertEquals(EMAIL, user.getEmail());
        assertEquals(LOGIN, user.getLogin());
        assertNotEquals(PASSWORD, user.getPassword());
        assertNotNull(user.getId());
    }

    @Test
    void savePlace() {
        User user = userRepository.findByLogin(LOGIN);
        AddPlaceDto addPlaceDto =new AddPlaceDto();
        addPlaceDto.setFavouritePlaces(POINAME);
        userService.savePlace(user.getId(), addPlaceDto);
        poi = poiRepository.findByName(POINAME);
        user = userRepository.findByLogin(LOGIN);

        assertThat(user.getPois().contains(poi));


    }


    @AfterEach
    public void close() {

        POI poi = poiRepository.findByName(POINAME);
        poiRepository.delete(poi);

        User user = userRepository.findByLogin(LOGIN);
        userRepository.delete(user);
    }

}