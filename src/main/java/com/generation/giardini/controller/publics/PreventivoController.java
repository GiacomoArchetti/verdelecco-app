package com.generation.giardini.controller.publics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.giardini.dto.PreventivoRequestDto;

@Controller
@RequestMapping("/preventivo")
public class PreventivoController {

    //METODI GET

        //Restituisce la pagina del preventivo
        @GetMapping
        public String preventivo(Model model) {
            if (!model.containsAttribute("preventivoRequest")) {
                model.addAttribute("preventivoRequest", new PreventivoRequestDto());
            }
            return "preventivo";
        }

        //Restituisce la pagina di ringraziamento del preventivo
        @GetMapping("/inviato")
        public String preventivoInviato() {
            return "preventivo-success";
        }



        

        
        //Gestisce l'invio del modulo preventivo
        @PostMapping
        public String submitPreventivo(@ModelAttribute PreventivoRequestDto preventivoRequest) {
            return "redirect:/preventivo/inviato";
        }

}