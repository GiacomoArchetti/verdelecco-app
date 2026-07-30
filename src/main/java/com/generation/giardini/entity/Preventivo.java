package com.generation.giardini.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.generation.giardini.entity.enums.StatoPreventivo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "preventivo")
@Data
public class Preventivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preventivo")
    private Long idPreventivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(name = "indirizzo", nullable = false, length = 200)
    private String indirizzo;

    @Column(name = "superficie_mq", nullable = false, precision = 7, scale = 2)
    private BigDecimal superficieMq;

    @Column(name = "costo_stimato", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoStimato;

    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "data_intervento", nullable = false)
    private LocalDateTime dataIntervento;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoPreventivo statoPreventivo;

    @OneToMany(mappedBy = "preventivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DettaglioPreventivo> dettagli = new ArrayList<>();

}
