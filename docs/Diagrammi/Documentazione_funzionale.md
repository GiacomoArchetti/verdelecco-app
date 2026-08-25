# Modello ER - Gestionale Giardiniere

## Descrizione progetto

Il sistema permette la gestione di servizi di manutenzione del verde, richieste di preventivo, prenotazioni degli interventi e recensioni da parte degli utenti.

Il modello prevede:
- utenti con diversi ruoli;
- servizi disponibili;
- preventivi composti da uno o più servizi;
- prenotazioni degli interventi;
- recensioni sui servizi effettuati.

---

# Entità

## UTENTE

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idUtente | Long | PK | Identificativo univoco utente |
| nome | varchar(50) | NOT NULL | Nome utente |
| cognome | varchar(50) | NOT NULL | Cognome utente |
| email | varchar(100) | NOT NULL, UNIQUE | Email utente |
| password | varchar(255) | NOT NULL | Password criptata |
| telefono | varchar(20) | | Numero di telefono |
| attivo | tinyint(1)/boolean | | Utente attivo o non |
| ruolo | ENUM | ADMIN, UTENTE | Ruolo dell'utente |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| Utente - Preventivo | 1:N | Un utente può creare più preventivi |
| Utente - Prenotazione | 1:N | Un utente può effettuare più prenotazioni |
| Utente - Recensione | 1:N | Un utente può lasciare più recensioni |

---

# SERVIZIO

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idServizio | Long | PK | Identificativo servizio |
| nome | ENUM | NOT NULL | Tipologia servizio |
| prezzoAlMq | decimal(10,2) | NOT NULL | Prezzo per metro quadro |
| minutiAlMq | int | NOT NULL | Tempo stimato per metro quadro |
| descrizione | text | | Descrizione dettagliata del servizio |
| attivo | tinyint(1)/boolean | | Servizio attivo o non |

### Esempi servizi

| ID | Servizio |
|---|---|
| 1 | Taglio erba |
| 2 | Potatura |
| 3 | Semina |
| 4 | Pulizia giardino |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| Servizio - Prenotazione | 1:N | Un servizio può avere più prenotazioni |
| Servizio - Recensione | 1:N | Un servizio può avere più recensioni |
| Servizio - DettaglioPreventivo | 1:N | Un servizio può appartenere a più preventivi |

---

# PREVENTIVO

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idPreventivo | Long | PK | Identificativo preventivo |
| idUtente | Long | FK | Utente che richiede il preventivo |
| indirizzo | varchar(200) | NOT NULL | Indirizzo intervento |
| superficieMq | decimal(7,2) | NOT NULL | Dimensione area da lavorare |
| costoStimato | decimal(10,2) | NOT NULL | Costo totale stimato |
| descrizione | text | | Descrizione richiesta cliente |
| dataIntervento | datetime | | Data desiderata intervento |
| dataEmissionePreventivo | date | NOT NULL | Data creazione preventivo |
| dataScadenza | date | | Scadenza validità preventivo |
| stato | ENUM | | Stato preventivo |

### Stati preventivo

| Valore |
|---|
| IN_ATTESA |
| ACCETTATO |
| RIFIUTATO |
| SCADUTO |
| ANNULLATO |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| Preventivo - DettaglioPreventivo | 1:N | Un preventivo può contenere più servizi |
| Preventivo - Prenotazione | 1:N | Un preventivo può generare una prenotazione |

---

# DETTAGLIO_PREVENTIVO

Tabella associativa tra PREVENTIVO e SERVIZIO.

Permette di associare più servizi allo stesso preventivo.

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idDettaglio | Long | PK | Identificativo dettaglio |
| idPreventivo | Long | FK | Preventivo associato |
| idServizio | Long | FK | Servizio associato |
| quantita | int | | Quantità del servizio |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| DettaglioPreventivo - Preventivo | N:1 | Ogni dettaglio appartiene a un preventivo |
| DettaglioPreventivo - Servizio | N:1 | Ogni dettaglio rappresenta un servizio |

---

# PRENOTAZIONE

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idPrenotazione | Long | PK | Identificativo prenotazione |
| idPreventivo | Long | FK | Preventivo collegato not null e unique |
| dataIntervento | datetime | NOT NULL | Data intervento |
| indirizzo | varchar(200) | NOT NULL | Luogo intervento |
| stato | ENUM | | Stato prenotazione |

### Stati prenotazione

| Valore |
|---|
| PROGRAMMATA |
| COMPLETATA |
| ANNULLATA |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| Prenotazione - Utente | N:1 | Una prenotazione appartiene a un utente |
| Prenotazione - Servizio | N:1 | Una prenotazione riguarda un servizio |
| Prenotazione - Preventivo | N:1 | Una prenotazione può derivare da un preventivo |

---

# RECENSIONE

| Campo | Tipo | Vincoli | Descrizione |
|---|---|---|---|
| idRecensione | Long | PK | Identificativo recensione |
| idPrenotazione | Long | FK | Prenotazione recensita |
| voto | tinyint | 1-5 | Valutazione servizio |
| commento | text | | Commento recensione |
| dataRecensione | datetime | | Data recensione |

### Vincoli

| Campo | Regola |
|---|---|
| voto | BETWEEN 1 AND 5 |

### Relazioni

| Relazione | Cardinalità | Descrizione |
|---|---|---|
| Recensione - Prenotazione | N:1 | Una recensione appartiene a una prenotazione completata |
| Prenotazione - Recensione | 1:0..1 | Una prenotazione può avere al massimo una recensione |

---

# Schema Relazionale Completo

## Relazioni principali

```text

                         +-----------+
                         |  UTENTE   |
                         +-----------+
                              |
                              | 1:N
                              |
                              v
                      +---------------+
                      |  PREVENTIVO   |
                      +---------------+
                              |
                              | 1:N
                              |
                              v
              +-----------------------------+
              | DETTAGLIO_PREVENTIVO        |
              +-----------------------------+
                              |
                              | N:1
                              |
                              v
                       +-------------+
                       |  SERVIZIO   |
                       +-------------+


                      +---------------+
                      |  PREVENTIVO   |
                      +---------------+
                              |
                              | 1:0..1
                              |
                              v
                      +---------------+
                      | PRENOTAZIONE  |
                      +---------------+
                              |
                              | 1:0..1
                              |
                              v
                      +---------------+
                      |  RECENSIONE   |
                      +---------------+


                      +---------------+
                      | PRENOTAZIONE  |
                      +---------------+
                              ∧
                              | N:1
                              |
                              |
                         +-----------+
                         |  UTENTE   |
                         +-----------+

```

---

# Relazioni Finali

| Entità A | Cardinalità | Entità B | Descrizione |
|---|---|---|---|
| UTENTE | 1:N | PREVENTIVO | Un utente può creare più preventivi |
| PREVENTIVO | 1:N | DETTAGLIO_PREVENTIVO | Un preventivo può contenere più servizi |
| DETTAGLIO_PREVENTIVO | N:1 | SERVIZIO | Ogni dettaglio rappresenta un servizio |
| PREVENTIVO | 1:0..1 | PRENOTAZIONE | Un preventivo accettato genera una prenotazione |
| PRENOTAZIONE | 1:0..1 | RECENSIONE | Un intervento può ricevere una recensione |
| PRENOTAZIONE | 1:N | UTENTE | Un utente può avere più prenotazioni |