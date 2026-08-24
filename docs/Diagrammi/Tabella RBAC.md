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


---

### Legenda Permessi
* **Accesso**: La pagina HTML/Vista è liberamente navigabile via `GET`.
* **Inviabile**: L'endpoint gestisce l'elaborazione dei dati ed è eseguibile via `POST`.
* **N/A (Redirect)**: L'utente viene reindirizzato alla pagina appropriata per la sua sessione.
* **N/A (Denegato)**: L'accesso o l'esecuzione è bloccata a livello di sicurezza (Spring Security / 403 Forbidden).


---

### Note e Dettagli Tecnologici

* **POST /login:** Gestito internamente dal filtro di sicurezza di Spring Security (`UsernamePasswordAuthenticationFilter`), non sovraccaricato nel controller.
* **Auto-Login su POST /register:** La registrazione esegue la creazione programmatica dell'autenticazione (`UsernamePasswordAuthenticationToken`) e il salvataggio nella sessione (`SPRING_SECURITY_CONTEXT`), inoltrando direttamente l'utente all'Area Cliente senza richiedere un secondo passaggio di login.
* **Gestione degli Utenti Loggati su GET `/login` e `/register`:** Se un utente autenticato prova ad accedere manualmente a questi endpoint, la `SecurityConfig` (o un controllo a livello di controller) deve reindirizzarlo alla propria area riservata per evitare stati di sessione ambigui.




