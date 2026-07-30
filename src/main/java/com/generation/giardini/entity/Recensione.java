package com.generation.giardini.entity;

import java.time.LocalDateTime;

import com.generation.giardini.entity.prenotazione.Prenotazione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recensione")
@Data
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recensione")
    private Long idRecensione;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prenotazione", nullable = false, unique = true)
    private Prenotazione prenotazione;

    @Column(name = "voto", nullable = false)
    private Integer voto;

    @Column(name = "commento", columnDefinition = "TEXT")
    private String commento;

    @Column(name = "data_recensione", nullable = false)
    private LocalDateTime dataRecensione;
}
