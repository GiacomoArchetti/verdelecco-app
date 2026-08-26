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

INSERT INTO preventivo (id_utente, indirizzo, superficie_mq, costo_stimato, descrizione, data_intervento, data_emissione, data_scadenza, stato)
VALUES 
    (@utente_id, 'Via Roma 15, Milano', 100.00, 180.00, 'Prato molto alto', '2026-01-15 09:00:00', '2026-01-10', '2026-01-20', 'IN_ATTESA'),
    (@utente_id, 'Via Roma 15, Milano', 50.00, 200.00, 'Siepe di lauro ceraso', '2026-02-10 14:00:00', '2026-02-01', '2026-02-15', 'ACCETTATO'),
    (@utente_id, 'Via Corso Italia 3, Milano', 200.00, 1000.00, 'Semina nuovo manto erboso', '2026-02-20 10:30:00', '2026-02-15', '2026-02-28', 'ACCETTATO'),
    (@utente_id, 'Via Roma 15, Milano', 80.00, 144.00, 'Manutenzione ordinaria', '2026-02-25 11:00:00', '2026-02-18', '2026-03-01', 'IN_ATTESA'),
    (@utente_id, 'Via Roma 15, Milano', 30.00, 60.00, 'Pulizia cortile', '2026-03-01 16:00:00', '2026-02-20', '2026-03-05', 'RIFIUTATO'),
    (@utente_id, 'Via Roma 15, Milano', 80.00, 144.00, 'Sfalcio scarpata laterale', '2026-02-25 11:00:00', '2026-02-18', '2026-03-01', 'IN_ATTESA'),
    (@utente_id, 'Via Milano 44, Milano', 60.00, 210.00, 'Potatura alberi da frutto', '2026-03-10 15:00:00', '2026-02-23', '2026-03-12', 'ACCETTATO')
ON DUPLICATE KEY UPDATE stato = VALUES(stato);


-- =====================================================
-- 3. DETTAGLIO PREVENTIVI (Associazioni con i nuovi ID servizio)
-- =====================================================

INSERT INTO dettaglio_preventivo (id_preventivo, id_servizio, quantita)
VALUES 
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Prato molto alto' AND id_utente = @utente_id LIMIT 1), 1, 1), -- MANUTENZIONE_TAPPETO_ERBOSO
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Siepe di lauro ceraso' AND id_utente = @utente_id LIMIT 1), 2, 1), -- POTATURA_SIEPI (o il nuovo ID della potatura siepi)
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Semina nuovo manto erboso' AND id_utente = @utente_id LIMIT 1), 5, 1), -- SEMINA (ID aggiornato)
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Manutenzione ordinaria' AND id_utente = @utente_id LIMIT 1), 1, 1), -- MANUTENZIONE_TAPPETO_ERBOSO
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Pulizia cortile' AND id_utente = @utente_id LIMIT 1), 6, 1), -- PULIZIA_GIARDINO (ID aggiornato)
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Sfalcio scarpata laterale' AND id_utente = @utente_id LIMIT 1), 4, 1), -- Scelto un ID servizio valido rimasto (es. 4)
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Potatura alberi da frutto' AND id_utente = @utente_id LIMIT 1), 3, 1)  -- POTATURA_ALBERI_DA_FRUTTO (ID aggiornato)
ON DUPLICATE KEY UPDATE quantita = VALUES(quantita);


-- =====================================================
-- 4. PRENOTAZIONI (6 record per testare la paginazione)
-- =====================================================

INSERT INTO prenotazione (id_preventivo, data_intervento, indirizzo, stato)
VALUES 
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Siepe di lauro ceraso' AND id_utente = @utente_id LIMIT 1), '2026-02-10 14:00:00', 'Via Roma 15, Milano', 'COMPLETATA'),
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Semina nuovo manto erboso' AND id_utente = @utente_id LIMIT 1), '2026-02-20 10:30:00', 'Via Corso Italia 3, Milano', 'PROGRAMMATA'),
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Sfalcio scarpata laterale' AND id_utente = @utente_id LIMIT 1), '2026-03-05 09:30:00', 'Via Roma 15, Milano', 'PROGRAMMATA'),
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Potatura alberi da frutto' AND id_utente = @utente_id LIMIT 1), '2026-03-10 15:00:00', 'Via Milano 44, Milano', 'COMPLETATA'),
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Prato molto alto' AND id_utente = @utente_id LIMIT 1), '2026-03-15 11:00:00', 'Via Roma 15, Milano', 'PROGRAMMATA'),
    ((SELECT id_preventivo FROM preventivo WHERE descrizione = 'Manutenzione ordinaria' AND id_utente = @utente_id LIMIT 1), '2026-03-20 09:00:00', 'Via Roma 15, Milano', 'PROGRAMMATA')
ON DUPLICATE KEY UPDATE stato = VALUES(stato);


-- =====================================================
-- 5. RECENSIONI
-- =====================================================

INSERT INTO recensione (id_prenotazione, voto, commento, data_recensione)
VALUES 
    (
        (SELECT p.id_prenotazione 
         FROM prenotazione p 
         JOIN preventivo pr ON p.id_preventivo = pr.id_preventivo 
         WHERE pr.descrizione = 'Siepe di lauro ceraso' AND pr.id_utente = @utente_id 
         LIMIT 1), 
        5, 
        'Servizio eccellente e puntualissimo!', 
        NOW()
    )
ON DUPLICATE KEY UPDATE voto = VALUES(voto);