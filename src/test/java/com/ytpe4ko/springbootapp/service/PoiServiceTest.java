package com.ytpe4ko.springbootapp.service;

import com.ytpe4ko.springbootapp.dto.CommentDto;
import com.ytpe4ko.springbootapp.entities.Comment;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PoiServiceTest {

    @Autowired
    private PoiService poiService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private POIRepository poiRepository;

    private static Long USERID = 156L;
    private static Long POIID = 1L;


    @Test
    void addComment() {
        User user = userRepository.getOne(USERID);
        CommentDto commentDto = new CommentDto();
        commentDto.setRating(4f);
        commentDto.setText("good");
        poiService.addComment(user, commentDto, POIID);

        List<Comment> comments = poiService.getCommentsByPOI(POIID);
        assertEquals(comments.size(), 1);
        CommentDto commentDto1 = new CommentDto();
        commentDto.setRating(4.4f);
        commentDto.setText("good");
        poiService.addComment(user, commentDto, POIID);
        comments = poiService.getCommentsByPOI(POIID);
        assertEquals(comments.size(), 2);

        Optional<POI> poi1 = poiRepository.findById(POIID);
        assertEquals(poi1.get().getRating(), 4.2f);

    }


    @Test
    void deleteComment() {
        List<Comment> comments = poiService.getCommentsByPOI(POIID);
        Long commentId1 = comments.get(0).getId();
        Long commentId2 = comments.get(1).getId();

        poiService.deleteComment(POIID, commentId1);
        poiService.deleteComment(POIID, commentId2);

        POI poi = poiRepository.findById(POIID).get();

        assertEquals(poi.getRating(), 0);


    }
}