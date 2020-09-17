package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByLogin(String login);
}
