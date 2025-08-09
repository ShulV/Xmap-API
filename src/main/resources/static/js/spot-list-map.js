document.addEventListener('DOMContentLoaded', function() {
    const listMapContainer = document.querySelector('.map-list');
    const mapContainer = document.querySelector('.map-list__map');
    const listContainer = document.querySelector('.map-list__list');
    let listMapContainerHeight = listMapContainer.offsetHeight;

    listContainer.style.setProperty('--spot-list-top-offset-y', `-${listMapContainerHeight}px`);
    listContainer.style.setProperty('--spot-list-center-offset-y', `-${listMapContainerHeight / 2}px`);
    listContainer.style.setProperty('--spot-list-top-height', `${listMapContainerHeight}px`)
    listContainer.style.setProperty('--spot-list-center-height', `${listMapContainerHeight / 2}px`)

    // Диапазон сдвига от -listMapContainerHeight (список виден полностью) до 0 (списка не видно)
    listContainer.style.transform = `translateY(-${listMapContainerHeight / 2}px)`;

    let translateY = -listMapContainerHeight / 2;//позиция относительно внешнего блока
    console.log("init translateY", translateY);
    let lastY = null;//положение относительно видимой части окна

    const stickPositions = [
        'spot-list__sticky-top',
        'spot-list__sticky-center',
        'spot-list__sticky-bottom'
    ];

    const movingClassName = 'spot-list__moving';

    function stickSpotListBlock() {
        console.log(parseFloat(getComputedStyle(listContainer).transform.split(',')[5]));
        const currentTranslateY = parseFloat(getComputedStyle(listContainer).transform.split(',')[5]);
        const minTranslateY = -listMapContainer.offsetHeight;
        const thirdOfMaxTranslateY = minTranslateY / 3;
        console.log('minTranslateY', minTranslateY);
        console.log('currentTranslateY', currentTranslateY);
        console.log('thirdOfMaxTranslateY', thirdOfMaxTranslateY);

        if (thirdOfMaxTranslateY < currentTranslateY) {
            listContainer.classList.toggle(stickPositions[2]); // блок снизу (скрыт)
            listContainer.style.transform = `translateY(0)`;
        } else if (thirdOfMaxTranslateY * 2 < currentTranslateY && currentTranslateY < thirdOfMaxTranslateY) {
            listContainer.classList.toggle(stickPositions[1]); // блок от центра (видно половину)
            listContainer.style.transform = `translateY(${minTranslateY / 2}px)`;
        } else {
            listContainer.classList.toggle(stickPositions[0]); // блок сверху (полностью виден)
            listContainer.style.transform = `translateY(${minTranslateY}px)`;

        }
    }

    function startDragging(e) {
        listContainer.classList.remove(...stickPositions);
        listContainer.classList.add(movingClassName);
        e.preventDefault();
        dragging = true;
        lastY = e.clientY || e.touches[0].clientY;
    }

    function stopDragging() {
        listContainer.classList.remove(movingClassName);
        dragging = false;
        stickSpotListBlock();
    }

    function moveSpotListBlock(e) {
        // console.log("<<<<<<<<<<<move start");
        if (!dragging) return;
        const deltaY = (e.clientY || e.touches[0].clientY) - lastY;//отрицательная дельта - движение вверх
        // console.log('old lastY', lastY);
        // console.log('deltaY (отрицательная дельта - движение вверх)', deltaY);
        lastY = e.clientY || e.touches[0].clientY;
        // console.log('new lastY', lastY);
        translateY += deltaY;
        // console.log('translateY', translateY);
        const minTranslateY = -listMapContainer.offsetHeight;
        // console.log('min translateY', minTranslateY);
        translateY = Math.max(minTranslateY, translateY);//Верхняя граница блока
        // console.log('final after max translateY', translateY);
        translateY = Math.min(0, translateY);//Нижняя граница блока
        // console.log('final after min translateY', translateY);
        listContainer.style.transform = `translateY(${translateY}px)`;
        // console.log("move end>>>>>>>>>>>>>");
    }

    listContainer.addEventListener('mousedown', startDragging);
    listContainer.addEventListener('touchstart', startDragging);
    document.addEventListener('mousemove', moveSpotListBlock);
    document.addEventListener('touchmove', moveSpotListBlock);
    document.addEventListener('mouseup', stopDragging);
    document.addEventListener('touchend', stopDragging);
});
