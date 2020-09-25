package com.ytpe4ko.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytpe4ko.springbootapp.dto.AddPlaceDto;
import com.ytpe4ko.springbootapp.dto.RegistrationForm;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userService))
                .build();
    }

    @Test
    void createUserWithRegistrationForm() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationForm form = new RegistrationForm();
        form.setLogin("test");
        form.setPassword("123456");
        form.setEmail("test@gmail.com");

        User user = form.toUser();

        Mockito.when(userService.create(form)).thenReturn(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(form)))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).create(form);
    }

    @Test
    void getUserById() throws Exception {
        User user = new User();
        user.setEmail("test@user.ru");
        user.setLogin("user");
        user.setPassword("1234");
        Optional<User> u = Optional.of(user);
        Mockito.when(userService.getUserById(1L)).thenReturn(u);

        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
    }

    @Test
    public void getUserNotFound() throws Exception {
        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isNotFound());

        Mockito.verify(userService, Mockito.times(1)).getUserById(1L);
    }

    @Test
    void savePlace() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        POI poi = new POI();
        poi.setRating(3f);
        poi.setAddress("street12");
        poi.setName("testPOI");

        RegistrationForm form = new RegistrationForm();
        form.setLogin("test");
        form.setPassword("123456");
        form.setEmail("test@gmail.com");

        User user = form.toUser();

        AddPlaceDto placeDto = new AddPlaceDto();
        placeDto.setFavouritePlaces("testPOI");

        Mockito.when(userService.savePlace(1L, placeDto)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mockMvc.perform(post("/user/{id}/place", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(placeDto)))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).savePlace(1L, placeDto);
    }

    @Test
    void getPOIByUser() throws Exception {
        POI poi = new POI();
        poi.setRating(3f);
        poi.setAddress("street12");
        poi.setName("testPOI");

        POI poi1 = new POI();
        poi1.setName("testPOI1");
        poi1.setRating(4f);
        poi1.setAddress("street1");
        List<POI> pois = new ArrayList<>(Arrays.asList(poi, poi1));
        Mockito.when(userService.getPOIByUser(1L)).thenReturn(pois);

        mockMvc.perform(get("/user/{id}/place", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("testPOI")))
                .andExpect(jsonPath("$.[1].name", is("testPOI1")));

        Mockito.verify(userService, Mockito.times(1)).getPOIByUser(1L);
    }

}