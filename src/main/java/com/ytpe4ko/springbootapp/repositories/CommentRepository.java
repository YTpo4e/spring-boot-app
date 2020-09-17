package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
