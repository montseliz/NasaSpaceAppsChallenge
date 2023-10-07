package com.galacticspacecoders.hackathon.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity  //Allow to use @PreAuthorize
public class SecurityConfiguration {
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        // -- authentication
                        "api/mysql/auth/**",
                        // -- Swagger UI v3 (OpenAPI)
                        "/v3/api-docs/**", "/swagger-ui/**",
                        "/images/**", "/css/**", "/static/**", "/error/**", "/img/**", "/json/**",
                        // controller
                        "/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginProcessingUrl("/auth/register")
//                     .defaultSuccessUrl("/api/success")
                    .permitAll()
                    .and()
                .authenticationProvider(authProvider)
                .logout()
                    .logoutUrl("/auth/logout")
                    .permitAll();
        return http.build();
    }






}
