package com.generation.giardini.controller.admin;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.giardini.repository.PrenotazioneRepository;
import com.generation.giardini.repository.PreventivoRepository;
import com.generation.giardini.repository.RecensioneRepository;
import com.generation.giardini.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    //DIPENDENZE
        private final UtenteRepository utenteRepository;
        private final PreventivoRepository preventivoRepository;
        private final PrenotazioneRepository prenotazioneRepository;
        private final RecensioneRepository recensioneRepository;


        
    //METODI GET

        //restituisce la pagina del portale admin assemblandone le varie parti con i Model
        @GetMapping
        public String admin(Model model,
                            @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                            @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                            @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                            @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage) {
            int pageSize = 5;
            model.addAttribute("clienti", utenteRepository.findAll(PageRequest.of(clientiPage, pageSize)));
            model.addAttribute("preventivi", preventivoRepository.findAll(PageRequest.of(preventiviPage, pageSize)));
            model.addAttribute("prenotazioni", prenotazioneRepository.findAll(PageRequest.of(prenotazioniPage, pageSize)));
            model.addAttribute("recensioni", recensioneRepository.findAll(PageRequest.of(recensioniPage, pageSize)));
            return "admin";
        }

        //METODI POST
        @PostMapping("/preventivi/{id}/accept")
        public String acceptPreventivo(@PathVariable Long id) {
            preventivoRepository.findById(id).ifPresent(p -> { p.setStatoPreventivo(com.generation.giardini.entity.preventivo.StatoPreventivo.ACCETTATO); preventivoRepository.save(p); });
            return "redirect:/admin";
        }

        @PostMapping("/preventivi/{id}/reject")
        public String rejectPreventivo(@PathVariable Long id) {
            preventivoRepository.findById(id).ifPresent(p -> { p.setStatoPreventivo(com.generation.giardini.entity.preventivo.StatoPreventivo.RIFIUTATO); preventivoRepository.save(p); });
            return "redirect:/admin";
        }

}