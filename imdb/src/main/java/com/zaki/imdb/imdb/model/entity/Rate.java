package com.zaki.imdb.imdb.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(generator = "rates_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rates_sequence", sequenceName = "rates_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @PositiveOrZero
    private double rating;
}
