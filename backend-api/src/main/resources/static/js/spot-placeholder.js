document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('.img-placeholder__original-img');
    images.forEach(img => addImagePlaceholder(img));

    //TODO разобраться насколько это оптимальное решение

    // Наблюдаем за добавлением новых изображений
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(mutation => {
            mutation.addedNodes.forEach(node => {
                if (node.nodeType === Node.ELEMENT_NODE) {
                    // Если это картинка с нужным классом
                    if (node.classList?.contains('img-placeholder__original-img')) {
                        addImagePlaceholder(node);
                    }
                    // Или если элемент сам — wrapper, и содержит нужную картинку
                    if (node.querySelector) {
                        const imgs = node.querySelectorAll('.img-placeholder__original-img');
                        imgs.forEach(img => addImagePlaceholder(img));
                    }
                }
            });
        });
    });

    observer.observe(document.body, {
        childList: true,
        subtree: true
    });
});

function addImagePlaceholder(img) {
    if (img.complete) {
        onImageLoad(img);
    } else {
        img.addEventListener('load', () => onImageLoad(img));
        img.addEventListener('error', () => onImageLoad(img));
    }
}

function onImageLoad(img) {
    const wrapper = img.closest('.img-placeholder__wrapper');
    img.classList.add('loaded');
    setTimeout(() => wrapper.classList.add('loaded'), 300);
}
