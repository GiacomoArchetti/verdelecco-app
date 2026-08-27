document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('.preventivo-form') || document.querySelector('.client-quote-form');
    const phoneInput = document.getElementById('phoneNumber');

    if (form && phoneInput) {
        phoneInput.addEventListener('input', function () {
            this.value = this.value.replace(/[^0-9 ]/g, '');
            this.setCustomValidity('');
        });

        form.addEventListener('submit', function (event) {
            const prefix = document.getElementById('phonePrefix')?.value || '';
            const raw = phoneInput.value.trim();

            if (!/^[0-9 ]+$/.test(raw)) {
                event.preventDefault();
                phoneInput.setCustomValidity('Inserisci solo numeri nel numero di telefono.');
                phoneInput.reportValidity();
                return;
            }

            phoneInput.setCustomValidity('');

            if (raw && !raw.startsWith(prefix)) {
                phoneInput.value = prefix + ' ' + raw;
            }
        });
    }

    const calendarEl = document.getElementById('calendar');
    const inputData = document.getElementById('dataIntervento');
    const txtDataMostrata = document.getElementById('data-mostrata');

    if (!calendarEl) return;

    let giornoCellaPrecedente = null;

    const oggi = new Date();
    oggi.setHours(0, 0, 0, 0);

    function esDisabilitato(cellDate) {
        const d = new Date(cellDate);
        d.setHours(0, 0, 0, 0);
        const dayOfWeek = d.getDay();
        return (d < oggi || dayOfWeek === 0 || dayOfWeek === 6);
    }

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'it',
        aspectRatio: 1.35,
        contentHeight: 'auto',
        fixedWeekCount: false,
        selectable: true,
        buttonText: {
            today: 'Oggi'
        },
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },

        selectAllow: function (selectInfo) {
            return !esDisabilitato(selectInfo.start);
        },

        dayCellDidMount: function (arg) {
            // Estraiamo la data locale evitando sfalsamenti di fuso orario
            const year = arg.date.getFullYear();
            const month = arg.date.getMonth();
            const day = arg.date.getDate();
            const d = new Date(year, month, day);
            const dayOfWeek = d.getDay(); // 0 = Domenica, 6 = Sabato

            // 1. Controlla prima se è un weekend
            if (dayOfWeek === 0 || dayOfWeek === 6) {
                arg.el.classList.add('giorno-weekend', 'giorno-disabilitato');
            } 
            // 2. Se non è weekend ed è passato
            else if (d < oggi) {
                arg.el.classList.add('giorno-passato', 'giorno-disabilitato');
            }
        },

        events: function(fetchInfo, successCallback, failureCallback) {
            const start = fetchInfo.startStr.split('T')[0];
            const end = fetchInfo.endStr.split('T')[0];

            fetch(`/api/prenotazioni/occupate?start=${start}&end=${end}`)
                .then(response => {
                    const contentType = response.headers.get("content-type");
                    if (!response.ok || (contentType && !contentType.includes("application/json"))) {
                        throw new Error("Risposta non valida o endpoint protetto da autenticazione");
                    }
                    return response.json();
                })
                .then(data => successCallback(data))
                .catch(error => failureCallback(error));
        },

        eventDidMount: function(info) {
            // 1. Nascondi l'elemento HTML nativo dell'evento
            info.el.style.display = 'none';

            // 2. Recupera l'intervallo di date dell'evento
            let curr = new Date(info.event.start);
            const end = info.event.end ? new Date(info.event.end) : new Date(curr.getTime() + 86400000);

            // 3. Applica la classe a TUTTE le celle del DOM (anche duplicate nei cambi di vista)
            while (curr < end) {
                const dateStr = curr.toISOString().split('T')[0];
                const celle = calendarEl.querySelectorAll(`.fc-daygrid-day[data-date="${dateStr}"]`);
                
                celle.forEach(cella => {
                    cella.classList.add('giorno-occupato', 'giorno-disabilitato');
                });

                curr.setDate(curr.getDate() + 1);
            }
        },

        dateClick: function(info) {
            if (esDisabilitato(info.date)) {
                return;
            }

            const dataCliccataStr = info.dateStr;
            const eventiDelGiorno = calendar.getEvents().filter(e => {
                const dataEvento = e.startStr.split('T')[0];
                return dataEvento === dataCliccataStr;
            });

            if (eventiDelGiorno.length > 0) {
                alert("Spiacenti, questo giorno è già occupato!");
                return;
            }

            if (giornoCellaPrecedente) {
                giornoCellaPrecedente.classList.remove('giorno-selezionato');
            }

            info.dayEl.classList.add('giorno-selezionato');
            giornoCellaPrecedente = info.dayEl;

            if (inputData) {
                inputData.value = dataCliccataStr;
                inputData.dispatchEvent(new Event('change', { bubbles: true }));
            }

            const dataOggetto = new Date(dataCliccataStr + 'T00:00:00');
            const dataFormattata = dataOggetto.toLocaleDateString('it-IT', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });

            if (txtDataMostrata) {
                txtDataMostrata.innerText = dataFormattata.charAt(0).toUpperCase() + dataFormattata.slice(1);
            }

            // NASCONDE SUBITO L'ERRORE VISIVO
            const erroreData = document.querySelector('.calendar-section .form-error');
            if (erroreData) {
                erroreData.style.display = 'none';
            }
        }
    });

    calendar.render();
});