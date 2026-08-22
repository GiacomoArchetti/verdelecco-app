
-- =====================================================
-- INSERIMENTO ADMIN
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, ruolo)
VALUES
('Admin','Marco','admin@giardiniere.it','giardinobello','3330000000',1,'ADMIN');


-- =====================================================
-- UTENTI
-- =====================================================

INSERT INTO utente
(nome, cognome, email, password, telefono, attivo, ruolo)
VALUES
('Mario', 'Rossi', 'mario.rossi@email.it', 'albero', '3331111111', 1, 'UTENTE'),
('Luca', 'Bianchi', 'luca.bianchi@email.it', 'crisantemo', '3332222222', 1, 'UTENTE'),
('Anna', 'Verdi', 'anna.verdi@email.it', 'geranio', '3333333333', 1, 'UTENTE'),
('Paolo', 'Ferrari', 'paolo.ferrari@email.it', 'mandorle', '3334444444', 1, 'UTENTE'),
('Giulia', 'Romano', 'giulia.romano@email.it', 'anacardi', '3335555555', 1, 'UTENTE'),
('Marco', 'Esposito', 'marco.esposito@email.it', 'girasoli', '3336666666', 1, 'UTENTE'),
('Sara', 'Russo', 'sara.russo@email.it', 'telefono', '3337777777', 1, 'UTENTE'),
('Davide', 'Conti', 'davide.conti@email.it', 'prato', '3338888888', 1, 'UTENTE'),
('Elena', 'Marino', 'elena.marino@email.it', 'piazzetta', '3339999999', 1, 'UTENTE');


-- =====================================================
-- SERVIZI (10)
-- =====================================================

INSERT INTO servizio
(nome, prezzo_al_mq, minuti_al_mq, descrizione, attivo)
VALUES
('TAGLIO_ERBA', 1.80, 2, 'Taglio prato', 1),
('POTATURA', 3.50, 5, 'Potatura alberi', 1),
('SEMINA', 2.20, 3, 'Preparazione del terreno, semina e rigenerazione del prato per ottenere un manto erboso denso, uniforme e resistente', 1),
('PULIZIA_GIARDINO', 2.80, 4, 'Pulizia generale, rimozione infestanti e cura stagionale per un giardino sempre in ordine e visivamente curato', 1);


-- =====================================================
-- PREVENTIVI (9)
-- =====================================================

INSERT INTO preventivo
(id_utente, indirizzo, superficie_mq, costo_stimato, descrizione,
data_intervento, data_emissione, data_scadenza, stato)
VALUES

(2,'Via Milano 20 Roma',200,360,
'Taglio giardino',
'2026-08-02 10:00:00',
'2026-07-30',
'2026-08-06',
'IN_ATTESA'),

(3,'Via Torino 15 Torino',80,280,
'Potatura siepi',
'2026-08-03 08:30:00',
'2026-07-30',
'2026-08-07',
'ACCETTATO'),

(4,'Via Firenze 8 Firenze',150,330,
'Semina prato',
'2026-08-04 09:30:00',
'2026-07-30',
'2026-08-08',
'RIFIUTATO'),

(5,'Via Venezia 5 Venezia',90,252,
'Pulizia giardino',
'2026-08-05 11:00:00',
'2026-07-30',
'2026-08-09',
'ACCETTATO'),

(6,'Via Napoli 30 Napoli',300,540,
'Taglio area verde',
'2026-08-06 07:30:00',
'2026-07-30',
'2026-08-10',
'IN_ATTESA'),

(7,'Via Bari 12 Bari',60,180,
'Potatura',
'2026-08-07 10:00:00',
'2026-07-30',
'2026-08-11',
'ACCETTATO'),

(8,'Via Genova 25 Genova',170,400,
'Manutenzione',
'2026-08-08 09:00:00',
'2026-07-30',
'2026-08-12',
'SCADUTO'),

(9,'Via Palermo 18 Palermo',220,500,
'Pulizia completa',
'2026-08-09 08:00:00',
'2026-07-30',
'2026-08-13',
'ANNULLATO'),

(10,'Via Como 7 Como',110,250,
'Taglio e semina',
'2026-08-10 09:00:00',
'2026-07-30',
'2026-08-14',
'ACCETTATO');


-- =====================================================
-- DETTAGLI_PREVENTIVO
-- =====================================================

INSERT INTO dettaglio_preventivo
(id_preventivo, id_servizio, quantita)
VALUES
(1, 1, 200),
(2, 2, 80),
(3, 3, 150),
(4, 4, 90),
(5, 1, 300),
(6, 2, 60),
(7, 3, 170),
(8, 4, 220),
(9, 3, 110);



-- =====================================================
-- PRENOTAZIONI
-- =====================================================

INSERT INTO prenotazione
(id_preventivo,data_intervento,indirizzo,stato)
VALUES

(1,'2026-08-02 10:00:00','Via Milano 20 Roma','CONFERMATA'),

(2,'2026-08-03 08:30:00','Via Torino 15 Torino','PROGRAMMATA'),

(3,'2026-08-04 09:30:00','Via Firenze 8 Firenze','COMPLETATA'),

(4,'2026-08-05 11:00:00','Via Venezia 5 Venezia','ANNULLATA'),

(5,'2026-08-06 07:30:00','Via Napoli 30 Napoli','COMPLETATA'),

(6,'2026-08-07 10:00:00','Via Bari 12 Bari','PROGRAMMATA'),

(7,'2026-08-08 09:00:00','Via Genova 25 Genova','CONFERMATA'),

(8,'2026-08-09 08:00:00','Via Palermo 18 Palermo','COMPLETATA'),

(9,'2026-08-10 09:00:00','Via Como 7 Como','ANNULLATA');



-- =====================================================
-- RECENSIONI
-- =====================================================

INSERT INTO recensione
(id_prenotazione,voto,commento,data_recensione)
VALUES

(3,5,'Molto professionali','2026-08-04 18:00:00'),

(5,5,'Consigliato','2026-08-06 18:00:00'),

(8,4,'Soddisfatto','2026-08-09 11:00:00');