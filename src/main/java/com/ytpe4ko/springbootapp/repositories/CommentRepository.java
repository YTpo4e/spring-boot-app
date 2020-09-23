package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPoiId(Long id);
}
