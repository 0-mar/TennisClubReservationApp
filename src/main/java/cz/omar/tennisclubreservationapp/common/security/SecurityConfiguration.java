package cz.omar.tennisclubreservationapp.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static cz.omar.tennisclubreservationapp.user.storage.Role.ADMIN;
import static cz.omar.tennisclubreservationapp.user.storage.Role.USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(POST, "api/auth/logout").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(POST, "api/auth/refresh-token").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(GET, "/api/courts/**").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers("/api/courts/**").hasAuthority(ADMIN.name())
                                .requestMatchers(GET, "/api/reservations/**").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers(POST, "/api/reservations/**").hasAnyAuthority(ADMIN.name(), USER.name())
                                .requestMatchers("/api/reservations/**").hasAuthority(ADMIN.name())
                                .requestMatchers("/api/users/**").hasAuthority(ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
