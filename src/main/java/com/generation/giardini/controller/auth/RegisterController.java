package com.generation.giardini.controller.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.repository.UtenteRepository;
import com.generation.giardini.security.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    // METODI GET
    // Restituisce la pagina di registrazione
    @GetMapping("")
    public String registrazione(Model model) {
        if (!model.containsAttribute("registrazione")) {
            model.addAttribute("registrazione", new RegistrationForm());
        }
        return "registrazione";
    }

    // METODI POST
    // Gestisce l'invio del modulo di registrazione
    @PostMapping("")
    public String submitRegistrazione(@Valid @ModelAttribute("registrazione") RegistrationForm form,
            Model model,
            HttpServletRequest request) { // 2. Aggiunto HttpServletRequest
        
        if (form.getPassword() == null || !form.getPassword().equals(form.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Le password non coincidono.");
            return "registrazione";
        }

        String email = form.getEmail() == null ? null : form.getEmail().trim().toLowerCase();
        Utente u = email == null ? new Utente() : utenteRepository.findByEmailIgnoreCase(email).orElseGet(Utente::new);
        
        String fullName = form.getNome() == null ? "" : form.getNome().trim();
        if (!fullName.isEmpty()) {
            String[] parts = fullName.split("\\s+", 2);
            u.setNome(parts[0]);
            u.setCognome(parts.length > 1 ? parts[1] : "");
        } else {
            u.setNome("");
            u.setCognome("");
        }
        u.setEmail(email);
        u.setTelefono(form.getTelefono());
        u.setPassword(passwordEncoder.encode(form.getPassword()));
        u.setAttivo(true);
        u.setGuest(false);

        // Salvataggio utente nel Database
        utenteRepository.save(u);

        // 3. LOGICA DI AUTENTICAZIONE AUTOMATICA (Auto-Login)
        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getEmail());
        UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Salva la sessione di login nel browser
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // 4. Redirect diretto alla dashboard cliente
        return "redirect:/client";
    }

    /**
     * Modello utilizzato per raccogliere i dati inseriti dall'utente
     * durante la fase di registrazione.
     *
     * <p>
     * Contiene i dati anagrafici e di contatto dell'utente,
     * oltre alle credenziali necessarie per la creazione dell'account.
     * </p>
     */
    public static class RegistrationForm {
        private String nome;
        private String cognome;
        private String email;
        private String telefono;
        private String password;
        private String passwordConfirm;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCognome() {
            return cognome;
        }

        public void setCognome(String cognome) {
            this.cognome = cognome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordConfirm() {
            return passwordConfirm;
        }

        public void setPasswordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
        }
    }

}