package com.ytpe4ko.springbootapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city")
    private Long id;

    @Column(unique = true, nullable = false, name = "name")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private Set<POI> pois = new HashSet<>();

    public void addPOI(POI poi) {
        pois.add(poi);
    }


}
