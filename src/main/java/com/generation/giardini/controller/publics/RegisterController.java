package com.generation.giardini.controller.publics;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.repository.UtenteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
            Model model) {
        if (form.getPassword() == null || !form.getPassword().equals(form.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Le password non coincidono.");
            return "registrazione";
        }

        String email = form.getEmail() == null ? null : form.getEmail().trim().toLowerCase();
        Utente u = email == null ? new Utente() : utenteRepository.findByEmailIgnoreCase(email).orElseGet(Utente::new);
        // The registration form provides a single "nome" field containing full name.
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
        // ruolo di default in entity è UTENTE, ma assicuriamolo
        // u.setRuolo(com.generation.giardini.entity.utente.Ruolo.UTENTE);

        utenteRepository.save(u);

        return "redirect:/login";
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