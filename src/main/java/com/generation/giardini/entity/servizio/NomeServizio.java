package com.generation.giardini.entity.servizio;

/**
 * public enum NomeServizio {
 * MANUTENZIONE_TAPPETO_ERBOSO,
 * POTATURA_ALBERI_DA_FRUTTO,
 * POTATURA_ALBERI_ORNAMENTALI,
 * POTATURA_SIEPI,
 * SEMINA,
 * PULIZIA_GIARDINO
 * }
 */
public enum NomeServizio {

    MANUTENZIONE_TAPPETO_ERBOSO(
            "Manutenzione tappeto erboso",
            "/images/manutenzione-tappeto-erboso.jpg",
            "Cura, taglio e mantenimento del prato sempre sano e ordinato."),

    POTATURA_ALBERI_DA_FRUTTO(
            "Potatura alberi da frutto",
            "/images/potatura-alberi-.webp",
            "Potatura mirata per mantenere gli alberi sani e favorire una buona fruttificazione."),

    POTATURA_ALBERI_ORNAMENTALI(
            "Potatura alberi ornamentali",
            "/images/potatura-ornamentali.jpg",
            "Potatura professionale per valorizzare forma, salute e crescita degli alberi."),

    POTATURA_SIEPI(
            "Potatura siepi",
            "/images/potatura_siepei.jpg",
            "Taglio e modellatura delle siepi per mantenerle curate, ordinate e rigogliose."),

    SEMINA(
            "Semina",
            "/images/semina-prato.jpg",
            "Semina e rigenerazione del prato per ottenere un manto erboso uniforme e resistente."),

    PULIZIA_GIARDINO(
            "Pulizia giardino",
            "/images/pulizia-giardino.jpg",
            "Pulizia e riordino degli spazi verdi per un giardino sempre curato e piacevole.");

    private final String label;
    private final String image;
    private final String descrizione;

    // Costruttore: inizializza il servizio con nome visualizzato, immagine e
    // descrizione
    NomeServizio(String label, String image, String descrizione) {
        this.label = label;
        this.image = image;
        this.descrizione = descrizione;
    }

    // Restituisce il nome del servizio da visualizzare
    public String getLabel() {
        return label;
    }

    // Restituisce il percorso dell'immagine associata al servizio
    public String getImage() {
        return image;
    }

    // Restituisce la descrizione del servizio
    public String getDescrizione() {
        return descrizione;
    }
}
