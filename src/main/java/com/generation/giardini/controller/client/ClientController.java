package com.generation.giardini.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {


    //METODI GET

        //Restituisce la pagina del portale cliente
        @GetMapping
        public String utente() {
            return "client";
        }
        
}