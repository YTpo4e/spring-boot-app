package com.ytpe4ko.springbootapp.dto;

import com.ytpe4ko.springbootapp.entities.Comment;
import lombok.Data;

@Data
public class CommentDto {
    private String text;
    private Float rating;

    public Comment toComment() {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setRating(rating);

        return comment;
    }
}
