package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"comments"})
public class Movie {

    @Id
    @GeneratedValue(generator = "movies_sequence", strategy = GenerationType.TABLE)
    @SequenceGenerator(name = "movies_sequence", sequenceName = "movies_sequence", allocationSize = 3)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movies_genres",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie", targetEntity = Comment.class)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @NotNull
    @NonNull
    @Size(min = 8, max = 128)
    private String name;

    @NotNull
    @NonNull
    @Size(min = 32, max = 2048)
    private String description;

    @NotNull
    @NonNull
    @URL
    private String trailerURL;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    @PositiveOrZero
    private Double rating = 0d;
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) &&
                name.equals(movie.name) &&
                description.equals(movie.description) &&
                trailerURL.equals(movie.trailerURL) &&
                created.equals(movie.created) &&
                modified.equals(movie.modified) &&
                rating.equals(movie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, trailerURL, created, modified, rating);
    }*/
}
