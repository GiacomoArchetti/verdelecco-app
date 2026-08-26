-- =====================================================
-- INSERIMENTO ADMIN
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, guest, ruolo)
VALUES
('Marco','Archetti','admin@giardiniere.it','giardinobello','+39 347 3849453', 1, 0, 'ADMIN');


-- =====================================================
-- UTENTI (9)
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, guest, ruolo)
VALUES
('Mario', 'Rossi', 'mario.rossi@email.it', 'albero', '+39 347 2849103', 1, 0, 'UTENTE'),
('Luca', 'Bianchi', 'luca.bianchi@email.it', 'crisantemo', '+39 334 8920145', 1, 0, 'UTENTE'),
('Anna', 'Verdi', 'anna.verdi@email.it', 'geranio', '+39 340 5193820', 1, 0, 'UTENTE'),
('Paolo', 'Ferrari', 'paolo.ferrari@email.it', 'mandorle', '+39 328 7304912', 1, 0, 'UTENTE'),
('Giulia', 'Romano', 'giulia.romano@email.it', 'anacardi', '+39 338 6412957', 1, 0, 'UTENTE'),
('Marco', 'Esposito', 'marco.esposito@email.it', 'girasoli', '+39 349 1058392', 1, 0, 'UTENTE'),
('Sara', 'Russo', 'sara.russo@email.it', 'telefono', '+39 333 4729104', 1, 0, 'UTENTE'),
('Davide', 'Conti', 'davide.conti@email.it', 'prato', '+39 366 9283014', 1, 0, 'UTENTE'),
('Elena', 'Marino', 'elena.marino@email.it', 'piazzetta', '+39 329 8401923', 1, 0, 'UTENTE');


-- =====================================================
-- SERVIZI (7)
-- =====================================================

INSERT INTO servizio
(nome, prezzo_al_mq, minuti_al_mq, descrizione, attivo)
VALUES
('MANUTENZIONE_TAPPETO_ERBOSO', 1.80, 2, 'Taglio del prato con rifinitura dei bordi per un manto erboso sempre curato, sano e in ordine.', 1),
('POTATURA_ALBERI_DA_FRUTTO', 3.50, 5, 'Potatura specifica per migliorare la produzione dei frutti e mantenere l''albero in salute.', 1),
('POTATURA_ALBERI_ORNAMENTALI', 4.50, 7, 'Cura della chioma e rimozione dei rami secchi per garantire sicurezza ed estetica alla pianta.', 1),
('POTATURA_SIEPI', 4.00, 6, 'Rifilatura e sagomatura di siepi perimetrali per mantenere un aspetto ordinato e compatto.', 1),
('SEMINA', 2.20, 3, 'Preparazione del terreno e semina per far crescere un nuovo prato folto e resistente.', 1),
('PULIZIA_GIARDINO', 2.80, 4, 'Pulizia generale del giardino con eliminazione delle erbacce e rimozione delle ramaglie.', 1);


-- =====================================================
-- PREVENTIVI (19)
-- =====================================================

INSERT INTO preventivo
(id_utente, indirizzo, superficie_mq, costo_stimato, descrizione,
data_intervento, data_emissione, data_scadenza, stato)
VALUES

(2,'Via Milano 20 Roma', 200, 3600,
'Taglio giardino',
'2026-08-02 10:00:00',
'2026-07-30',
'2026-08-06',
'IN_ATTESA'),

(3,'Via Torino 15 Torino', 80, 2000,
'Potatura siepi',
'2026-08-03 08:30:00',
'2026-07-30',
'2026-08-07',
'ACCETTATO'),

(4,'Via Firenze 8 Firenze', 15, 330,
'Semina prato',
'2026-08-04 09:30:00',
'2026-07-30',
'2026-08-08',
'RIFIUTATO'),

(5,'Via Venezia 5 Venezia', 90, 1500,
'Pulizia giardino',
'2026-11-05 11:00:00',
'2026-07-30',
'2026-08-09',
'ACCETTATO'),

(6,'Via Napoli 30 Napoli', 300, 5400,
'Taglio area verde',
'2026-09-06 07:30:00',
'2026-07-30',
'2026-08-10',
'IN_ATTESA'),

(7,'Via Bari 12 Bari', 60, 1800,
'Potatura',
'2026-08-07 10:00:00',
'2026-07-30',
'2026-08-11',
'ACCETTATO'),

(8,'Via Genova 25 Genova', 80,450,
'Manutenzione',
'2026-08-08 09:00:00',
'2026-07-30',
'2026-08-23',
'SCADUTO'),

(9,'Via Palermo 18 Palermo',220,3500,
'Pulizia completa',
'2026-08-09 08:00:00',
'2026-07-30',
'2026-08-13',
'ANNULLATO'),

(4,'Via Como 7 Como',110,2500,
'Taglio e semina',
'2026-08-10 09:00:00',
'2026-07-30',
'2026-08-14',
'ACCETTATO'),

(3,'Via Lecco 13 Lecco', 15, 220,
'Taglio e semina',
'2026-08-02 09:00:00',
'2026-07-30',
'2026-08-17',
'ACCETTATO'),

-- 10 NUOVI PREVENTIVI ACCETTATI (Agosto, Settembre, Ottobre 2026)
(2, 'Via Roma 101 Roma', 120, 216.00, 'Manutenzione prato', '2026-08-28 09:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(3, 'Corso Francia 45 Torino', 150, 600.00, 'Manutenzione prato', '2026-08-31 10:30:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(4, 'Via Bologna 12 Firenze', 90, 315.00, 'Potatura alberi frutta', '2026-09-03 08:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(5, 'Piazza San Marco 3 Venezia', 200, 900.00, 'Potatura ornamentale', '2026-09-08 14:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(6, 'Via Toledo 50 Napoli', 110, 440.00, 'Potatura siepi', '2026-09-11 09:30:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(7, 'Corso Vittorio 14 Bari', 130, 286.00, 'Semina prato', '2026-09-18 11:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(8, 'Via Garibaldi 88 Genova', 250, 700.00, 'Pulizia giardino', '2026-09-24 08:30:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(9, 'Via Libertà 22 Palermo', 160, 288.00, 'Manutenzione prato', '2026-09-29 10:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(2, 'Via Appia 33 Roma', 180, 720.00, 'Manutenzione prato', '2026-10-05 09:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO'),
(4, 'Via Pistoia 7 Firenze', 100, 350.00, 'Potatura alberi frutta', '2026-10-12 15:00:00', '2026-08-26', '2026-09-02', 'ACCETTATO');


-- =====================================================
-- DETTAGLI_PREVENTIVO (19)
-- =====================================================

INSERT INTO dettaglio_preventivo
(id_preventivo, id_servizio, quantita)
VALUES
(1, 1, 200),
(2, 1, 80),
(3, 3, 150),
(4, 4, 90),
(5, 1, 300),
(6, 1, 60),
(7, 3, 170),
(8, 4, 220),
(9, 3, 110),
(11, 1, 120),
(12, 1, 150),
(13, 3, 90),
(14, 4, 200),
(15, 5, 110),
(16, 6, 130),
(17, 6, 250),
(18, 1, 160),
(19, 1, 180),
(20, 3, 100);



-- =====================================================
-- PRENOTAZIONI (15)
-- =====================================================

INSERT INTO prenotazione
(id_preventivo,data_intervento,indirizzo,stato)
VALUES

(2,'2026-08-03 08:30:00','Via Torino 15 Torino','COMPLETATA'),

(3,'2026-08-02 09:00:00','Via Lecco 13 Lecco','COMPLETATA'),

(4,'2026-11-05 11:00:00','Via Venezia 5 Venezia','PROGRAMMATA'),

(6,'2026-08-07 10:00:00','Via Bari 12 Bari','COMPLETATA'),

(9,'2026-08-10 09:00:00','Via Como 7 Como','ANNULLATA'),

-- Prenotazioni collegate ai 10 nuovi preventivi accettati
(11, '2026-08-28 09:00:00', 'Via Roma 101 Roma', 'PROGRAMMATA'),
(12, '2026-08-31 10:30:00', 'Corso Francia 45 Torino', 'PROGRAMMATA'),
(13, '2026-09-03 08:00:00', 'Via Bologna 12 Firenze', 'PROGRAMMATA'),
(14, '2026-09-08 14:00:00', 'Piazza San Marco 3 Venezia', 'PROGRAMMATA'),
(15, '2026-09-11 09:30:00', 'Via Toledo 50 Napoli', 'PROGRAMMATA'),
(16, '2026-09-18 11:00:00', 'Corso Vittorio 14 Bari', 'PROGRAMMATA'),
(17, '2026-09-24 08:30:00', 'Via Garibaldi 88 Genova', 'PROGRAMMATA'),
(18, '2026-09-29 10:00:00', 'Via Libertà 22 Palermo', 'PROGRAMMATA'),
(19, '2026-10-05 09:00:00', 'Via Appia 33 Roma', 'PROGRAMMATA'),
(20, '2026-10-12 15:00:00', 'Via Pistoia 7 Firenze', 'PROGRAMMATA');


-- =====================================================
-- RECENSIONI (3)
-- =====================================================

INSERT INTO recensione
(id_prenotazione,voto,commento,data_recensione)
VALUES

(2, 5, 'Servizio impeccabile e personale estremamente preparato. Mi hanno seguito passo dopo passo con grande attenzione ai dettagli. Super consigliato!', '2026-08-04 18:00:00'),

(3, 4, 'Ottima esperienza complessiva. Marco è sicuramente molto preparato e sa dove mettere le mani.', '2026-08-09 11:00:00'),

(6, 5, 'Li ho contattati per un preventivo e un sopralluogo prima di sistemare il giardino. Sono stati puntualissimi e mi hanno dato ottimi consigli sulla cura delle piante. Lavoro pulito e professionale, consigliatissimo a tutti!', '2026-08-06 18:00:00');