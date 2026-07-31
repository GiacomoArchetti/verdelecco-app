package com.generation.giardini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. AREA PUBBLICA(con pagina autenticazione)
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/preventivo/**", "/login", "/registrazione").permitAll()
                
                
                // 2. AREA UTENTE
                .requestMatchers("/utente/**").hasRole("USER")
                
                // 3. AREA AMMINISTRATORE(ADMIN)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // 4. Tutto il resto richiede autenticazione
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Specifica la rotta della tua pagina di login personalizzata
                .defaultSuccessUrl("/", true) // Dove reindirizzare dopo il login con successo
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Dove mandare l'utente dopo il logout
                .permitAll()
            );

        return http.build();
    }
}