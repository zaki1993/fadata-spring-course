package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"comments", "rates"})
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", targetEntity = Comment.class)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie", targetEntity = Rate.class)
    private List<Rate> rates = new ArrayList<>();

    @JsonProperty(access = READ_ONLY)
    private Double rating;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) &&
                name.equals(movie.name) &&
                description.equals(movie.description) &&
                created.equals(movie.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }

    public Double getRating() {
        if (rates == null || rates.isEmpty()) {
            return 0d;
        }
        return rates.stream().map(Rate::getRating).reduce(0d, Double::sum) / rates.size();
    }
}
