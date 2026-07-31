const scrollButton = document.getElementById("scrollButton");

window.addEventListener("scroll", () => {

    if (window.scrollY > 300) {
        scrollButton.classList.add("show");
    } else {
        scrollButton.classList.remove("show");
    }

    // Cambia la freccia in base alla posizione
    if (window.scrollY > document.body.scrollHeight / 2) {
        scrollButton.innerHTML = "↑";
    } else {
        scrollButton.innerHTML = "↓";
    }

});

scrollButton.addEventListener("click", () => {

    if (window.scrollY > document.body.scrollHeight / 2) {

        // Torna in alto
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    } else {

        // Vai in fondo
        window.scrollTo({
            top: document.body.scrollHeight,
            behavior: "smooth"
        });

    }

});