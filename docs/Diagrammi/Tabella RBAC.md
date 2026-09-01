# Matrice RBAC - Area Pubblica, Cliente e Amministratore

| Modulo / Risorsa | Endpoint / Pagina | Metodo HTTP | Visitatore (Anonimo) | Utente (Cliente) | Amministratore (Giardiniere) |
|---|---|:---:|:---:|:---:|:---:|
| **Area Pubblica** | `/` (Home Page) | `GET` | Accesso | Accesso | Accesso |
| | `/preventivo` (Form Preventivo Pubblico) | `GET` | Accesso | N/A (Redirect a `/client#richiedi-preventivo`) | N/A (Redirect a `/admin`) |
| | `/preventivo` (Invio Preventivo Pubblico) | `POST` | Eseguibile | Eseguibile | Eseguibile |
| | `/preventivo/inviato` (Conferma Invio) | `GET` | Accesso | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/api/prenotazioni/occupate` (Disponibilità calendario) | `GET` | Accesso | Accesso | Accesso |
| **Autenticazione** | `/login` (Pagina di Login) | `GET` | Accesso | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/login` (Processo di Login) | `POST` | Eseguibile | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/logout` (Disconnessione) | `GET` | N/A (Redirect a `/`) | Eseguibile (Redirect a `/`) | Eseguibile (Redirect a `/`) |
| **Registrazione** | `/register` (Form Registrazione) | `GET` | Accesso | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| | `/register` (Invio con Auto-Login) | `POST` | Eseguibile (Redirect a `/client`) | N/A (Redirect a `/client`) | N/A (Redirect a `/admin`) |
| **Area Cliente** | `/client` (Dashboard Cliente) | `GET` | N/A (Redirect a `/login`) | Accesso | N/A (403 Forbidden) |
| | `/client/preventivo` (Richiesta Preventivo) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#richiedi-preventivo`) | N/A (403 Forbidden) |
| | `/client/preventivi/{id}/cancel` (Annulla Preventivo) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#preventivi`) | N/A (403 Forbidden) |
| | `/client/prenotazioni/{id}/recensione` (Crea Recensione) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#prenotazioni`) | N/A (403 Forbidden) |
| | `/client/prenotazioni/{id}/cancel` (Annulla Prenotazione) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#prenotazioni`) | N/A (403 Forbidden) |
| | `/client/profilo` (Aggiornamento Dati Contatto) | `POST` | N/A (Redirect a `/login`) | Eseguibile (Redirect a `/client#profilo`) | N/A (403 Forbidden) |
| **Area Amministratore** | `/admin` (Dashboard Admin) | `GET` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Accesso |
| | `/admin/preventivi/{id}/accept` (Accetta Preventivo) | `POST` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Eseguibile (Redirect a `/admin#preventivi`) |
| | `/admin/preventivi/{id}/reject` (Rifiuta Preventivo) | `POST` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Eseguibile (Redirect a `/admin#preventivi`) |
| | `/admin/prenotazioni/{id}/complete` (Completa Prenotazione) | `POST` | N/A (Redirect a `/login`) | N/A (403 Forbidden) | Eseguibile (Redirect a `/admin#prenotazioni`) |

---

### Legenda Permessi

* **Accesso**: La pagina HTML/Vista è liberamente visibile e navigabile via `GET`.
* **Eseguibile**: Il processo o la funzione legata alla richiesta HTTP viene elaborata ed eseguita con successo dal server.
* **N/A (Redirect a [URL])**: L'operazione non si applica al ruolo corrente; il framework intercetta la richiesta e reindirizza l'utente a una rotta alternativa valida per la sua sessione.
* **N/A (403 Forbidden)**: L'accesso all'endpoint viene rifiutato a livello di Spring Security prima di raggiungere il controller, poiché l'utente non possiede l'autorizzazione/ruolo richiesto (`.hasRole()`).

---

### Significato di N/A

L'acronimo **N/A** sta per **"Not Applicable"** (*Non Applicabile*).

Indica che una specifica combinazione tra il **Ruolo dell'utente** (Visitatore, Utente Cliente, Amministratore) e l'**Endpoint/Metodo HTTP** non costituisce un flusso valido o consentito nell'applicativo. L'indicazione presente tra parentesi specifica la contromisura adottata dal sistema (*Redirect* automatico verso una rotta consentita oppure blocco della richiesta tramite *403 Forbidden*).

---

### Note e Dettagli Tecnologici

* **Security config**: la configurazione di Spring Security definisce le aree riservate tramite `requestMatchers` con ruoli `ROLE_UTENTE` e `ROLE_ADMIN`, mentre l'area pubblica e le rotte di autenticazione sono consentite a tutti i visitatori.
* **Redirect RBAC**: i controller `HomeController`, `PreventivoController`, `LoginController` e `RegisterController` reindirizzano automaticamente gli utenti già autenticati verso il proprio portale dedicato per evitare stati di sessione ambigui.
* **Auto-Login su POST /register**: la registrazione crea automaticamente l'autenticazione nella sessione e reindirizza l'utente verso `/client`.
* **Logout**: `/logout` è gestito da Spring Security e, dopo la sessione, reindirizza l'utente alla home page.
* **API calendario**: `/api/prenotazioni/occupate` è esposta in lettura per supportare la logica del calendario e la disponibilità delle date di intervento.

