-- =====================================================
-- TEST
-- =====================================================
USE gestionale_giardiniere;

SELECT *
FROM utente
WHERE ruolo='ADMIN';

-- =====================================================
-- Preventivi collegati agli utenti
-- =====================================================
SELECT 
p.id_preventivo,
u.nome,
u.cognome,
p.indirizzo
FROM preventivo p
JOIN utente u 
ON p.id_utente=u.id_utente;

-- =====================================================
-- Prenotazioni collegate ai preventivi
-- =====================================================
SELECT
pr.id_prenotazione,
p.id_preventivo,
pr.indirizzo,
pr.stato
FROM prenotazione pr
JOIN preventivo p
ON pr.id_preventivo=p.id_preventivo;

-- =====================================================
-- Prenotazione
-- =====================================================
SELECT *
from prenotazione;

-- =====================================================
-- Recensioni collegate alle prenotazioni
-- =====================================================
SELECT
r.id_recensione,
p.id_prenotazione,
p.stato,
r.voto,
r.commento
FROM recensione r
JOIN prenotazione p
ON r.id_prenotazione=p.id_prenotazione;



-- TEST VINCOLI DATABASE

-- =====================================================
-- Prova a inserire un voto non valido:
-- =====================================================
INSERT INTO recensione
(id_prenotazione,voto,commento,data_recensione)
VALUES
(1,10,'Test','2026-08-10 10:00:00');


-- =====================================================
-- Eliminazione utente con preventivo collegato
-- =====================================================
DELETE FROM utente
WHERE id_utente=2;

-- =====================================================
-- Test unicità prenotazione/preventivo
-- =====================================================
INSERT INTO prenotazione
(id_preventivo,data_intervento,indirizzo,stato)
VALUES
(3,'2026-08-15 10:00:00','Via Test 1 Roma','PROGRAMMATA');
