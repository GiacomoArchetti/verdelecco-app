-- =====================================================
-- TABELLA UTENTE
-- =====================================================

CREATE TABLE utente (
    id_utente BIGINT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,

    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    telefono VARCHAR(30),

    attivo TINYINT(1) NOT NULL DEFAULT 1,

    ruolo ENUM('ADMIN', 'UTENTE') NOT NULL DEFAULT 'UTENTE'
);

-- =====================================================
-- TABELLA SERVIZIO
-- =====================================================

CREATE TABLE servizio (
    id_servizio BIGINT AUTO_INCREMENT PRIMARY KEY,

    nome ENUM(
        'TAGLIO_ERBA',
        'POTATURA',
        'SEMINA',
        'PULIZIA_GIARDINO'
    ) NOT NULL,

    prezzo_al_mq DECIMAL(10,2) NOT NULL,

    minuti_al_mq INT NOT NULL,

    descrizione TEXT,

    attivo TINYINT(1) NOT NULL DEFAULT 1
);

-- =====================================================
-- TABELLA PREVENTIVO
-- =====================================================

CREATE TABLE preventivo (

    id_preventivo BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_utente BIGINT NOT NULL,

    indirizzo VARCHAR(200) NOT NULL,

    superficie_mq DECIMAL(7,2) NOT NULL,

    costo_stimato DECIMAL(10,2) NOT NULL,

    descrizione TEXT,

    data_intervento DATETIME NOT NULL,

    data_emissione DATE NOT NULL,

    data_scadenza DATE,

    stato ENUM(
        'IN_ATTESA',
        'ACCETTATO',
        'RIFIUTATO',
        'SCADUTO',
        'ANNULLATO'
    ) NOT NULL DEFAULT 'IN_ATTESA',

    CONSTRAINT fk_preventivo_utente
        FOREIGN KEY (id_utente)
        REFERENCES utente(id_utente)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =====================================================
-- TABELLA DETTAGLIO_PREVENTIVO
-- =====================================================

CREATE TABLE dettaglio_preventivo (

    id_dettaglio BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_preventivo BIGINT NOT NULL,

    id_servizio BIGINT NOT NULL,

    quantita INT NOT NULL DEFAULT 1,

    CONSTRAINT fk_dettaglio_preventivo
        FOREIGN KEY (id_preventivo)
        REFERENCES preventivo(id_preventivo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_dettaglio_servizio
        FOREIGN KEY (id_servizio)
        REFERENCES servizio(id_servizio)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =====================================================
-- TABELLA PRENOTAZIONE
-- =====================================================

CREATE TABLE prenotazione (

    id_prenotazione BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_preventivo BIGINT NOT NULL UNIQUE,

    data_intervento DATETIME NOT NULL,

    indirizzo VARCHAR(200) NOT NULL,

    stato ENUM(
        'PROGRAMMATA',
        'CONFERMATA',
        'COMPLETATA',
        'ANNULLATA'
    ) NOT NULL DEFAULT 'PROGRAMMATA',

    CONSTRAINT fk_prenotazione_preventivo
        FOREIGN KEY (id_preventivo)
        REFERENCES preventivo(id_preventivo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- =====================================================
-- TABELLA RECENSIONE
-- =====================================================

CREATE TABLE recensione (

    id_recensione BIGINT AUTO_INCREMENT PRIMARY KEY,

    id_prenotazione BIGINT NOT NULL UNIQUE,

    voto TINYINT NOT NULL,

    commento TEXT,

    data_recensione DATETIME NOT NULL,

    CONSTRAINT chk_voto
        CHECK (voto BETWEEN 1 AND 5),

    CONSTRAINT fk_recensione_prenotazione
        FOREIGN KEY (id_prenotazione)
        REFERENCES prenotazione(id_prenotazione)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);