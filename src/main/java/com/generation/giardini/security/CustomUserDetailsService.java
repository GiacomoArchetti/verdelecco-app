package com.generation.giardini.security;

import java.util.Collections;

import com.generation.giardini.entity.utente.Utente;
import com.generation.giardini.repository.UtenteRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username == null ? null : username.trim();
        Utente utente = utenteRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utente.getRuolo().name());

        return new User(utente.getEmail(), utente.getPassword(), Boolean.TRUE.equals(utente.getAttivo())
            && !Boolean.TRUE.equals(utente.getGuest()), true, true, true, Collections.singleton(authority));
    }
}
