document.addEventListener("DOMContentLoaded", () => {
    // Seleziona tutte le textarea che hanno un limite di caratteri definito (maxlength)
    const textareas = document.querySelectorAll("textarea[maxlength]");

    textareas.forEach(textarea => {
        const maxLength = textarea.getAttribute("maxlength");
        if (!maxLength) return;

        // Crea dinamicamente l'elemento per il conteggio
        const counter = document.createElement("small");
        counter.className = "char-counter";
        counter.textContent = `${textarea.value.length} / ${maxLength} caratteri`;

        // Inserisce il contatore subito dopo la textarea nel DOM
        textarea.parentNode.insertBefore(counter, textarea.nextSibling);

        // Aggiorna il valore durante la digitazione
        textarea.addEventListener("input", () => {
            const currentLength = textarea.value.length;
            counter.textContent = `${currentLength} / ${maxLength} caratteri`;

            // Cambia colore quando si raggiunge il limite
            if (currentLength >= maxLength) {
                counter.style.color = "#ff6b6b";
            } else {
                counter.style.color = "rgba(238, 244, 222, 0.7)";
            }
        });
    });
});