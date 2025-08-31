const content = document.querySelector('.content');

content.addEventListener('scroll', () => {
    // Показываем скроллбар во время скроллинга
    content.style.scrollbarColor = "rgba(0, 0, 0, 0.5) rgba(0, 0, 0, 0)";

    // Для webkit
    content.style.setProperty('--scrollbar-opacity', '1');

    // Таймаут, чтобы скрыть скроллбар через 0.5с после прекращения скроллинга
    clearTimeout(content.scrollTimeout);
    content.scrollTimeout = setTimeout(() => {
        content.style.scrollbarColor = "transparent transparent"; // скрыть для Firefox
        content.style.setProperty('--scrollbar-opacity', '0'); // скрыть для webkit
    }, 500); // через 0.5 секунды после скроллинга
});
