package com.ytpe4ko.springbootapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

    @OneToMany(mappedBy = "poi", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "pois", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users;

    public POI(String name, TypeOfPlace typeOfPlace, City city, String address, Float rating, User user) {
        this.name = name;
        this.typeOfPlace = typeOfPlace;
        this.city = city;
        this.address = address;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        POI poi = (POI) o;
        return id.equals(poi.id) &&
                name.equals(poi.name) &&
                Objects.equals(typeOfPlace, poi.typeOfPlace) &&
                Objects.equals(city, poi.city) &&
                address.equals(poi.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeOfPlace, city, address);
    }


}
