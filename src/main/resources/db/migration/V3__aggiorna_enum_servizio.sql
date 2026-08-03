-- 1. Espandiamo l'ENUM con i nuovi valori professionali
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

-- 2. Cambiamo il nome delle colonne per evitare errori dato che potrebbero essere già associate a delle righe(anzi lo sono dato che la versione di insert è precedente)
UPDATE servizio SET nome = 'MANUTENZIONE_TAPPETO_ERBOSO' WHERE nome = 'TAGLIO_ERBA';

-- Per le potature che erano più generiche modifichiamo usando anche il campo descrizione, e controllando gli insert dato che sono ancora pochi
-- Per le siepi
UPDATE servizio 
SET nome = 'POTATURA_SIEPI' 
WHERE nome = 'POTATURA' AND (descrizione LIKE '%siepe%' OR descrizione LIKE '%siepi%');

-- Per gli alberi
UPDATE servizio 
SET nome = 'POTATURA_ALBERI_DA_FRUTTO' 
WHERE nome = 'POTATURA' AND (descrizione LIKE '%albero%' OR descrizione LIKE '%alberi%');

-- Rete di sicurezza (FALLBACK):
-- Se è rimasta qualche vecchia 'POTATURA' generica che non conteneva le parole chiave sopra specififcate, 
-- la mappiamo su un valore predefinito (POTATURA_SIEPI)
-- per evitare che la migrazione si blocchi al punto 3.
UPDATE servizio 
SET nome = 'POTATURA_SIEPI'
WHERE nome = 'POTATURA';


-- 3. Rimuoviamo i vecchi valori obsoleti
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