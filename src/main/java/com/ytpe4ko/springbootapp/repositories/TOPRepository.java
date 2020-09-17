package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.TypeOfPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TOPRepository extends JpaRepository<TypeOfPlace, Long> {
    TypeOfPlace findByName(String name);
}
