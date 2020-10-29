package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"approved"})
public class Comment {

    @Id
    @GeneratedValue(generator = "comments_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comments_sequence", sequenceName = "comments_sequence", allocationSize = 3)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie.class, optional = false)
    @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    @NonNull
    private Movie movie;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false, referencedColumnName = "ID")
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
