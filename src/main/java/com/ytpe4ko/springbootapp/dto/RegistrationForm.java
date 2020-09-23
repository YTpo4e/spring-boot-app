package com.ytpe4ko.springbootapp.dto;


import com.ytpe4ko.springbootapp.entities.Role;
import com.ytpe4ko.springbootapp.entities.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;

@Data
public class RegistrationForm {

    private String login;
    private String password;
    public String email;


    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setRoles(Collections.singleton(Role.USER));
        return user;
    }
}
