package com.fadata.course.restmvc.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(generator = "post_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="post_sequence", sequenceName = "post_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 20, max = 2048, message = "Content is invalid test error message")
    private String content;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // In case we want to set name for te column
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false)
    private User author;

    @ElementCollection
    private List<@Size(min = 2, max = 10) String> keywords;

    @URL
    @Basic(optional = true)
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}
