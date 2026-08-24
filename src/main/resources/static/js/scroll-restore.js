document.addEventListener('DOMContentLoaded', function () {
    // 1. Salva la posizione verticale dello scroll quando l'utente invia un form
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function () {
            sessionStorage.setItem('scrollPosY', window.scrollY);
        });
    });

    // 2. Ripristina la posizione esatta dello scroll dopo il caricamento completo della pagina
    const scrollPosY = sessionStorage.getItem('scrollPosY');
    if (scrollPosY !== null) {
        window.scrollTo(0, parseInt(scrollPosY, 10));
        sessionStorage.removeItem('scrollPosY');
    }
});