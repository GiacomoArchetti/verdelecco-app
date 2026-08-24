-- =====================================================
-- 1. UTENTE CLIENTE DI TEST
-- =====================================================

INSERT INTO utente (nome, cognome, email, password, telefono, attivo, ruolo)
VALUES (
    'Giacomo',
    'Archetti',
    'archettigiacomoarchetti@gmail.com',
    'G14c0m0!01',
    '+39 333 9876543',
    1,
    'UTENTE'
)
ON DUPLICATE KEY UPDATE 
    nome = 'Giacomo',
    cognome = 'Archetti',
    telefono = '+39 333 9876543';

SET @utente_id = (SELECT id_utente FROM utente WHERE email = 'archettigiacomoarchetti@gmail.com');


-- =====================================================
-- 2. PREVENTIVI (7 record per testare la paginazione)
-- =====================================================

INSERT INTO preventivo (id_preventivo, id_utente, indirizzo, superficie_mq, costo_stimato, descrizione, data_intervento, data_emissione, data_scadenza, stato)
VALUES 
    (101, @utente_id, 'Via Roma 15, Milano', 100.00, 180.00, 'Prato molto alto', '2026-01-15 09:00:00', '2026-01-10', '2026-01-20', 'IN_ATTESA'),
    (102, @utente_id, 'Via Roma 15, Milano', 50.00, 200.00, 'Siepe di lauro ceraso', '2026-02-10 14:00:00', '2026-02-01', '2026-02-15', 'ACCETTATO'),
    (103, @utente_id, 'Via Corso Italia 3, Milano', 200.00, 1000.00, 'Semina nuovo manto erboso', '2026-02-20 10:30:00', '2026-02-15', '2026-02-28', 'ACCETTATO'),
    (104, @utente_id, 'Via Roma 15, Milano', 80.00, 144.00, 'Manutenzione ordinaria', '2026-02-25 11:00:00', '2026-02-18', '2026-03-01', 'IN_ATTESA'),
    (105, @utente_id, 'Via Roma 15, Milano', 30.00, 60.00, 'Pulizia cortile', '2026-03-01 16:00:00', '2026-02-20', '2026-03-05', 'RIFIUTATO'),
    (106, @utente_id, 'Via Roma 15, Milano', 120.00, 240.00, 'Sfalcio scarpata laterale', '2026-03-05 09:30:00', '2026-02-22', '2026-03-10', 'ACCETTATO'),
    (107, @utente_id, 'Via Milano 44, Milano', 60.00, 210.00, 'Potatura alberi da frutto', '2026-03-10 15:00:00', '2026-02-23', '2026-03-12', 'ACCETTATO')
ON DUPLICATE KEY UPDATE stato = VALUES(stato);


-- =====================================================
-- 3. DETTAGLIO PREVENTIVI (Associazioni con i nuovi ID servizio)
-- =====================================================

INSERT INTO dettaglio_preventivo (id_dettaglio, id_preventivo, id_servizio, quantita)
VALUES 
    (1, 101, 1, 1), -- MANUTENZIONE_TAPPETO_ERBOSO
    (2, 102, 2, 1), -- POTATURA_SIEPI
    (3, 103, 6, 1), -- SEMINA
    (4, 104, 1, 1), -- MANUTENZIONE_TAPPETO_ERBOSO
    (5, 105, 7, 1), -- PULIZIA_GIARDINO
    (6, 106, 5, 1), -- SFALCIO_RIVE_E_SCARPATE
    (7, 107, 3, 1)  -- POTATURA_ALBERI_DA_FRUTTO
ON DUPLICATE KEY UPDATE quantita = VALUES(quantita);


-- =====================================================
-- 4. PRENOTAZIONI (6 record per testare la paginazione)
-- =====================================================

INSERT INTO prenotazione (id_prenotazione, id_preventivo, data_intervento, indirizzo, stato)
VALUES 
    (201, 102, '2026-02-10 14:00:00', 'Via Roma 15, Milano', 'COMPLETATA'),
    (202, 103, '2026-02-20 10:30:00', 'Via Corso Italia 3, Milano', 'CONFERMATA'),
    (203, 106, '2026-03-05 09:30:00', 'Via Roma 15, Milano', 'CONFERMATA'),
    (204, 107, '2026-03-10 15:00:00', 'Via Milano 44, Milano', 'PROGRAMMATA'),
    (205, 101, '2026-03-15 11:00:00', 'Via Roma 15, Milano', 'PROGRAMMATA'),
    (206, 104, '2026-03-20 09:00:00', 'Via Roma 15, Milano', 'PROGRAMMATA')
ON DUPLICATE KEY UPDATE stato = VALUES(stato);


-- =====================================================
-- 5. RECENSIONI
-- =====================================================

INSERT INTO recensione (id_recensione, id_prenotazione, voto, commento, data_recensione)
VALUES 
    (301, 201, 5, 'Servizio eccellente e puntualissimo!', NOW())
ON DUPLICATE KEY UPDATE voto = VALUES(voto);