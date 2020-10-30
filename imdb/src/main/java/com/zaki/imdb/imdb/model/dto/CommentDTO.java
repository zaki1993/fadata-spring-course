package com.zaki.imdb.imdb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.model.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"approved", "movie"})
public class CommentDTO {
    private Long id;

    @NonNull
    private User author;

    @NotNull
    @NonNull
    @Size(min = 10, max = 2048)
    private String content;

    private boolean approved = false;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}
