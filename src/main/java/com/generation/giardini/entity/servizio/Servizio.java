package com.generation.giardini.entity.servizio;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "servizio")
@Data
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servizio")
    private Long idServizio;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private NomeServizio nome;

    @Column(name = "prezzo_al_mq", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoAlMq;

    @Column(name = "minuti_al_mq", nullable = false)
    private Integer minutiAlMq;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "attivo", nullable = false)
    private Boolean attivo = true;
}
