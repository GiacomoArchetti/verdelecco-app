const reviewsCarousel = document.querySelector('.reviews-carousel');

if (reviewsCarousel) {
    const reviewsTrack = reviewsCarousel.querySelector('.reviews-track');
    const reviews = reviewsCarousel.querySelectorAll('.review');
    const previousButton = reviewsCarousel.querySelector('.reviews-prev');
    const nextButton = reviewsCarousel.querySelector('.reviews-next');
    const pageIndicator = reviewsCarousel.querySelector('.reviews-page');
    let currentReview = 0;

    const showReview = (index) => {
        currentReview = (index + reviews.length) % reviews.length;
        reviewsTrack.style.transform = `translateX(-${currentReview * 100}%)`;
        pageIndicator.textContent = `${currentReview + 1} / ${reviews.length}`;
    };

    previousButton.addEventListener('click', () => showReview(currentReview - 1));
    nextButton.addEventListener('click', () => showReview(currentReview + 1));
    showReview(0);
}