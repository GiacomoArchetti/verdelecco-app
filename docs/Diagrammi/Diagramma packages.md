```mermaid
%%{
  init: {
    'theme': 'neutral',
    'themeVariables': {
      'textColor': '#000000',
      'nodeBorder': '#333333',
      'mainBkg': '#ffffff',
      'clusterBkg': '#fafafa',
      'edgeLabelBackground': '#ffffff',
      'lineColor': '#2b78e4'
    }
  }
}%%
graph TD
    classDef configStyle fill:#f9e6ff,stroke:#333,stroke-width:1px,color:#000;
    classDef controllerStyle fill:#d0e0ff,stroke:#333,stroke-width:1px,color:#000;
    classDef serviceStyle fill:#d5f5e3,stroke:#333,stroke-width:1px,color:#000;
    classDef repoStyle fill:#fef9e7,stroke:#333,stroke-width:1px,color:#000;
    classDef entityStyle fill:#fadbd8,stroke:#333,stroke-width:1px,color:#000;
    classDef dtoStyle fill:#ffe6cc,stroke:#333,stroke-width:1px,color:#000;
    classDef utilStyle fill:#e1d5e7,stroke:#333,stroke-width:1px,color:#000;
    classDef excStyle fill:#f8cecc,stroke:#333,stroke-width:1px,color:#000;
    classDef resourceStyle fill:#f2f3f4,stroke:#333,stroke-width:1px,stroke-dasharray: 5 5,color:#000;

    subgraph App ["📦 com.giardini.app"]
        
        subgraph ConfigPkg ["📁 config"]
            SecurityConfig["SecurityConfig.java<br/>(Spring Security, BCrypt, RBAC)"]:::configStyle
            WebConfig["WebConfig.java"]:::configStyle
        end

        subgraph ExceptionPkg ["📁 exception"]
            GlobalExceptionHandler["GlobalExceptionHandler.java<br/>(@ControllerAdvice)"]:::excStyle
            ResourceNotFoundException["ResourceNotFoundException.java"]:::excStyle
        end

        subgraph ControllerPkg ["📁 controller"]
            HomeController["HomeController.java"]:::controllerStyle
            AuthController["AuthController.java"]:::controllerStyle
            ClientController["ClientController.java"]:::controllerStyle
            AdminController["AdminController.java"]:::controllerStyle
        end

        subgraph DtoPkg ["📁 dto"]
            UserDto["UserDTO / RegisterDTO"]:::dtoStyle
            PreventivoDto["PreventivoDTO"]:::dtoStyle
            PrenotazioneDto["PrenotazioneDTO"]:::dtoStyle
        end

        subgraph ServicePkg ["📁 service"]
            UserService["UserServiceImpl.java<br/>(@Transactional)"]:::serviceStyle
            PreventivoService["PreventivoServiceImpl.java"]:::serviceStyle
            PrenotazioneService["PrenotazioneServiceImpl.java"]:::serviceStyle
        end

        subgraph UtilPkg ["📁 util / mapper"]
            UserMapper["UserMapper.java<br/>(DTO &lt;-&gt; Entity)"]:::utilStyle
            PreventivoMapper["PreventivoMapper.java"]:::utilStyle
        end

        subgraph RepoPkg ["📁 repository"]
            UserRepository["UserRepository.java<br/>(JpaRepository)"]:::repoStyle
            PreventivoRepository["PreventivoRepository.java"]:::repoStyle
            PrenotazioneRepository["PrenotazioneRepository.java"]:::repoStyle
        end

        subgraph ModelPkg ["📁 model (entity)"]
            UserEntity["User.java &amp; Role.java"]:::entityStyle
            PreventivoEntity["Preventivo.java"]:::entityStyle
            PrenotazioneEntity["Prenotazione.java"]:::entityStyle
        end

    end

    subgraph Resources ["📁 src/main/resources"]
        subgraph FlywayPkg ["📁 db/migration"]
            V1["V1__schema.sql"]:::resourceStyle
            V2["V2__data.sql"]:::resourceStyle
        end
        subgraph ViewPkg ["📁 templates (Thymeleaf)"]
            PublicViews["public/ (index, login, form)"]:::resourceStyle
            ClientViews["client/ (dashboard, preventivi)"]:::resourceStyle
            AdminViews["admin/ (dashboard, gestione)"]:::resourceStyle
        end
    end

    %% Flusso delle dipendenze
    ControllerPkg -->|Usa Bean Validation + BindingResult| DtoPkg
    ControllerPkg -->|Invia DTO e riceve DTO| ServicePkg
    ControllerPkg -->|Popola Model per| ViewPkg
    
    ServicePkg -->|Converte dati con| UtilPkg
    UtilPkg -.->|Mappa| DtoPkg
    UtilPkg -.->|Mappa| ModelPkg
    
    ServicePkg -->|Gestisce transazioni e logica su| RepoPkg
    RepoPkg -->|Persiste / Recupera| ModelPkg
    
    ExceptionPkg -.->|Intercetta eccezioni da| ControllerPkg
    SecurityConfig -.->|Applica filtri su| ControllerPkg
    FlywayPkg -.->|Crea tabelle coerenti con| ModelPkg