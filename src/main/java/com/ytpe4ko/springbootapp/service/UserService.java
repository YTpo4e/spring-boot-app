package com.ytpe4ko.springbootapp.service;

import com.ytpe4ko.springbootapp.dto.RegistrationForm;
import com.ytpe4ko.springbootapp.dto.AddPlaceDto;
import com.ytpe4ko.springbootapp.entities.POI;
import com.ytpe4ko.springbootapp.entities.User;
import com.ytpe4ko.springbootapp.repositories.POIRepository;
import com.ytpe4ko.springbootapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private POIRepository poiRepositor;

    public User create(RegistrationForm form) {
        User user = form.toUser();
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }


    public ResponseEntity<String> savePlace(Long id, AddPlaceDto addPlaceDto) {
        try {
            User user = userRepository.getOne(id);
            POI poi = poiRepositor.findByName(addPlaceDto.getFavouritePlaces());
            user.addPoi(poi);
            userRepository.save(user);
            return new ResponseEntity<>("place add", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("place not found", HttpStatus.OK);
        }
    }

    public List<POI> getPOIByUser(Long id) {
        return userRepository.getPOIByUser(id);
    }

    public ResponseEntity<String> deletePlace(Long id, AddPlaceDto addPlaceDto) {
        try {
            POI poi = poiRepositor.findByName(addPlaceDto.getFavouritePlaces());
            Optional<User> user = userRepository.findById(id);
            user.get().removePoi(poi);
            userRepository.save(user.get());
            return new ResponseEntity<>("place delete", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("place not found", HttpStatus.OK);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }
}
