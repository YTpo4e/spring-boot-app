package com.ytpe4ko.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytpe4ko.springbootapp.dto.POIDto;
import com.ytpe4ko.springbootapp.entities.Comment;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.service.PoiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class POIControllerTest {
    @MockBean
    private PoiService poiService;
    @MockBean
    private POIRepository poiRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new POIController(poiRepository, poiService))
                .build();
    }

    @Test
    void getCommentByPOI() throws Exception {
        Comment c1 = new Comment();
        c1.setId(1L);
        c1.setText("good");
        c1.setRating(4f);

        Comment c2 = new Comment();
        c2.setId(2L);
        c2.setText("very good");
        c2.setRating(4.4f);


        List<Comment> comments = new ArrayList<>(Arrays.asList(c1, c2));

        Mockito.when(poiService.getCommentsByPOI(1L)).thenReturn(comments);

        mockMvc.perform(get("/poi/{id}/comments", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].text", is("good")))
                .andExpect(jsonPath("$.[0].rating", is(4.0)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].text", is("very good")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].rating", is(4.4)));


        Mockito.verify(poiService, Mockito.times(1)).getCommentsByPOI(1L);
    }

    @Test
    void createPoi() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        POIDto poidto = new POIDto();
        poidto.setName("testPoi");
        poidto.setAddress("street11");
        poidto.setRating(4f);

        POI poi = new POI();
        poi.setRating(poidto.getRating());
        poi.setName(poidto.getName());
        poi.setRating(poidto.getRating());

        Mockito.when(poiService.savePOI(poidto)).thenReturn(poi);

        mockMvc.perform(post("/poi").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(poidto)))
                .andExpect(status().isOk());

        Mockito.verify(poiService, Mockito.times(1)).savePOI(poidto);
    }

    @Test
    void deletePOI() throws Exception {
        mockMvc.perform(delete("/poi/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(poiRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getAllPoi() throws Exception {
        POI poi = new POI();
        poi.setName("poi1");
        poi.setId(0L);
        poi.setAddress("street12");
        poi.setRating(3f);


        POI poi1 = new POI();
        poi1.setName("poi2");
        poi1.setId(1L);
        poi1.setAddress("street2");
        poi1.setRating(4f);

        List<POI> pois = new ArrayList<>(Arrays.asList(poi, poi1));

        Mockito.when(poiRepository.findAll()).thenReturn(pois);

        mockMvc.perform(get("/poi"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("poi1")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].name", is("poi2")));


        Mockito.verify(poiRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getPOIById() throws Exception {
        POI poi = new POI();
        poi.setName("test");

        Optional<POI> poi1 = Optional.of(poi);
        Mockito.when(poiRepository.findById(1L)).thenReturn(poi1);

        mockMvc.perform(get("/poi/{id}", 1L))
                .andExpect(status().isOk());

        Mockito.verify(poiRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void updatePOI() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        POIDto poiDto = new POIDto();
        poiDto.setName("uptest");
        poiDto.setRating(4f);
        poiDto.setAddress("street12");

        POI poiUpdate = new POI();
        poiDto.setName("uptest");
        poiDto.setRating(4f);
        poiDto.setAddress("street12");

        Mockito.when(poiService.savePOI(poiDto)).thenReturn(poiUpdate);

        mockMvc.perform(put("/poi/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(poiDto)))
                .andExpect(status().isOk());

        Mockito.verify(poiService, Mockito.times(1)).updatePoi(ArgumentMatchers.any(User.class)
                , ArgumentMatchers.eq(poiDto), ArgumentMatchers.eq(1L));

    }

    @Test
    void addComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        POI poi = new POI();
        poi.setRating(4f);
        poi.setName("poi");
        poi.setId(1L);

        User user = new User();
        user.setLogin("user");
        user.setPassword("1234");
        user.setEmail("test@gmail.com");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setRating(4f);
        comment.setText("good");

        Mockito.when(poiService.addComment(user, comment, 1L)).thenReturn(new ResponseEntity(HttpStatus.OK));

        mockMvc.perform(post("/poi/{id}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(comment))
                .content(mapper.writeValueAsBytes(user)))
                .andExpect(status().isOk());

    }
}