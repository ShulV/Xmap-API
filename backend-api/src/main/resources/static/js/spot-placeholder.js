document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.img-placeholder__original-img');

    images.forEach(img => {
        if (img.complete) {
            onImageLoad(img);
        } else {
            img.addEventListener('load', () => onImageLoad(img));
            img.addEventListener('error', () => onImageLoad(img));
        }
    });
});

function onImageLoad(img) {
    const wrapper = img.closest('.img-placeholder__wrapper');
    img.classList.add('loaded');
    setTimeout(() => wrapper.classList.add('loaded'), 300);
}
