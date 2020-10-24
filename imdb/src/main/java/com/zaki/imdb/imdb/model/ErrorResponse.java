package com.zaki.imdb.imdb.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    private Integer httpStatusCode;

    @NonNull
    private String message;

    private List<String> constraintViolations = new ArrayList<>();

    private List<String> exceptionMessages = new ArrayList<>();
}
