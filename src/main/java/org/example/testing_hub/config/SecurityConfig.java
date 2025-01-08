package org.example.testing_hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключить CSRF
                .authorizeHttpRequests(auth -> auth
                        // Разрешить публичный доступ к Swagger и документации
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v2/api-docs/**",
                                "/webjars/**",
                                "/public/**"
                        ).permitAll()
                        // Сделать все эндпоинты доступными без авторизации
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указать кастомную страницу логина (опционально)
                        .defaultSuccessUrl("/", true) // Перенаправление после успешного входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // Перенаправление после выхода
                        .permitAll()
                );
        return http.build();
    }
}
