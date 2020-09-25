package com.ytpe4ko.springbootapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(0)
    @Max(5)
    private Float rating;

    @Size(min = 1, max = 10000)
    private String text;


    private LocalDateTime localDateTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poi_id", nullable = false)
    @JsonIgnore
    private POI poi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(rating, comment.rating) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(localDateTime, comment.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, text, localDateTime);
    }
}
