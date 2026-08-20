package com.generation.giardini.config;

import java.util.List;

import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.repository.UtenteRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configurazione eseguita all'avvio dell'applicazione per verificare
 * e, se necessario, codificare le password degli utenti presenti
 * nel database.
 *
 * <p>
 * Le password già codificate con BCrypt vengono lasciate invariate,
 * mentre quelle ancora memorizzate in chiaro vengono codificate prima
 * di essere salvate nuovamente nel database.
 * </p>
 */
@Configuration
public class StartupPasswordEncoder {

    /**
     * Crea un {@link CommandLineRunner} che viene eseguito
     * all'avvio dell'applicazione.
     *
     * <p>
     * Il metodo recupera tutti gli utenti dal database e verifica
     * lo stato della relativa password. Le password non ancora codificate
     * con BCrypt vengono convertite e aggiornate nel database.
     * </p>
     *
     * @param utenteRepository repository utilizzato per recuperare
     *                         e aggiornare gli utenti
     * @param passwordEncoder  componente utilizzato per codificare
     *                         le password tramite BCrypt
     * @return {@link CommandLineRunner} eseguito all'avvio dell'applicazione
     */
    @Bean
    public CommandLineRunner encodePasswords(
            UtenteRepository utenteRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Recupera tutti gli utenti presenti nel database.
            List<Utente> utenti = utenteRepository.findAll();

            for (Utente u : utenti) {

                // Recupera la password attualmente memorizzata.
                String pw = u.getPassword();

                // Verifica che la password esista e che non sia già
                // codificata tramite uno dei formati BCrypt supportati.
                if (pw != null
                        && !pw.startsWith("$2a$")
                        && !pw.startsWith("$2b$")
                        && !pw.startsWith("$2y$")) {

                    // Codifica la password prima di salvarla nel database.
                    String encoded = passwordEncoder.encode(pw);
                    u.setPassword(encoded);

                    // Salva l'utente con la password codificata.
                    utenteRepository.save(u);
                }
            }
        };
    }
}
