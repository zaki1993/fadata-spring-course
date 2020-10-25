package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"movies"})
public class Category {

    @Id
    @GeneratedValue(generator = "categories_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 128)
    private String name;

    @NonNull
    @NotNull
    @Size(min = 16, max = 1024)
    private String description;

    @OneToMany(targetEntity = Movie.class, mappedBy = "category", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id) &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
