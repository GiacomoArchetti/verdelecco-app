```mermaid
graph TD

    %% ----------------------------------------------------
    %% AREA PUBBLICA
    %% ----------------------------------------------------
    subgraph AreaPubblica["🌐 Area Pubblica (Sito Web)"]
        HomePage["🏠 Home Page<br><i>(/)</i>"]
        ServiziOfferti["🌿 Servizi Offerti<br><i>(/servizi)</i>"]
        RichiediPreventivo["📝 Richiedi Preventivo<br><i>(/preventivo/nuovo)</i>"]
        ConfermaInvio["✅ Conferma Invio<br><i>(/conferma)</i>"]
    end

    %% ----------------------------------------------------
    %% AUTENTICAZIONE
    %% ----------------------------------------------------
    subgraph Autenticazione["🔑 Autenticazione"]
        Login["🔓 Login / Accedi<br><i>(/login)</i>"]
        Registrazione["✍️ Registrazione<br><i>(/registrazione)</i>"]
    end

    %% ----------------------------------------------------
    %% PORTAL CLIENT
    %% ----------------------------------------------------
    subgraph PortalClient["👤 Portal Client (Role: CLIENT)"]
        DashboardCliente["📊 Dashboard Cliente<br><i>(/client/dashboard)</i>"]
        IMieiPreventivi["📑 I Miei Preventivi<br><i>(/client/preventivi)</i>"]
        LeMiePrenotazioni["📅 Le Mie Prenotazioni<br><i>(/client/prenotazioni)</i>"]
        RichiediPrenotazione["🗓️ Richiedi Prenotazione<br><i>(/prenotazione/nuova)</i>"]
    end

    %% ----------------------------------------------------
    %% PORTAL ADMIN
    %% ----------------------------------------------------
    subgraph PortalAdmin["🛠️ Portal Admin (Role: ADMIN)"]
        DashboardAdmin["🛠️ Dashboard Admin<br><i>(/admin/dashboard)</i>"]
        GestionePreventivi["📝 Gestione Preventivi<br><i>(/admin/preventivi)</i>"]
        GestioneAppuntamenti["📆 Gestione Appuntamenti<br><i>(/admin/prenotazioni)</i>"]
        GestioneUtenti["👥 Gestione Utenti<br><i>(/admin/utenti)</i>"]
    end

    %% ----------------------------------------------------
    %% COLLEGAMENTI TRA NODI E SUBGRAPH
    %% ----------------------------------------------------

    %% Flusso Area Pubblica
    HomePage -->|Accedi| Login
    HomePage -->|Esplora| ServiziOfferti
    HomePage -->|Richiede| RichiediPreventivo
    ServiziOfferti -->|Richiede| RichiediPreventivo
    RichiediPreventivo -->|Invia Form| ConfermaInvio

    %% Reindirizzamento da Conferma Invio ad Accedi
    ConfermaInvio -->|Invia Form / Prosegui| Login

    %% Flusso Autenticazione
    Login -->|Non hai un account?| Registrazione
    Registrazione -->|Account Creato| DashboardCliente
    Login -->|Ruolo: CLIENT| DashboardCliente
    Login -->|Ruolo: ADMIN| DashboardAdmin

    %% Flusso Portal Client
    DashboardCliente -->|Vedi stato| IMieiPreventivi
    DashboardCliente -->|Vedi calendario| LeMiePrenotazioni
    DashboardCliente -->|Nuova richiesta| RichiediPreventivo
    
    %% Flusso Preventivo -> Risposta -> Prenotazione
    IMieiPreventivi -->|Risposta preventivo / Accettato| RichiediPrenotazione

    %% Flusso Portal Admin
    DashboardAdmin -->|Valuta e Invia| GestionePreventivi
    DashboardAdmin -->|Approva / Rifiuta| GestioneAppuntamenti
    DashboardAdmin -->|Gestisci Ruoli| GestioneUtenti

    %% ----------------------------------------------------
    %% STILIZZAZIONE GRAFICA (SFONDI PASTELLO E TESTO NERO)
    %% ----------------------------------------------------
    style AreaPubblica fill:#f8f9fa,stroke:#0d6efd,stroke-width:2px,color:#000000
    style Autenticazione fill:#fff8e1,stroke:#ffa000,stroke-width:2px,color:#000000
    style PortalClient fill:#f1f8e9,stroke:#558b2f,stroke-width:2px,color:#000000
    style PortalAdmin fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#000000

    style HomePage fill:#e3f2fd,stroke:#0d6efd,color:#000000
    style ServiziOfferti fill:#e3f2fd,stroke:#0d6efd,color:#000000
    style RichiediPreventivo fill:#e3f2fd,stroke:#0d6efd,color:#000000
    style ConfermaInvio fill:#d1e7dd,stroke:#0f5132,color:#000000

    style Login fill:#fff3cd,stroke:#ffc107,color:#000000
    style Registrazione fill:#fff3cd,stroke:#ffc107,color:#000000

    style DashboardCliente fill:#d1e7dd,stroke:#198754,color:#000000
    style IMieiPreventivi fill:#d1e7dd,stroke:#198754,color:#000000
    style LeMiePrenotazioni fill:#d1e7dd,stroke:#198754,color:#000000
    style RichiediPrenotazione fill:#d1e7dd,stroke:#198754,color:#000000

    style DashboardAdmin fill:#e1bee7,stroke:#8e24aa,color:#000000
    style GestionePreventivi fill:#e1bee7,stroke:#8e24aa,color:#000000
    style GestioneAppuntamenti fill:#e1bee7,stroke:#8e24aa,color:#000000
    style GestioneUtenti fill:#e1bee7,stroke:#8e24aa,color:#000000