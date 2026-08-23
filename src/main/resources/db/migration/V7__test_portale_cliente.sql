-- =====================================================
-- 1. AGGIORNAMENTO ENUM E MIGRAZIONE DATI TABELLA SERVIZIO
-- =====================================================

-- Step 1: Espansione dell'ENUM per accogliere sia vecchi che nuovi valori
ALTER TABLE servizio 
MODIFY COLUMN nome ENUM(
    'TAGLIO_ERBA',
    'POTATURA',
    'SEMINA',
    'PULIZIA_GIARDINO',
    'MANUTENZIONE_TAPPETO_ERBOSO',
    'SFALCIO_RIVE_E_SCARPATE',
    'POTATURA_ALBERI_DA_FRUTTO',
    'POTATURA_ALBERI_ORNAMENTALI',
    'POTATURA_SIEPI'
) NOT NULL;

-- Step 1.1: Disattiva i vecchi servizi generici
UPDATE servizio 
SET attivo = 0 
WHERE nome IN ('TAGLIO_ERBA', 'POTATURA');

-- Step 2: Riconversione dei record esistenti sui nuovi tipi
UPDATE servizio SET nome = 'MANUTENZIONE_TAPPETO_ERBOSO' WHERE nome = 'TAGLIO_ERBA';

UPDATE servizio 
SET nome = 'POTATURA_SIEPI' 
WHERE nome = 'POTATURA' AND (descrizione LIKE '%siepe%' OR descrizione LIKE '%siepi%');

UPDATE servizio 
SET nome = 'POTATURA_ALBERI_DA_FRUTTO' 
WHERE nome = 'POTATURA' AND (descrizione LIKE '%albero%' OR descrizione LIKE '%alberi%');

-- Fallback per eventuali rimanenze
UPDATE servizio 
SET nome = 'POTATURA_SIEPI'
WHERE nome = 'POTATURA';

-- Step 3: Restrizione dell'ENUM ai soli valori definitivi
ALTER TABLE servizio
MODIFY COLUMN nome ENUM(
    'MANUTENZIONE_TAPPETO_ERBOSO',
    'SFALCIO_RIVE_E_SCARPATE',
    'POTATURA_ALBERI_DA_FRUTTO',
    'POTATURA_ALBERI_ORNAMENTALI',
    'POTATURA_SIEPI',
    'SEMINA',
    'PULIZIA_GIARDINO'
) NOT NULL;

-- Step 4: Inserimento / Aggiornamento Servizi Professionali
INSERT INTO servizio (id_servizio, nome, prezzo_al_mq, minuti_al_mq, descrizione, attivo) VALUES
(1, 'MANUTENZIONE_TAPPETO_ERBOSO', 1.80, 2, 'Manutenzione e taglio prato', 1),
(2, 'POTATURA_SIEPI', 4.00, 6, 'Potatura siepi', 1),
(3, 'POTATURA_ALBERI_DA_FRUTTO', 3.50, 5, 'Potatura alberi da frutto', 1),
(4, 'POTATURA_ALBERI_ORNAMENTALI', 4.50, 7, 'Potatura alberi ornamentali', 1),
(5, 'SFALCIO_RIVE_E_SCARPATE', 4.00, 3, 'Sfalcio di rive e scarpate', 1),
(6, 'SEMINA', 5.00, 10, 'Semina e concimazione terreno', 1),
(7, 'PULIZIA_GIARDINO', 2.00, 4, 'Pulizia foglie e sgombero ramaglie', 1)
ON DUPLICATE KEY UPDATE 
    prezzo_al_mq = VALUES(prezzo_al_mq),
    minuti_al_mq = VALUES(minuti_al_mq),
    descrizione = VALUES(descrizione),
    attivo = VALUES(attivo);


-- =====================================================
-- 2. UTENTE CLIENTE DI TEST
-- =====================================================

INSERT INTO utente (nome, cognome, email, password, telefono, attivo, ruolo)
VALUES (
    'Giacomo',
    'Archetti',
    'archettigiacomoarchetti@gmail.com',
    '$2a$10$wT2HnK.E2x/g2G/sD1pTce6k2q7Fz3f7E8H0wI3k4m5n6o7p8q9r0',
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
-- 3. PREVENTIVI (7 record per testare la paginazione)
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
-- 4. DETTAGLIO PREVENTIVI (Associazioni con i nuovi ID servizio)
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
-- 5. PRENOTAZIONI (6 record per testare la paginazione)
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
-- 6. RECENSIONI
-- =====================================================

INSERT INTO recensione (id_recensione, id_prenotazione, voto, commento, data_recensione)
VALUES 
    (301, 201, 5, 'Servizio eccellente e puntualissimo!', NOW())
ON DUPLICATE KEY UPDATE voto = VALUES(voto);