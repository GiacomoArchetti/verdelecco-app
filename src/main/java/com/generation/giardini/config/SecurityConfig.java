package com.generation.giardini.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.generation.giardini.security.CustomUserDetailsService;

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
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        http
        .authenticationProvider(authenticationProvider)
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
                // Gestiamo il reindirizzamento dell utente dopo login con un metodo creato apposta per reindirizzare a seconda del ruolo dato che nel nostro appplicativo abbiamo implementato 2 portali, 1 admin e 1 utente
                .successHandler(authenticationSuccessHandler())
                // Se username o password sono sbagliati, torniamo alla login con un parametro error.
                // La pagina può usare quel parametro per mostrare un messaggio all'utente... "/login?error" è già impostato di default quindi in realtà inutile, è stato inserito a scopo didattico
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                // Dove mandare l'utente dopo il logout
                .logoutSuccessUrl("/")
                // Distrugge la sessione lato server.
                // Questo è importante perché la vecchia sessione non deve restare valida.
                .invalidateHttpSession(true)
                // Rimuove l'informazione di autenticazione associata all'utente.
                // In pratica Spring "dimentica" chi era loggato.
                .clearAuthentication(true)
                // Elimina il cookie di sessione dal browser.
                // Serve a evitare che il browser continui a usare una sessione vecchia.
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Spring cambia l'identificatore della sessione dopo il login.
            // Questo riduce un attacco chiamato session fixation, cioè il riuso di un ID di sessione noto.
            .sessionManagement(session -> session
                .sessionFixation(fixation -> fixation.changeSessionId())
            )

            // CSRF resta attivo.
            // Questo protegge i form: impedisce che un sito esterno faccia inviare richieste al posto dell'utente.
            // È molto importante quando l'app usa sessioni e form HTML.
            .csrf(Customizer.withDefaults())
            
            // Content Security Policy: è una regola di sicurezza del browser.
            // Dice da quali sorgenti il browser può caricare script, stili, immagini e altri contenuti.
            // .headers(headers -> headers
            //     .contentSecurityPolicy(csp -> csp.policyDirectives(
            //         // 'self' significa "solo da questo stesso sito".
            //         // In pratica, blocchiamo contenuti caricati da siti esterni non autorizzati.
            //         "default-src 'self'; " +
            //         "script-src 'self'; " +
            //         "style-src 'self'; " +
            //         "img-src 'self' data:; " +
            //         "object-src 'none'; " +
            //         "base-uri 'self'; " +
            //         "frame-ancestors 'none'; " +
            //         "form-action 'self'"
            //     ))
            // )
            ;

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
                // Creiamo il provider standard per login con username e password.
                // Il costruttore riceve il servizio che sa come trovare gli utenti nel database.
        DaoAuthenticationProvider provider = 
        new DaoAuthenticationProvider(userDetailsService);
                // Diciamo al provider come deve confrontare la password inserita dall'utente
                // con quella salvata nel database.
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new com.generation.giardini.security.RoleBasedAuthSuccessHandler();
    }
}