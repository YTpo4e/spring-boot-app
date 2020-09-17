package com.ytpe4ko.springbootapp.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poi")
public class POI {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "poi_id")
    private Long id;

    @Column(unique = true, nullable = false, name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_of_place_id")
    private TypeOfPlace typeOfPlace;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(nullable = false, name = "address")
    private String address;

    @Min(0)
    @Max(5)
    @Column(name = "rating")
    private Float rating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "favourite_places",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users;

    public POI(String name, TypeOfPlace typeOfPlace, City city, String address, Float rating, User user) {
        this.name = name;
        this.typeOfPlace = typeOfPlace;
        this.city = city;
        this.address = address;
        this.rating = rating;
    }
}
