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

/**
 * <h3>AdminController</h3>
 * <p>Controller Spring MVC per la gestione dell'area amministrativa.</p>
 * <p>Gestisce la visualizzazione delle tabelle paginate per clienti, preventivi, 
 * prenotazioni e recensioni, oltre alle azioni di approvazione e rifiuto dei preventivi.</p>
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    // DIPENDENZE
    private final UtenteService utenteService;
    private final PreventivoService preventivoService;
    private final PrenotazioneService prenotazioneService;
    private final RecensioneService recensioneService;

    // METODI GET

    /**
     * Carica la dashboard amministrativa popolando il Model con i dati paginati di ciascuna sezione.
     * 
     * @param model Il Model Spring per il passaggio dei dati alla vista Thymeleaf
     * @param clientiPage Numero di pagina per la tabella Clienti (default: 0)
     * @param preventiviPage Numero di pagina per la tabella Preventivi (default: 0)
     * @param prenotazioniPage Numero di pagina per la tabella Prenotazioni (default: 0)
     * @param recensioniPage Numero di pagina per la tabella Recensioni (default: 0)
     * @return Il nome del template HTML ("admin")
     */
    @GetMapping
    public String admin(Model model,
                        @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                        @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                        @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                        @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage) {
        
        int pageSize = 5;
        
        // Popola il Model con le entità paginate per ciascun tab della dashboard
        model.addAttribute("clienti", utenteService.readAll(PageRequest.of(clientiPage, pageSize)));
        model.addAttribute("preventivi", preventivoService.readAll(PageRequest.of(preventiviPage, pageSize)));
        model.addAttribute("prenotazioni", prenotazioneService.readAll(PageRequest.of(prenotazioniPage, pageSize)));
        model.addAttribute("recensioni", recensioneService.readAll(PageRequest.of(recensioniPage, pageSize)));
        
        return "admin";
    }

    // METODI POST

    /**
     * Gestisce l'accettazione di un preventivo da parte dell'amministratore.
     * Mantiene gli indici di paginazione attuali durante il redirect.
     * 
     * @param id L'identificativo unico del preventivo da accettare
     * @param clientiPage Indice pagina Clienti da preservare nel redirect
     * @param preventiviPage Indice pagina Preventivi da preservare nel redirect
     * @param prenotazioniPage Indice pagina Prenotazioni da preservare nel redirect
     * @param recensioniPage Indice pagina Recensioni da preservare nel redirect
     * @param redirectAttributes Oggetto per mantenere i parametri nella query string post-redirect
     * @return Redirect all'ancora #preventivi della dashboard admin
     */
    @PostMapping("/preventivi/{id}/accept")
    public String acceptPreventivo(@PathVariable Long id,
                                   @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                                   @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                                   @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                   @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage,
                                   RedirectAttributes redirectAttributes) {
        
        // Esegue la logica di business per l'accettazione del preventivo
        preventivoService.accept(id);
        
        // Preserva lo stato di paginazione di tutte le sezioni
        redirectAttributes.addAttribute("clientiPage", clientiPage);
        redirectAttributes.addAttribute("preventiviPage", preventiviPage);
        redirectAttributes.addAttribute("prenotazioniPage", prenotazioniPage);
        redirectAttributes.addAttribute("recensioniPage", recensioniPage);
        
        return "redirect:/admin#preventivi";
    }

    /**
     * Gestisce il rifiuto di un preventivo da parte dell'amministratore.
     * Mantiene gli indici di paginazione attuali durante il redirect.
     * 
     * @param id L'identificativo unico del preventivo da rifiutare
     * @param clientiPage Indice pagina Clienti da preservare nel redirect
     * @param preventiviPage Indice pagina Preventivi da preservare nel redirect
     * @param prenotazioniPage Indice pagina Prenotazioni da preservare nel redirect
     * @param recensioniPage Indice pagina Recensioni da preservare nel redirect
     * @param redirectAttributes Oggetto per mantenere i parametri nella query string post-redirect
     * @return Redirect all'ancora #preventivi della dashboard admin
     */
    @PostMapping("/preventivi/{id}/reject")
    public String rejectPreventivo(@PathVariable Long id,
                                   @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                                   @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                                   @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                   @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage,
                                   RedirectAttributes redirectAttributes) {

        // Esegue la logica di business per il rifiuto del preventivo
        preventivoService.reject(id);

        // Preserva lo stato di paginazione di tutte le sezioni
        redirectAttributes.addAttribute("clientiPage", clientiPage);
        redirectAttributes.addAttribute("preventiviPage", preventiviPage);
        redirectAttributes.addAttribute("prenotazioniPage", prenotazioniPage);
        redirectAttributes.addAttribute("recensioniPage", recensioniPage);
        
        return "redirect:/admin#preventivi";
    }

    /**
     * Imposta lo stato di una prenotazione come 'COMPLETATA' da parte dell'amministratore.
     * Mantiene gli indici di paginazione attuali durante il redirect.
     * 
     * @param id L'identificativo unico della prenotazione da completare
     * @param clientiPage Indice pagina Clienti da preservare nel redirect
     * @param preventiviPage Indice pagina Preventivi da preservare nel redirect
     * @param prenotazioniPage Indice pagina Prenotazioni da preservare nel redirect
     * @param recensioniPage Indice pagina Recensioni da preservare nel redirect
     * @param redirectAttributes Oggetto per mantenere i parametri nella query string post-redirect
     * @return Redirect all'ancora #prenotazioni della dashboard admin
     */
    @PostMapping("/prenotazioni/{id}/complete")
    public String completePrenotazione(@PathVariable Long id,
                                       @RequestParam(name = "clientiPage", defaultValue = "0") int clientiPage,
                                       @RequestParam(name = "preventiviPage", defaultValue = "0") int preventiviPage,
                                       @RequestParam(name = "prenotazioniPage", defaultValue = "0") int prenotazioniPage,
                                       @RequestParam(name = "recensioniPage", defaultValue = "0") int recensioniPage,
                                       RedirectAttributes redirectAttributes) {
        
        // Esegue la logica di business per impostare la prenotazione come completata
        prenotazioneService.complete(id);
        
        // Preserva lo stato di paginazione di tutte le sezioni
        redirectAttributes.addAttribute("clientiPage", clientiPage);
        redirectAttributes.addAttribute("preventiviPage", preventiviPage);
        redirectAttributes.addAttribute("prenotazioniPage", prenotazioniPage);
        redirectAttributes.addAttribute("recensioniPage", recensioniPage);
        
        return "redirect:/admin#prenotazioni";
    }

}