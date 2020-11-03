package com.zaki.imdb.imdb.model.dto;

import com.sun.istack.NotNull;
import com.zaki.imdb.imdb.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminDTO {

    private static final UserRole DEFAULT_USER_ROLE = UserRole.ANONYMOUS;

    private static final String DEFAULT_USER_IMAGE = "https://www.knowmuhammad.org/img/noavatarn.png";

    private Long id;

    @NonNull
    @NotNull
    @Email
    private String email;

    @NonNull
    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    private boolean active = true;

    @NotNull
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles = Set.of(DEFAULT_USER_ROLE);

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    public UserAdminDTO(@NonNull @NotNull @Email String email, @NonNull @NotNull @Size(min = 5, max = 30) String username, Set<UserRole> roles) {
        this.email = email;
        this.username = username;
        this.roles = roles;
    }
}
