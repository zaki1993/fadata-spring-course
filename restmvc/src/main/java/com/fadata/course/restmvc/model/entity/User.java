package com.fadata.course.restmvc.model;

import com.fadata.course.restmvc.util.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
@JsonIgnoreProperties({"comments", "authorities", "enabled"})
public class User implements UserDetails {

    private static final boolean active = true;

    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 3)
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 5, max = 30)
    private String username;

    @NonNull
    @NotNull
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NonNull
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NonNull
    @NotNull
    @Size(min = 5)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private Set<Role> roles = Utils.asSet(Role.READER);

    @ElementCollection
    @OneToMany(targetEntity = Comment.class, mappedBy = "author", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments;

    public User(@NonNull @NotNull @Size(min = 5, max = 30) String username, @NonNull @NotNull @Email @Size(min = 5, max = 50) String email, @NonNull @NotNull @Size(min = 2, max = 30) String firstName, @NonNull @NotNull @Size(min = 2, max = 30) String lastName, @NonNull @NotNull @Size(min = 5) String password, @NonNull @NotNull @Size(min = 1) Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
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
