package com.zaki.imdb.imdb.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(generator = "movies_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movies_sequence", sequenceName = "movies_sequence", allocationSize = 3)
    private Long id;

    @NotNull
    @NonNull
    @ManyToOne(targetEntity = Category.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    private Category category;

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
    private Double rating;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Comment> comments;
}
