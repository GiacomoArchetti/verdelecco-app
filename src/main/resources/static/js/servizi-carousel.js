const servicesCarousel = document.querySelector('[data-services-carousel]');

if (servicesCarousel) {
    const cards = Array.from(servicesCarousel.querySelectorAll('.service-card'));
    const previousButton = servicesCarousel.querySelector('[data-carousel-prev]');
    const nextButton = servicesCarousel.querySelector('[data-carousel-next]');
    const dotsContainer = servicesCarousel.parentElement.querySelector('[data-carousel-dots]');
    
    // 1. PREVENZIONE DOPPIONI: Svuota i pallini se già esistenti
    if (dotsContainer) {
        dotsContainer.innerHTML = '';
    }

    let activeIndex = cards.findIndex((card) => card.classList.contains('is-active'));
    if (activeIndex < 0) activeIndex = 0;

    const getIndex = (index) => (index + cards.length) % cards.length;

    const updateCarousel = () => {
        const prevIndex = getIndex(activeIndex - 1);
        const nextIndex = getIndex(activeIndex + 1);

        cards.forEach((card, index) => {
            const isActive = index === activeIndex;
            const isPrevious = index === prevIndex;
            const isNext = index === nextIndex;

            card.classList.toggle('is-active', isActive);
            card.classList.toggle('is-previous', isPrevious);
            card.classList.toggle('is-next', isNext);

            if (isPrevious) {
                card.style.display = 'block';
                card.style.gridColumn = '1';
            } else if (isActive) {
                card.style.display = 'block';
                card.style.gridColumn = '2';
            } else if (isNext) {
                card.style.display = 'block';
                card.style.gridColumn = '3';
            } else {
                card.style.display = 'none';
                card.style.gridColumn = '';
            }
        });

        if (previousButton) previousButton.disabled = false;
        if (nextButton) nextButton.disabled = false;

        dotsContainer?.querySelectorAll('.carousel-dot').forEach((dot, index) => {
            dot.classList.toggle('is-active', index === activeIndex);
            dot.setAttribute('aria-current', index === activeIndex ? 'true' : 'false');
        });
    };

    const selectService = (index) => {
        activeIndex = getIndex(index);
        updateCarousel();
    };

    // 2. Event Listeners "puliti"
    cards.forEach((card, index) => {
        const dot = document.createElement('button');
        dot.type = 'button';
        dot.className = 'carousel-dot';
        dot.setAttribute('aria-label', `Vai al servizio ${index + 1}`);
        dot.onclick = () => selectService(index);
        dotsContainer?.appendChild(dot);
        
        card.onclick = () => selectService(index);
    });

    if (previousButton) previousButton.onclick = () => selectService(activeIndex - 1);
    if (nextButton) nextButton.onclick = () => selectService(activeIndex + 1);

    updateCarousel();
}