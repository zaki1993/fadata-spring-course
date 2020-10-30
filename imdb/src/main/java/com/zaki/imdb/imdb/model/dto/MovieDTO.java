package com.zaki.imdb.imdb.model.dto;

import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.model.entity.Genre;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MovieDTO {
    private Long id;

    private Set<Genre> genres = new HashSet<>();

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
}
