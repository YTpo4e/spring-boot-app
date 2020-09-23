package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.Comment;
import com.ytpe4ko.springbootapp.entities.POI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface POIRepository extends JpaRepository<POI, Long> {
    POI findByName(String name);

    POI deleteByName(String name);

    void deleteById(Long id);


    @Query("select comments from POI p where p.id = ?1")
    List<Comment> getCommentsByPOI(Long id);
}
