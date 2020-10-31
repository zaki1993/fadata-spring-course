package com.zaki.imdb.imdb.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(generator = "rates_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "rates_sequence",
            sequenceName = "rates_sequence",
            allocationSize = 3
    )
    private Long id;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    @NonNull
    private User user;

    @ManyToOne(targetEntity = Movie.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    @NonNull
    private Movie movie;

    @NonNull
    @NotNull
    @Positive
    @Min(value = 1)
    @Max(value = 10)
    private Double rating;
}
