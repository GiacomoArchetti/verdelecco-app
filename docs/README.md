# Giardini

Applicazione web Spring Boot per la gestione di richieste di preventivo, prenotazioni, recensioni e dashboard amministrativa per un'azienda di giardinaggio.

## Overview

Il progetto presenta tre aree principali:

- Area pubblica: home page, modulo preventivo, pagina di conferma, calendario disponibilità
- Area cliente: gestione profilo, richiesta e annullamento preventivi, prenotazioni, recensioni
- Area amministrativa: gestione clienti, preventivi, prenotazioni e completamento interventi

## Stack Tecnologico

- Java 25
- Spring Boot 4.1.0
- Spring MVC + Thymeleaf
- Spring Security
- Spring Data JPA
- MySQL
- Flyway
- Maven

## Struttura del Progetto

```text
src/
  main/
    java/
      com/generation/giardini/
        config/
        controller/
          admin/
          auth/
          client/
          publics/
        dto/
        model/
        repository/
        security/
        service/
    resources/
      application.properties
      db/
        migration/
      static/
      templates/
  test/
    java/

docs/
  Analisi/
  Diagrammi/
```

## Requisiti

- JDK 25
- Maven
- MySQL in esecuzione
- File di ambiente locale `.env` creato a partire da `.env.example`
- Configurazione del database via variabili d'ambiente o fallback in `application.properties`

## Configurazione ambiente

Il progetto usa variabili d'ambiente per i parametri di accesso al database e per altri valori di runtime. Non è previsto il tracciamento di un file `.env` reale nel repository: il file `.env` va creato localmente e non va versionato.

Per iniziare, copia il template presente in `.env.example`:

```bash
cp .env.example .env
```

Quindi personalizza i valori richiesti, in particolare:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/gestionale_giardiniere?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=tuo_utente_mysql
SPRING_DATASOURCE_PASSWORD=tua_password_mysql
```

Se il file `.env` non esiste, l'applicazione usa i valori di default definiti in `src/main/resources/application.properties`.

## Esecuzione

```bash
./mvnw clean install
./mvnw spring-boot:run
```

oppure su Windows:

```powershell
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

## Endpoint Principali

### Pubblici

- `GET /` — home page
- `GET /preventivo` — form preventivo pubblico
- `POST /preventivo` — invio preventivo
- `GET /preventivo/inviato` — conferma invio
- `GET /api/prenotazioni/occupate` — giorni occupati per il calendario
- `GET /login` — pagina login
- `GET /register` — pagina registrazione
- `POST /register` — registrazione con auto-login

### Cliente autenticato

- `GET /client` — dashboard cliente
- `POST /client/preventivo` — invio preventivo da cliente
- `POST /client/preventivi/{id}/cancel` — annullamento preventivo
- `POST /client/prenotazioni/{id}/cancel` — annullamento prenotazione
- `POST /client/prenotazioni/{id}/recensione` — creazione recensione
- `POST /client/profilo` — aggiornamento profilo

### Amministratore autenticato

- `GET /admin` — dashboard admin
- `POST /admin/preventivi/{id}/accept` — accettazione preventivo
- `POST /admin/preventivi/{id}/reject` — rifiuto preventivo
- `POST /admin/prenotazioni/{id}/complete` — completamento prenotazione

## Ruoli e Permessi

- `ROLE_UTENTE` — accesso area cliente
- `ROLE_ADMIN` — accesso area amministrativa
- Pubblico senza autenticazione — accesso limitato a pagine e endpoint pubblici

## Sicurezza

L’applicazione usa Spring Security con regole di autorizzazione basate sui ruoli:

- `/` e `/preventivo` e `/register` e `/login` sono pubblici
- `/client/**` richiede ruolo `UTENTE`
- `/admin/**` richiede ruolo `ADMIN`
- il resto richiede autenticazione

## Database

Le migrazioni Flyway si trovano in:

- `src/main/resources/db/migration/`

## Documentazione

La documentazione di analisi e diagrammi è presente nella cartella `docs`.

## Note

- L’utente cliente viene reindirizzato automaticamente alla propria area dopo il login
- L’amministratore viene reindirizzato alla dashboard admin dopo il login
- La registrazione crea direttamente la sessione autenticata
