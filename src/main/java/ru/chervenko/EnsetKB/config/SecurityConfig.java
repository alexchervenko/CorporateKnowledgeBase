package ru.chervenko.EnsetKB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.chervenko.EnsetKB.services.PersonDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/login",
                                "/auth/register",
                                "/error",
                                "/webjars/**",
                                "/css/**"
                        )
                        .permitAll()
                        .anyRequest().hasAnyRole("USER", "EDITOR","ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/problems", true)
                        .permitAll()
                )
                .userDetailsService(personDetailsService)
                .logout((logout) -> logout.permitAll());
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
