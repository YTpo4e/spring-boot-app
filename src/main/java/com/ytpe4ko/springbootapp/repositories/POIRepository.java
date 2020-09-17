package com.ytpe4ko.springbootapp.repositories;

import com.ytpe4ko.springbootapp.entities.POI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POIRepository extends JpaRepository<POI, Long> {
    POI findByName(String name);
}
