package com.zaki.imdb.imdb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import com.zaki.imdb.imdb.model.UserRole;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"comments", "rates", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class User implements UserDetails {

    private static final UserRole DEFAULT_USER_ROLE = UserRole.ANONYMOUS;

    private static final String DEFAULT_USER_IMAGE = "https://www.knowmuhammad.org/img/noavatarn.png";

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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles = Set.of(DEFAULT_USER_ROLE);

    @URL
    @Column(name = "USER_IMAGE_URL")
    private String userImageUrl = DEFAULT_USER_IMAGE;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author", targetEntity = Comment.class)
    @ToString.Exclude
    @Fetch(value = FetchMode.SELECT)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", targetEntity = Rate.class)
    private List<Rate> rates = new ArrayList<>();

    public User(@NonNull @NotNull @Size(min = 2, max = 50) String firstName, @NonNull @NotNull @Size(min = 2, max = 50) String lastName, @NonNull @NotNull @Email String email, @NonNull @NotNull @Size(min = 5, max = 30) String username, @NonNull @NotNull @Size(min = 5, max = 30) String password, Set<UserRole> roles, @URL String userImageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userImageUrl = userImageUrl;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAsString())).collect(Collectors.toList());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email) &&
                username.equals(user.username) &&
                created.equals(user.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, created);
    }
}
