package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import com.zaki.imdb.imdb.model.UserRole;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "users_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 3
    )
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NonNull
    @NotNull
    @Email
    private String email;

    @NonNull
    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @NonNull
    @NotNull
    @Size(min = 5)
    @Column(length = 1024)
    private String password;

    private boolean active = true;

    @NotNull
    @NonNull
    private UserRole role = UserRole.ANONYMOUS;

    @URL
    @Basic(optional = true)
    @Column(name = "USER_IMAGE_URL")

    // TODO use some default image if the user did not provide such
    private String userImageUrl;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    @OneToMany(targetEntity = Comment.class, mappedBy = "author", fetch = FetchType.LAZY)
    @ToString.Exclude
    List<Comment> userComments = new ArrayList<>();

    public User(@NonNull @NotNull @Size(min = 2, max = 50) String firstName, @NonNull @NotNull @Size(min = 2, max = 50) String lastName, @NonNull @NotNull @Email String email, @NonNull @NotNull @Size(min = 5, max = 30) String username, @NonNull @NotNull @Size(min = 5, max = 30) String password, UserRole role, @URL String userImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userImageUrl = userImageUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.getAsString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
