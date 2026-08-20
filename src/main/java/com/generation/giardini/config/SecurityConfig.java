package com.generation.giardini.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
                // DelegatingPasswordEncoder può gestire più algoritmi diversi.
                // La mappa dice quali codificatori conosce l'app.
        Map<String, PasswordEncoder> encoders = new HashMap<>();
                // BCrypt con strength 12: più il numero sale, più il controllo della password costa tempo.
                // 12 è un compromesso comune tra sicurezza e prestazioni.
                //12 significa che l'algoritmo BCrypt esegue 2^12 (4096) iterazioni
                // di hashing, rendendo più difficile per un attaccante indovinare la password.
        encoders.put("bcrypt", new BCryptPasswordEncoder(12));
                // DelegatingPasswordEncoder usa {bcrypt} come prefisso negli hash.
                // Questo è utile se un domani si volesse cambiare algoritmo senza rompere gli hash già esistenti.
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. AREA PUBBLICA(con pagina autenticazione)
                .requestMatchers("/", "/css/**", "/js/**", "/preventivo", "/preventivo/inviato", "/images/**", "/webjars/**", "/login", "/register", "/register/**").permitAll()
                
                
                // 2. AREA UTENTE (client area)
                .requestMatchers("/client/**").hasRole("UTENTE")
                
                // 3. AREA AMMINISTRATORE(ADMIN)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // 4. Tutto il resto richiede autenticazione
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Specifica la rotta della tua pagina di login personalizzata
                .usernameParameter("email") // Specifica il parametro dello username
                .passwordParameter("password") // Specifica il parametro della password
                .successHandler(authenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Dove mandare l'utente dopo il logout
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new com.generation.giardini.security.RoleBasedAuthSuccessHandler();
    }
}