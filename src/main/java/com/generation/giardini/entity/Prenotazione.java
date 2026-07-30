package com.generation.giardini.entity;

import java.time.LocalDateTime;

import com.generation.giardini.entity.enums.StatoPrenotazione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prenotazione")
@Data
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenotazione")
    private Long idPrenotazione;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_preventivo", nullable = false, unique = true)
    private Preventivo preventivo;

    @Column(name = "data_intervento", nullable = false)
    private LocalDateTime dataIntervento;

    @Column(name = "indirizzo", nullable = false, length = 200)
    private String indirizzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoPrenotazione stato = StatoPrenotazione.PROGRAMMATA;
}
