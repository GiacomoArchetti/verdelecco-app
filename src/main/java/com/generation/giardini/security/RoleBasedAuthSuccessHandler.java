package com.generation.giardini.security;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Gestisce il reindirizzamento dell'utente dopo un'autenticazione
 * avvenuta con successo, in base al ruolo assegnato.
 *
 * <p>Gli utenti con ruolo {@code ROLE_ADMIN} vengono reindirizzati
 * alla sezione amministrativa, mentre gli utenti con ruolo
 * {@code ROLE_UTENTE} vengono reindirizzati alla sezione cliente.</p>
 *
 * <p>Nel caso in cui l'utente non abbia nessuno dei ruoli previsti,
 * viene effettuato un reindirizzamento alla pagina principale.</p>
 */
@Component
public class RoleBasedAuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Gestisce l'autenticazione avvenuta con successo e determina
     * la pagina di destinazione dell'utente in base alle sue autorità.
     *
     * @param request richiesta HTTP corrente
     * @param response risposta HTTP utilizzata per il reindirizzamento
     * @param authentication informazioni relative all'utente autenticato
     * @throws IOException se si verifica un errore durante il reindirizzamento
     * @throws ServletException se si verifica un errore nella gestione della richiesta
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Recupera i ruoli/autorità associati all'utente autenticato.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Verifica se l'utente possiede il ruolo di amministratore.
        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Verifica se l'utente possiede il ruolo di utente.
        boolean isUser = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_UTENTE"));

        // Gli amministratori vengono reindirizzati alla sezione amministrativa.
        if (isAdmin) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        // Gli utenti vengono reindirizzati alla sezione cliente.
        if (isUser) {
            response.sendRedirect(request.getContextPath() + "/client");
            return;
        }

        // Fallback: se il ruolo non è riconosciuto, reindirizza alla pagina principale.
        response.sendRedirect(request.getContextPath() + "/");
    }
}
