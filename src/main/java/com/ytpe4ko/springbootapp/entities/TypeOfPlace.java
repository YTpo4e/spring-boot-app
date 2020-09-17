package com.ytpe4ko.springbootapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_of_place")
public class TypeOfPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "name")
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "typeOfPlace")
    private Set<POI> pois = new HashSet<>();

    public void addPOI(POI poi) {
        pois.add(poi);
    }
}
