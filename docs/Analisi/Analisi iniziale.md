# Documento di Analisi Iniziale — Progetto "Giardini"

## 1. Tema del Progetto e Obiettivi Generali
Il progetto **"Giardini"** consiste nello sviluppo di un'applicazione web gestionale ed espositiva dedicata ai servizi di giardinaggio e cura del verde.

L'applicazione soddisfa una duplice esigenza:
1. **Sito Vetrina & Acquisizione Clienti (Frontend Pubblico):** Presentare l'attività, mostrare i lavori svolti (confronto prima/dopo), raccogliere recensioni e guidare i potenziali clienti verso la richiesta di preventivo o prenotazione di un intervento.
2. **Portale Gestionale (Area Riservata RBAC):** Fornire una piattaforma sia per i clienti (monitoraggio richieste, preventivi e profilo) sia per l'amministratore/giardiniero (gestione operativa, dashboard dati, calendario interventi, approvazione preventivi e gestione clienti).

---

## 2. Target Utenti
L'applicativo è pensato per rispondere alle esigenze di due tipologie principali di utenti:

* **Privati (Target Primario):** Persone di età compresa tra i 25 e i 60 anni con casa di proprietà, giardino o terrazzo (famiglie, residenti in villette).
* **Amministratori di Condominio & Piccole Aziende (Target Secondario):** Soggetti che cercano un partner affidabile e strutturato per la manutenzione periodica di aree verdi comuni o aziendali.

---

## 3. Ruoli e Requisiti Funzionali (RBAC)

I ruoli gestiti dal sistema sono tre:

### 3.1. Utente Anonimo (Visitatore)
* Navigazione della pagina principale vetrina (Chi siamo, Servizi, Galleria Lavori, Workflow, FAQ, Recensioni).
* Visualizzazione dei contatti aziendali e social.
* Compilazione del Form di richiesta preventivo.
* Visualizzazione della pagina di ringraziamento con invito alla registrazione.
* Accesso alle pagine di Login e Registrazione.

### 3.2. Utente Registrato (`ROLE_USER` / Cliente)
* Tutte le funzionalità dell'utente anonimo.
* Accesso al **Portale Cliente** tramite login protetto.
* Consultazione dello storico dei propri **Preventivi** (con filtri e ricerca).
* Consultazione dello storico delle proprie **Prenotazioni / Interventi**.
* Gestione dei propri dati personali nel **Profilo** (privacy, contatti, documenti).

### 3.3. Amministratore (`ROLE_ADMIN` / Giardiniero-Gestore)
* Accesso al **Portale Admin** con dashboard riservata.
* Visualizzazione della **Dashboard** con l'insieme visivo dei KPI e dati più importanti.
* Visualizzazione e consultazione del **Calendario** degli interventi programmati.
* Gestione della anagrafica **Clienti** (lista e dettaglio funzionalità).
* Gestione delle richieste di **Preventivo** (approvazione, stima costi, avanzamento).
* Gestione delle **Prenotazioni** e programmazione degli interventi.
* Moderazione e gestione delle **Recensioni** (possibilità di risposta o approvazione).
* Gestione del **Profilo Admin**.

---

## 4. Architettura delle Pagine e Flussi di Navigazione

### 4.1. Vetrina Pubblica (Single Page Layout + Pagine dedicate)
* **Pagina Principale (Landing Page):**
  * **Navbar:** Nome/Logo (allineato a sinistra); Chi siamo, Servizi, Lavori, Recensioni, Come Lavoriamo, FAQ, Contatti, Accedi/Portale (allineati al centro); Bottone "Richiedi Preventivo" (allineato a destra).
  * **Hero / Frontman:** Presentazione del referente principale (Marco) con foto d'impatto e Call to Action (CTA) per il preventivo.
  * **Sezione Storia / Chi Siamo:** Storytelling dell'azienda, valori ed esperienza maturata negli anni.
  * **Sezione Servizi:** Schede descrittive dei lavori di giardinaggio offerti.
  * **Sezione Lavori (Portfolio):** Galleria visiva interattiva con modalità "Prima e Dopo".
  * **Sezione Recensioni:** Feedback e valutazione da parte dei clienti.
  * **Sezione Come Lavoriamo:** Workflow passo-passo della presa in carico del lavoro.
  * **Sezione FAQ:** Domande frequenti risolte a risposta rapida.
  * **Sezione Contatti & Footer:** Split section con icone Social (Instagram, Facebook), contatti diretti (Email, Telefono, P.IVA) e bottone floating per tornare all'inizio della pagina.
* **Pagina Preventivo:** Form dedicato per l'inserimento dei dettagli dell'intervento richiesto.
* **Pagina Ringraziamento:** Messaggio di conferma invio preventivo con CTA per completare la registrazione al portale.
* **Pagina Login / Registrazione:** Form di autenticazione con toggle/link "Non sei registrato?" per passare al Form di registrazione.

### 4.2. Portale Cliente (`ROLE_USER`)
* **Navbar Portale:** Logo/Messaggio di benvenuto con link alla Home Portale; Voci di navigazione: *Home*, *Preventivi*, *Prenotazioni*, *Profilo*, *Logout* (reindirizza alla landing page).
* **Home Portale:** Panoramica rapida degli ultimi aggiornamenti sulle proprie richieste.
* **Sezione Preventivi:** Tabella/Lista dei preventivi personali con barra di ricerca, filtri per stato (in attesa, approvato, rifiutato) e dettagli.
* **Sezione Prenotazioni:** Elenco degli interventi concordati e relativi dettagli.
* **Sezione Profilo:** Form di modifica dati anagrafici, gestione consensi privacy e consultazione documenti.

### 4.3. Portale Amministratore (`ROLE_ADMIN`)
* **Navbar Admin:** Logo/Benvenuto Admin; Voci di navigazione: *Dashboard*, *Calendario*, *Clienti*, *Preventivi*, *Prenotazioni*, *Recensioni*, *Profilo*, *Logout*.
* **Dashboard:** Grafici e widget di sintesi su interventi della settimana, nuovi preventivi da valutare e totale clienti.
* **Calendario:** Vista di generazione e pianificazione visiva degli appuntamenti/interventi.
* **Clienti:** Gestione completa del registro clienti.
* **Preventivi:** Gestione delle richieste in arrivo con cambio stato e invio quotazioni.
* **Prenotazioni:** Assegnazione e gestione degli interventi operativi sul campo.
* **Recensioni:** Tabella di controllo delle recensioni con possibilità di pubblicazione/risposta.
* **Profilo Admin:** Impostazioni account amministrativo.

---

## 5. Requisiti Trasversali e UX/UI
* **Responsive Design:** L'intera interfaccia web è progettata mobile-first per garantire una fruizione ottimale da smartphone e tablet.
* **Componenti d'Interazione:** Presenza del pulsante "Torna su" (Back to top) in fondo alle pagine a scorrimento lungo.
* **Elementi Multimediali:** Uso di immagini ad alta risoluzione (foto e prima/dopo) ed elementi video per presentare al meglio i lavori svolti.