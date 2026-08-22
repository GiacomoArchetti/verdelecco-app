INSERT INTO servizio
(nome, prezzo_al_mq, minuti_al_mq, descrizione, attivo)
SELECT 'POTATURA_ALBERI_ORNAMENTALI', 4.50, 7,
       'Potatura professionale per valorizzare forma, salute e crescita degli alberi.', 1
WHERE NOT EXISTS (
    SELECT 1
    FROM servizio
    WHERE nome = 'POTATURA_ALBERI_ORNAMENTALI'
);
