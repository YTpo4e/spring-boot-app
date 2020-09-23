package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.Comment;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByLogin(String login);

    @Query("select pois from User u where u.id = ?1")
    List<POI> getPOIByUser(Long id);
}
