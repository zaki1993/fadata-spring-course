package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"movies"})
public class Genre {

    @Id
    @GeneratedValue(generator = "genres_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "genres_sequence", sequenceName = "genres_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 128)
    private String name;

    @NonNull
    @NotNull
    @Size(min = 16, max = 1024)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id.equals(genre.id) &&
                name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
