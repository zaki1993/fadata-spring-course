package com.zaki.imdb.imdb.model.dto;

import com.sun.istack.NotNull;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    private Long id;

    @NonNull
    private User user;

    @NonNull
    private Movie movie;

    @NonNull
    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Double rating;
}
