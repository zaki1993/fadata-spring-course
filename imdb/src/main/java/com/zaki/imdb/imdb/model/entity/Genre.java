package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ToString.Exclude
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
}
