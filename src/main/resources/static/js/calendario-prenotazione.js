document.addEventListener('DOMContentLoaded', function() {
    // Gestione del numero di telefono e prefisso
    const form = document.querySelector('.preventivo-form');
    const phoneInput = document.getElementById('phoneNumber');

    if (form && phoneInput) {

        // Permette solo numeri e spazi durante la digitazione
        phoneInput.addEventListener('input', function () {
            this.value = this.value.replace(/[^0-9 ]/g, '');
            this.setCustomValidity('');
        });

        form.addEventListener('submit', function (event) {
            const prefix = document.getElementById('phonePrefix')?.value || '';
            const raw = phoneInput.value.trim();

            // Controlla che siano presenti solo numeri e spazi
            if (!/^[0-9 ]+$/.test(raw)) {
                event.preventDefault();
                phoneInput.setCustomValidity('Inserisci solo numeri nel numero di telefono.');
                phoneInput.reportValidity();
                return;
            }

            phoneInput.setCustomValidity('');

            // Aggiunge il prefisso internazionale
            if (raw && !raw.startsWith(prefix)) {
                phoneInput.value = prefix + ' ' + raw;
            }
        });
    }

    // Inizializzazione di FullCalendar
    const calendarEl = document.getElementById('calendar');
    const inputData = document.getElementById('dataIntervento');
    const txtDataMostrata = document.getElementById('data-mostrata');
    const btnInvia = document.getElementById('btn-invia');

    if (!calendarEl) return;

    let giornoCellaPrecedente = null;

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'it',
        buttonText: {
            today: 'Oggi'
        },
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        validRange: {
            start: new Date().toISOString().split('T')[0] // Blocca i giorni passati
        },

        // MODIFICA QUI: Recupero dei giorni occupati dal Backend con gestione reindirizzamento/non-JSON
        events: function(fetchInfo, successCallback, failureCallback) {
            const start = fetchInfo.startStr.split('T')[0];
            const end = fetchInfo.endStr.split('T')[0];

            fetch(`/api/prenotazioni/occupate?start=${start}&end=${end}`)
                .then(response => {
                    // Controlla se la risposta è valida e se il contenuto è effettivamente JSON
                    // Evita il crash 'Unexpected token <' in caso di redirect alla pagina di login HTML
                    const contentType = response.headers.get("content-type");
                    if (!response.ok || (contentType && !contentType.includes("application/json"))) {
                        throw new Error("Risposta non valida o endpoint protetto da autenticazione");
                    }
                    return response.json();
                })
                .then(data => successCallback(data))
                .catch(error => failureCallback(error));
        },

        // Gestione del click sulle celle del calendario
        dateClick: function(info) {
            const dataCliccata = info.dateStr;

            // Verifica se il giorno selezionato è già occupato
            const eventiDelGiorno = calendar.getEvents().filter(e => {
                const dataEvento = e.startStr.split('T')[0];
                return dataEvento === dataCliccata;
            });

            if (eventiDelGiorno.length > 0) {
                alert("Spiacenti, questo giorno è già occupato!");
                return;
            }

            // Evidenzia la cella cliccata
            if (giornoCellaPrecedente) {
                giornoCellaPrecedente.classList.remove('giorno-selezionato');
            }

            info.dayEl.classList.add('giorno-selezionato');
            giornoCellaPrecedente = info.dayEl;

            // Inserisce il valore nell'input nascosto per Spring Boot
            if (inputData) {
                inputData.value = dataCliccata;
                inputData.dispatchEvent(new Event('change', { bubbles: true }));
            }

            // Mostra la data formattata all'utente
            const dataFormattata = new Date(dataCliccata + 'T00:00:00').toLocaleDateString('it-IT', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });

            if (txtDataMostrata) {
                // Prima lettera maiuscola per un look più pulito (es. "Lunedì 15 settembre 2026")
                txtDataMostrata.innerText = dataFormattata.charAt(0).toUpperCase() + dataFormattata.slice(1);
            }
        }
    });

    calendar.render();
});