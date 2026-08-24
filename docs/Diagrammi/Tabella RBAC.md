# Matrice RBAC - Area Pubblica e Area Cliente

| Modulo / Risorsa | Endpoint / Pagina | Metodo HTTP | Visitatore (Anonimo) | Utente (Cliente) | Amministratore (Giardiniere) |
|---|---|:---:|:---:|:---:|:---:|
| **Area Pubblica** | `/` (Home Page) | `GET` | Accesso | Accesso | Accesso |
| | `/preventivo` (Form Preventivo Pubblico) | `GET` | Accesso | N/A (Redirect a /client#richiedi-preventivo) | N/A (Redirect a /admin) |
| | `/preventivo` (Invio Preventivo Pubblico) | `POST` | Inviabile | N/A (Denegato) | N/A (Denegato) |
| | `/preventivo/inviato` (Conferma Invio) | `GET` | Accesso | N/A (Redirect a /client) | N/A (Redirect a /admin) |
| **Autenticazione** | `/login` (Pagina di Login) | `GET` | Accesso | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/login` (Processo di Login) | `POST` | Eseguibile | N/A (Denegato) | N/A (Denegato) |
| | `/logout` (Disconnessione) | `POST/GET` | N/A (Redirect a `/`) | Eseguibile (Redirect a `/`) | Eseguibile (Redirect a `/`) |
| **Registrazione** | `/register` (Form Registrazione) | `GET` | Accesso | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/register` (Invio con Auto-Login) | `POST` | Eseguibile (Redirect a `/client`) | N/A (Denegato) | N/A (Denegato) |
| **Area Cliente** | `/client` (Dashboard Cliente) | `GET` | N/A (Redirect a `/login`) | Accesso | N/A (403 Forbidden) |
| | `/client/preventivo` (Richiesta Preventivo) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#richiedi-preventivo`) | N/A (403 Forbidden) |
| | `/client/profilo` (Aggiornamento Dati Contatto) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#profilo`) | N/A (403 Forbidden) |
| | `/client/prenotazioni/{id}/recensione` (Crea Recensione) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#prenotazioni`) | N/A (403 Forbidden) |
| **Area Amministratore** | `/admin` (Dashboard Admin) | `GET` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Accesso |
| | `/admin/preventivi/{id}/accept` (Accetta Preventivo) | `POST` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Eseguibile (Redirect a `/admin#preventivi`) |
| | `/admin/preventivi/{id}/reject` (Rifiuta Preventivo) | `POST` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Eseguibile (Redirect a `/admin#preventivi`) |


---

### Legenda Permessi

* **Accesso**: La pagina HTML/Vista è liberamente visibile e navigabile via `GET`.
* **Eseguibile**: Il processo o la funzione legata alla richiesta HTTP (come `POST`, `PUT`, `DELETE`) viene elaborata ed eseguita con successo dal server.
* **Inviabile**: Form o richiesta di dati che può essere inoltrata ed elaborata con successo dal server (equivalente a *Eseguibile*).
* **N/A (Redirect a [URL])**: L'operazione non si applica al ruolo corrente; il framework intercetta la richiesta e reindirizza l'utente a una rotta alternativa valida per la sua sessione.
* **N/A (Denegato)**: L'azione non è consentita al ruolo corrente ed è bloccata a livello di logica applicativa o di controller (es. richiesta `POST` da parte di un utente loggato su un form pensato per gli ospiti).
* **N/A (403 Forbidden)**: L'accesso all'endpoint viene rifiutato a livello di Spring Security prima di raggiungere il controller, poiché l'utente non possiede l'autorizzazione/ruolo richiesto (`.hasRole()`).

---

### Significato di N/A

L'acronimo **N/A** sta per **"Not Applicable"** (*Non Applicabile*). 

Indica che una specifica combinazione tra il **Ruolo dell'utente** (Visitatore, Utente Cliente, Amministratore) e l'**Endpoint/Metodo HTTP** non costituisce un flusso valido o consentito nell'applicativo. L'indicazione presente tra parentesi specifica la contromisura adottata dal sistema (*Redirect* automatico verso una rotta consentita oppure blocco della richiesta tramite *Denegato / 403 Forbidden*).


---

### Note e Dettagli Tecnologici

* **Auto-Login su POST /register:** La registrazione esegue la creazione programmatica dell'autenticazione (`UsernamePasswordAuthenticationToken`) e il salvataggio nella sessione (`SPRING_SECURITY_CONTEXT`), inoltrando direttamente l'utente all'Area Cliente senza richiedere un secondo passaggio di login.
* **Gestione degli Utenti Loggati su GET `/login` e `/register`:** Se un utente autenticato prova ad accedere manualmente a questi endpoint viene reinderizzato alla propria area riservata(portale cliente o admin) per evitare stati di sessione ambigui.




