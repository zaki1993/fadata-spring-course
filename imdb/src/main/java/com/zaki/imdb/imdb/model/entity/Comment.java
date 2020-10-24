package com.zaki.imdb.imdb.model.entity;

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
public class Comment {

    @Id
    @GeneratedValue(generator = "comments_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comments_sequence", sequenceName = "comments_sequence", allocationSize = 3)
    private Long id;

    @NotNull
    @NonNull
    @ManyToOne(targetEntity = Movie.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    private Movie movie;

    @NotNull
    @NonNull
    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    private User author;

    @NotNull
    @NonNull
    @Size(min = 10, max = 2048)
    private String content;

    @PastOrPresent
    private LocalDateTime created;

    @PastOrPresent
    private LocalDateTime modified;
}
