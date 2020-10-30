package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"approved", "movie"})
public class Comment {

    @Id
    @GeneratedValue(generator = "comments_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comments_sequence", sequenceName = "comments_sequence", allocationSize = 3)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie.class, optional = false)
    @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    @NonNull
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                content.equals(comment.content) &&
                created.equals(comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created);
    }
}
