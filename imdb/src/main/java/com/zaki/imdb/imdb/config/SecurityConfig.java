package com.zaki.imdb.imdb.config;


import com.zaki.imdb.imdb.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic(withDefaults())
                .authorizeRequests()
// Main entities access. Everyone can view movies and genres, but only ADMINISTRATOR user can create, update and delete such.
                .antMatchers(HttpMethod.GET, "/imdb/genres/**", "/imdb/movies/**").hasAnyRole()
                .antMatchers(HttpMethod.POST, "/imdb/genres/**", "/imdb/movies/**").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT, "/imdb/genres/**", "/imdb/movies/**").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "/imdb/genres/**", "/imdb/movies/**").hasRole("ADMINISTRATOR")
// TODO: /users
// Comments access. Everyone can view comments, but only REGISTERED, MODERATOR and ADMINISTRATOR users can POST, PUT and DELETE such. ADMINISTRATOR and MODERATOR can approve or disapprove comments.
                .antMatchers(HttpMethod.GET, "/imdb/comments/**").hasAnyRole()
                .antMatchers(HttpMethod.POST, "/imdb/comments/**").hasAnyRole("ADMINISTRATOR", "MODERATOR", "REGISTERED")
                .antMatchers(HttpMethod.PUT, "/imdb/comments/**").hasAnyRole("ADMINISTRATOR", "MODERATOR", "REGISTERED")
                .antMatchers(HttpMethod.DELETE, "/imdb/comments/**").hasAnyRole("ADMINISTRATOR", "MODERATOR", "REGISTERED")
                .antMatchers(HttpMethod.POST, "/imdb/comments/**/approve", "/imdb/comments/**/disapprove").hasAnyRole("ADMINISTRATOR", "MODERATOR")
// Rate access. Only REGISTERED, MODERATOR and ADMINISTRATOR users can rate a movie.
                .antMatchers(HttpMethod.POST, "/imdb/movies/**/rate").hasAnyRole("ADMINISTRATOR", "MODERATOR", "REGISTERED")
// Additional access
                .antMatchers("/actuator/**").hasRole("ADMINISTRATOR")
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Bean
    UserDetailsService userDetailsService(UsersService userService) {
        return username -> userService.getUserByUsername(username);
    }
}