-- =====================================================
-- INSERIMENTO ADMIN
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, guest, ruolo)
VALUES
('Marco', 'Archetti', 'admin@giardiniere.it', 'giardinobello', '+39 347 3849453', 1, 0, 'ADMIN');


-- =====================================================
-- CLIENTI (20)
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, guest, ruolo)
VALUES
('Massimo', 'Cigno', 'massimo.cigno@email.it', 'rosmarino', '+39 335 1234567', 1, 0, 'UTENTE'),
('Mario', 'Rossi', 'mario.rossi@email.it', 'albero', '+39 347 2849103', 1, 0, 'UTENTE'),
('Luca', 'Bianchi', 'luca.bianchi@email.it', 'crisantemo', '+39 334 8920145', 1, 0, 'UTENTE'),
('Anna', 'Verdi', 'anna.verdi@email.it', 'geranio', '+39 340 5193820', 1, 0, 'UTENTE'),
('Paolo', 'Ferrari', 'paolo.ferrari@email.it', 'mandorle', '+39 328 7304912', 1, 0, 'UTENTE'),
('Giulia', 'Romano', 'giulia.romano@email.it', 'anacardi', '+39 338 6412957', 1, 0, 'UTENTE'),
('Marco', 'Esposito', 'marco.esposito@email.it', 'girasoli', '+39 349 1058392', 1, 0, 'UTENTE'),
('Sara', 'Russo', 'sara.russo@email.it', 'telefono', '+39 333 4729104', 1, 0, 'UTENTE'),
('Davide', 'Conti', 'davide.conti@email.it', 'prato', '+39 366 9283014', 1, 0, 'UTENTE'),
('Elena', 'Marino', 'elena.marino@email.it', 'piazzetta', '+39 329 8401923', 1, 0, 'UTENTE'),
('Stefano', 'Ricci', 'stefano.ricci@email.it', 'azalea', '+39 331 4567890', 1, 0, 'UTENTE'),
('Chiara', 'Bruno', 'chiara.bruno@email.it', 'tulipano', '+39 342 9876543', 1, 0, 'UTENTE'),
('Alessandro', 'Moretti', 'alessandro.moretti@email.it', 'ficus', '+39 339 6543210', 1, 0, 'UTENTE'),
('Francesca', 'Barbieri', 'francesca.barbieri@email.it', 'orchidea', '+39 320 1122334', 1, 0, 'UTENTE'),
('Andrea', 'Lombardi', 'andrea.lombardi@email.it', 'bonsai', '+39 348 9988776', 1, 0, 'UTENTE'),
('Valentina', 'Santoro', 'valentina.santoro@email.it', 'lavanda', '+39 337 4455667', 1, 0, 'UTENTE'),
('Roberto', 'Galli', 'roberto.galli@email.it', 'pino', '+39 360 3322114', 1, 0, 'UTENTE'),
('Federica', 'Costa', 'federica.costa@email.it', 'margherita', '+39 327 7788990', 1, 0, 'UTENTE'),
('Simone', 'Fontana', 'simone.fontana@email.it', 'edera', '+39 345 6677889', 1, 0, 'UTENTE'),
('Martina', 'Greco', 'martina.greco@email.it', 'mimosa', '+39 333 1122445', 1, 0, 'UTENTE');


-- =====================================================
-- SERVIZI (6)
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
-- PREVENTIVI (30)
-- =====================================================

INSERT INTO preventivo
(id_utente, indirizzo, superficie_mq, costo_stimato, descrizione, data_intervento, data_emissione, data_scadenza, stato)
VALUES
(2, 'Via Milano 20 Roma', 200, 360.00, 'Taglio giardino e rifinitura bordi', '2026-08-02 10:00:00', '2026-07-20', '2026-07-27', 'ACCETTATO'),
(3, 'Via Torino 15 Torino', 80, 320.00, 'Potatura siepi di lauroceraso', '2026-08-03 08:30:00', '2026-07-21', '2026-07-28', 'ACCETTATO'),
(4, 'Via Firenze 8 Firenze', 15, 33.00, 'Semina prato piccolo cortile', '2026-08-04 09:30:00', '2026-07-22', '2026-07-29', 'ACCETTATO'),
(5, 'Via Venezia 5 Venezia', 90, 252.00, 'Pulizia giardino autunnale', '2026-08-05 11:00:00', '2026-07-23', '2026-07-30', 'ACCETTATO'),
(6, 'Via Napoli 30 Napoli', 300, 540.00, 'Taglio erba area verde condominiale', '2026-08-06 07:30:00', '2026-07-24', '2026-07-31', 'ACCETTATO'),
(7, 'Via Bari 12 Bari', 60, 270.00, 'Potatura alberi ornamentali', '2026-08-07 10:00:00', '2026-07-25', '2026-08-01', 'ACCETTATO'),
(8, 'Via Genova 25 Genova', 80, 224.00, 'Manutenzione e pulizia generale', '2026-08-08 09:00:00', '2026-07-26', '2026-08-02', 'ACCETTATO'),
(9, 'Via Palermo 18 Palermo', 220, 616.00, 'Pulizia completa e rimozione sterpaglie', '2026-08-09 08:00:00', '2026-07-27', '2026-08-03', 'ACCETTATO'),
(4, 'Via Como 7 Como', 110, 242.00, 'Preparazione terreno e semina', '2026-08-10 09:00:00', '2026-07-28', '2026-08-04', 'ACCETTATO'),
(3, 'Via Lecco 13 Lecco', 15, 27.00, 'Taglio prato e manutenzione', '2026-08-11 09:00:00', '2026-07-29', '2026-08-05', 'ACCETTATO'),
(2, 'Via Roma 101 Roma', 120, 216.00, 'Manutenzione prato periodica', '2026-08-12 09:00:00', '2026-07-30', '2026-08-06', 'ACCETTATO'),
(3, 'Corso Francia 45 Torino', 150, 600.00, 'Sagomatura e potatura siepi', '2026-08-14 10:30:00', '2026-07-31', '2026-08-07', 'ACCETTATO'),
(4, 'Via Bologna 12 Firenze', 90, 315.00, 'Potatura frutteto estivo', '2026-08-18 08:00:00', '2026-08-01', '2026-08-08', 'ACCETTATO'),
(5, 'Piazza San Marco 3 Venezia', 200, 900.00, 'Potatura alberi ornamentali di alto fusto', '2026-08-21 14:00:00', '2026-08-02', '2026-08-09', 'ACCETTATO'),
(6, 'Via Toledo 50 Napoli', 110, 440.00, 'Potatura siepe perimetrale', '2026-08-24 09:30:00', '2026-08-03', '2026-08-10', 'ACCETTATO'),
(7, 'Corso Vittorio 14 Bari', 130, 286.00, 'Risemina prato ed erba folta', '2026-09-18 11:00:00', '2026-08-04', '2026-08-11', 'ACCETTATO'),
(8, 'Via Garibaldi 88 Genova', 250, 700.00, 'Pulizia totale parco e aiuole', '2026-09-24 08:30:00', '2026-08-05', '2026-08-12', 'ACCETTATO'),
(9, 'Via Liberta 22 Palermo', 160, 288.00, 'Manutenzione prato residenziale', '2026-09-29 10:00:00', '2026-08-06', '2026-08-13', 'ACCETTATO'),
(2, 'Via Appia 33 Roma', 180, 324.00, 'Taglio erba e diserbo manuale', '2026-10-05 09:00:00', '2026-08-07', '2026-08-14', 'ACCETTATO'),
(4, 'Via Pistoia 7 Firenze', 100, 350.00, 'Potatura alberi da frutto', '2026-10-12 15:00:00', '2026-08-08', '2026-08-15', 'ACCETTATO'),
(5, 'Via Dante 18 Bergamo', 95, 266.00, 'Pulizia cortile interno', '2026-10-15 10:00:00', '2026-08-09', '2026-08-16', 'IN_ATTESA'),
(6, 'Corso Italia 12 Milano', 140, 252.00, 'Manutenzione manto erboso', '2026-10-20 09:00:00', '2026-08-10', '2026-08-17', 'IN_ATTESA'),
(7, 'Via Cavour 4 Genova', 85, 340.00, 'Rifilatura siepi e arbusti', '2026-10-25 11:30:00', '2026-08-11', '2026-08-18', 'IN_ATTESA'),
(8, 'Via Mazzini 30 Padova', 170, 476.00, 'Pulizia radici e ramaglie', '2026-11-02 08:30:00', '2026-08-12', '2026-08-19', 'ACCETTATO'),
(9, 'Via Garibaldi 5 Monza', 100, 220.00, 'Semina manto erboso ad alta resistenza', '2026-11-10 14:00:00', '2026-08-13', '2026-08-20', 'IN_ATTESA'),
(10, 'Via Trento 16 Verona', 75, 270.00, 'Potatura alberi ornamentali', '2026-11-15 09:30:00', '2026-08-14', '2026-08-21', 'ACCETTATO'),
(11, 'Via Sardegna 9 Cagliari', 140, 308.00, 'Risemina e manutenzione prato', '2026-11-20 10:00:00', '2026-08-15', '2026-08-22', 'ACCETTATO'),
(12, 'Via Etnea 44 Catania', 60, 168.00, 'Pulizia area verde privata', '2026-11-25 08:00:00', '2026-08-16', '2026-08-23', 'RIFIUTATO'),
(13, 'Via Dante 27 Padova', 180, 630.00, 'Potatura alberi da frutto invernale', '2026-12-02 14:00:00', '2026-08-17', '2026-08-24', 'IN_ATTESA'),
(14, 'Via Verdi 6 Parma', 90, 252.00, 'Pulizia stagionale del giardino', '2026-12-08 09:00:00', '2026-08-18', '2026-08-25', 'ACCETTATO'),
(15, 'Via Roma 72 Perugia', 50, 200.00, 'Sagomatura siepe di confine', '2026-12-15 11:00:00', '2026-08-19', '2026-08-26', 'SCADUTO');


-- =====================================================
-- DETTAGLI_PREVENTIVO (30)
-- =====================================================

INSERT INTO dettaglio_preventivo
(id_preventivo, id_servizio, quantita)
VALUES
(1, 1, 200),
(2, 4, 80),
(3, 5, 15),
(4, 6, 90),
(5, 1, 300),
(6, 3, 60),
(7, 6, 80),
(8, 6, 220),
(9, 5, 110),
(10, 1, 15),
(11, 1, 120),
(12, 4, 150),
(13, 2, 90),
(14, 3, 200),
(15, 4, 110),
(16, 5, 130),
(17, 6, 250),
(18, 1, 160),
(19, 1, 180),
(20, 2, 100),
(21, 6, 95),
(22, 1, 140),
(23, 4, 85),
(24, 6, 170),
(25, 5, 100),
(26, 3, 75),
(27, 5, 140),
(28, 6, 60),
(29, 2, 180),
(30, 4, 50);


-- =====================================================
-- PRENOTAZIONI (18)
-- =====================================================

INSERT INTO prenotazione
(id_preventivo, data_intervento, indirizzo, stato)
VALUES
-- 12 interventi completati, utilizzati dalle recensioni
(1, '2026-08-02 10:00:00', 'Via Milano 20 Roma', 'COMPLETATA'),
(2, '2026-08-03 08:30:00', 'Via Torino 15 Torino', 'COMPLETATA'),
(3, '2026-08-04 09:30:00', 'Via Firenze 8 Firenze', 'COMPLETATA'),
(4, '2026-08-05 11:00:00', 'Via Venezia 5 Venezia', 'COMPLETATA'),
(5, '2026-08-06 07:30:00', 'Via Napoli 30 Napoli', 'COMPLETATA'),
(6, '2026-08-07 10:00:00', 'Via Bari 12 Bari', 'COMPLETATA'),
(7, '2026-08-08 09:00:00', 'Via Genova 25 Genova', 'COMPLETATA'),
(8, '2026-08-09 08:00:00', 'Via Palermo 18 Palermo', 'COMPLETATA'),
(9, '2026-08-10 09:00:00', 'Via Como 7 Como', 'COMPLETATA'),
(10, '2026-08-11 09:00:00', 'Via Lecco 13 Lecco', 'COMPLETATA'),
(11, '2026-08-12 09:00:00', 'Via Roma 101 Roma', 'COMPLETATA'),
(12, '2026-08-14 10:30:00', 'Corso Francia 45 Torino', 'COMPLETATA'),
-- 2 interventi annullati e 4 programmati
(13, '2026-08-18 08:00:00', 'Via Bologna 12 Firenze', 'ANNULLATA'),
(14, '2026-08-21 14:00:00', 'Piazza San Marco 3 Venezia', 'ANNULLATA'),
(16, '2026-09-18 11:00:00', 'Corso Vittorio 14 Bari', 'PROGRAMMATA'),
(17, '2026-09-24 08:30:00', 'Via Garibaldi 88 Genova', 'PROGRAMMATA'),
(18, '2026-09-29 10:00:00', 'Via Liberta 22 Palermo', 'PROGRAMMATA'),
(19, '2026-10-05 09:00:00', 'Via Appia 33 Roma', 'PROGRAMMATA');


-- =====================================================
-- RECENSIONI (12)
-- =====================================================

INSERT INTO recensione
(id_prenotazione, voto, commento, data_recensione)
VALUES
(1, 5, 'Servizio impeccabile e personale estremamente preparato.', '2026-08-03 18:00:00'),
(2, 4, 'Lavoro puntuale e giardino lasciato in perfetto ordine.', '2026-08-04 18:00:00'),
(3, 5, 'Puntuali, professionali e molto disponibili.', '2026-08-05 18:00:00'),
(4, 5, 'Rapporto qualita prezzo eccellente.', '2026-08-06 18:00:00'),
(5, 5, 'Precisi e attenti alle richieste del cliente.', '2026-08-07 18:00:00'),
(6, 4, 'Attrezzatura professionale e lavoro eseguito in sicurezza.', '2026-08-08 18:00:00'),
(7, 5, 'Un punto di riferimento per la cura del verde.', '2026-08-09 18:00:00'),
(8, 5, 'Disponibili, cortesi e con grande passione per il lavoro.', '2026-08-10 18:00:00'),
(9, 4, 'Intervento rapido e risultato molto soddisfacente.', '2026-08-11 18:00:00'),
(10, 5, 'Rifinitura precisa e giardino finalmente ordinato.', '2026-08-12 18:00:00'),
(11, 5, 'Servizio chiaro, veloce e professionale.', '2026-08-13 18:00:00'),
(12, 5, 'Ottima esperienza, lavoro eseguito con grande attenzione.', '2026-08-15 18:00:00');