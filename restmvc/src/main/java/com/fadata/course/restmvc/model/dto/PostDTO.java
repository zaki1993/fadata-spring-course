package com.fadata.course.restmvc.model.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 5, max = 40)
    private String title;

    @NonNull
    @NotNull
    @Size(min = 20, max = 2048)
    private String content;

    @NonNull
    @NotNull
    private User author;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}
