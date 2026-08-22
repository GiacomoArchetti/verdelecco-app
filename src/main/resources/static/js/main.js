// Main JS: menu toggling and FAQ accordion interactions
const hamburger = document.querySelector('.hamburger');
const navLinks = document.querySelector('.nav-links');

if (hamburger && navLinks) {
    hamburger.addEventListener('click', () => {
        navLinks.classList.toggle('active');
    });

    navLinks.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', () => {
            if (window.innerWidth <= 768) {
                navLinks.classList.remove('active');
            }
        });
    });
}

const faqItems = document.querySelectorAll('.faq-item');

faqItems.forEach((item) => {
    const question = item.querySelector('.faq-question');
    const answer = item.querySelector('.faq-answer');

    if (question && answer) {
        question.addEventListener('click', () => {
            item.classList.toggle('active');
        });
    }
});

document.querySelectorAll('a[href*="#"]').forEach((link) => {
    link.addEventListener('click', (event) => {
        const targetUrl = new URL(link.href, window.location.href);
        if (targetUrl.pathname !== window.location.pathname || !targetUrl.hash) {
            return;
        }

        const target = document.querySelector(targetUrl.hash);
        if (!target) {
            return;
        }

        event.preventDefault();
        const navbar = document.querySelector('.navbar');
        const offset = (navbar?.offsetHeight ?? 0) + 16;
        const startPosition = window.scrollY;
        const targetPosition = target.getBoundingClientRect().top + startPosition - offset;
        const distance = targetPosition - startPosition;
        const duration = 650;
        const startTime = performance.now();

        const animateScroll = (currentTime) => {
            const progress = Math.min((currentTime - startTime) / duration, 1);
            const easedProgress = 1 - Math.pow(1 - progress, 3);
            window.scrollTo(0, startPosition + distance * easedProgress);

            if (progress < 1) {
                requestAnimationFrame(animateScroll);
            } else {
                history.replaceState(null, '', targetUrl.hash);
            }
        };

        requestAnimationFrame(animateScroll);
    });
});

