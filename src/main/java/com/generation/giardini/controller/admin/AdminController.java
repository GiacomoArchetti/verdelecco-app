package com.generation.giardini.controller.admin;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.generation.giardini.service.prenotazione.PrenotazioneService;
import com.generation.giardini.service.preventivo.PreventivoService;
import com.generation.giardini.service.recensione.RecensioneService;
import com.generation.giardini.service.utente.UtenteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    //DIPENDENZE
        private final UtenteService utenteService;
        private final PreventivoService preventivoService;
        private final PrenotazioneService prenotazioneService;
        private final RecensioneService recensioneService;


        
    //METODI GET

        //restituisce la pagina del portale admin assemblandone le varie parti con i Model
        @GetMapping
        public String admin(Model model,
                            @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                            @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                            @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                            @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage) {
            int pageSize = 5;
            model.addAttribute("clienti", utenteService.readAll(PageRequest.of(clientiPage, pageSize)));
            model.addAttribute("preventivi", preventivoService.readAll(PageRequest.of(preventiviPage, pageSize)));
            model.addAttribute("prenotazioni", prenotazioneService.readAll(PageRequest.of(prenotazioniPage, pageSize)));
            model.addAttribute("recensioni", recensioneService.readAll(PageRequest.of(recensioniPage, pageSize)));
            return "admin";
        }

        //METODI POST
        @PostMapping("/preventivi/{id}/accept")
        public String acceptPreventivo(@PathVariable Long id,
                                       @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                                       @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                                       @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                       @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage,
                                       RedirectAttributes redirectAttributes) {
            
            preventivoService.accept(id);
            
            redirectAttributes.addAttribute("clientiPage", clientiPage);
            redirectAttributes.addAttribute("preventiviPage", preventiviPage);
            redirectAttributes.addAttribute("prenotazioniPage", prenotazioniPage);
            redirectAttributes.addAttribute("recensioniPage", recensioniPage);
            return "redirect:/admin#preventivi";
        }

        @PostMapping("/preventivi/{id}/reject")
        public String rejectPreventivo(@PathVariable Long id,
                                       @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                                       @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                                       @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                       @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage,
                                       RedirectAttributes redirectAttributes) {

            preventivoService.reject(id);

            redirectAttributes.addAttribute("clientiPage", clientiPage);
            redirectAttributes.addAttribute("preventiviPage", preventiviPage);
            redirectAttributes.addAttribute("prenotazioniPage", prenotazioniPage);
            redirectAttributes.addAttribute("recensioniPage", recensioniPage);
            return "redirect:/admin#preventivi";
        }

}