const servicesCarousel = document.querySelector('[data-services-carousel]');

if (servicesCarousel) {
    const cards = Array.from(servicesCarousel.querySelectorAll('.service-card'));
    const previousButton = servicesCarousel.querySelector('[data-carousel-prev]');
    const nextButton = servicesCarousel.querySelector('[data-carousel-next]');
    const dotsContainer = servicesCarousel.parentElement.querySelector('[data-carousel-dots]');
    let activeIndex = cards.findIndex((card) => card.classList.contains('is-active'));

    if (activeIndex < 0) {
        activeIndex = 0;
    }

    const updateCarousel = () => {
        const visibleIndexes = new Set();
        if (activeIndex === 0) {
            [0, 1, 2].forEach((index) => visibleIndexes.add(index));
        } else if (activeIndex === cards.length - 1) {
            [cards.length - 3, cards.length - 2, cards.length - 1]
                .forEach((index) => visibleIndexes.add(index));
        } else {
            [activeIndex - 1, activeIndex, activeIndex + 1]
                .forEach((index) => visibleIndexes.add(index));
        }

        cards.forEach((card, index) => {
            const isActive = index === activeIndex;
            const isPrevious = index === (activeIndex === 0 ? 1 : activeIndex === cards.length - 1 ? cards.length - 3 : activeIndex - 1);
            const isNext = index === (activeIndex === 0 ? 2 : activeIndex === cards.length - 1 ? cards.length - 2 : activeIndex + 1);
            card.classList.toggle('is-active', isActive);
            card.classList.toggle('is-previous', isPrevious);
            card.classList.toggle('is-next', isNext);
            card.hidden = !visibleIndexes.has(index);
        });

        if (previousButton) {
            previousButton.disabled = activeIndex === 0;
        }
        if (nextButton) {
            nextButton.disabled = activeIndex === cards.length - 1;
        }

        dotsContainer?.querySelectorAll('.carousel-dot').forEach((dot, index) => {
            dot.classList.toggle('is-active', index === activeIndex);
            dot.setAttribute('aria-current', index === activeIndex ? 'true' : 'false');
        });
    };

    const selectService = (index) => {
        activeIndex = Math.max(0, Math.min(index, cards.length - 1));
        updateCarousel();
    };

    cards.forEach((card, index) => {
        const dot = document.createElement('button');
        dot.type = 'button';
        dot.className = 'carousel-dot';
        dot.setAttribute('aria-label', `Vai al servizio ${index + 1}`);
        dot.addEventListener('click', () => selectService(index));
        dotsContainer?.appendChild(dot);
        card.addEventListener('click', () => selectService(index));
    });

    previousButton?.addEventListener('click', () => selectService(activeIndex - 1));
    nextButton?.addEventListener('click', () => selectService(activeIndex + 1));
    updateCarousel();
}