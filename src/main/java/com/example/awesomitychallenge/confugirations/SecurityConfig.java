package com.example.awesomitychallenge.confugirations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint jwtAuthEntryPoint;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**","/v2/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**")
                        .permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/products/**").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers("/orders/**").hasAnyAuthority("ADMIN","USER")
                        .requestMatchers("/categories/**").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
