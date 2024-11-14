package com.example.hms1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception{
        httpSecurity.csrf().disable().cors().disable();
        httpSecurity.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        //httpSecurity.authorizeHttpRequests().anyRequest().permitAll();

        httpSecurity.authorizeHttpRequests().requestMatchers("/api/v1/user/signup-User","/api/v1/user/signup-Owner-User","/api/v1/user/login")
                .permitAll()
                .requestMatchers("/api/v1/country/addCountry").hasAnyRole("OWNER","ADMIN")
                .anyRequest().authenticated();
        return httpSecurity.build();
    }

}
