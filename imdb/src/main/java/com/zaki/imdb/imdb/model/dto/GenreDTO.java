package com.zaki.imdb.imdb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zaki.imdb.imdb.model.entity.Movie;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"movies"})
public class GenreDTO {
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 128)
    private String name;

    @NonNull
    @NotNull
    @Size(min = 16, max = 1024)
    private String description;

    private Set<Movie> movies = new HashSet<>();
}
