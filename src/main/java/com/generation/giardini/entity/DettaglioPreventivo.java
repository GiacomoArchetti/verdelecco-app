package com.generation.giardini.entity;

import com.generation.giardini.entity.preventivo.Preventivo;
import com.generation.giardini.entity.servizio.Servizio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "dettaglio_preventivo")
@Data
public class DettaglioPreventivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dettaglio")
    private Long idDettaglio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_preventivo", nullable = false)
    private Preventivo preventivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servizio", nullable = false)
    private Servizio servizio;

    @Column(name = "quantita", nullable = false)
    private Integer quantita = 1;
}
