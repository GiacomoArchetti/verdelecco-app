package com.generation.giardini.entity.preventivo;

public enum StatoPreventivo {
    IN_ATTESA("IN ATTESA"),
    ACCETTATO("ACCETTATO"),
    RIFIUTATO("RIFIUTATO"),
    SCADUTO("SCADUTO"),
    ANNULLATO("ANNULLATO");

    private final String label;

    StatoPreventivo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}