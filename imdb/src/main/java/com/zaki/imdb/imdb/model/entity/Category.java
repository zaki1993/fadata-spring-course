package com.zaki.imdb.imdb.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue(generator = "categories_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @Size(min=2, max=128)
    private String name;

    @NonNull
    @NotNull
    @Size(min=16, max=1024)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Movie> movies;
}
