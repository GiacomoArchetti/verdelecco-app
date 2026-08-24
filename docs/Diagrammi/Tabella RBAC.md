| Modulo / Risorsa | Endpoint / Pagina | Visitatore (Anonimo) | Utente (Cliente) | Amministratore (Giardiniere) |
|---|---|:---:|:---:|:---:|
| **Area Pubblica** | `GET /` (Home Page) | Lettura | Lettura | Lettura |
| | `GET /servizi` (Servizi Offerti) | Lettura | Lettura | Lettura |
| | `GET /preventivo/nuovo` (Richiesta Preventivo) | Creazione *(Reindirizza a Login)* | Creazione | Creazione |
| | `POST /conferma` (Invio Form Preventivo) | Esecuzione | Esecuzione | Esecuzione |
| **Autenticazione** | `GET/POST /login` | Esecuzione | Negato | Negato |
| | `GET/POST /registrazione` | Esecuzione | Negato | Negato |
| **Portale Cliente** | `GET /client/dashboard` | Negato | Lettura | Negato |
| | `GET /client/preventivi` | Negato | Lettura *(I propri)* | Lettura *(Tutti)* |
| | `GET /client/prenotazioni` | Negato | Lettura *(Le proprie)* | Lettura *(Tutte)* |
| | `GET/POST /prenotazione/nuova` | Negato | Creazione | Creazione |
| **Portale Admin** | `GET /admin/dashboard` | Negato | Negato | Accesso Completo |
| | `ALL /admin/preventivi/**` | Negato | Negato | Accesso Completo |
| | `ALL /admin/prenotazioni/**` | Negato | Negato | Accesso Completo |
| | `ALL /admin/utenti/**` | Negato | Negato | Accesso Completo |