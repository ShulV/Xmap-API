// добавляем loader для пакета, где указываем из какого CDN загружать

let mapCenter = {longitude: undefined, latitude: undefined};
getUserLocation().then(res => {
    mapCenter = res;
    initMap();
});

async function initMap() {
    // Промис `ymaps3.ready` будет зарезолвлен, когда загрузятся все компоненты основного модуля API
    await ymaps3.ready;

    const {YMap, YMapDefaultSchemeLayer, YMapListener} = ymaps3;

    const map = new YMap(
        document.getElementById('yandex-map-id'),
        {
            location: {
                center: [mapCenter.longitude, mapCenter.latitude],
                zoom: 10
            }
        }
    );
    let singleMarker = null;

    const addMarker = (coords) => {
        // Создаем контейнер для нашего маркера
        const markerElement = document.createElement('div');
        markerElement.className = 'marker-class';
        markerElement.style.backgroundImage = 'url(/assets/marker.svg)';
        markerElement.style.backgroundSize = 'contain';
        markerElement.style.width = '40px';
        markerElement.style.height = '40px';
        markerElement.style.position = 'absolute';
        markerElement.style.transform = 'translate(-50%, -100%)';//Низ иконки метки указывает на точку
        const marker = new ymaps3.YMapMarker(
            {
                coordinates: coords,
                draggable: true,
                mapFollowsOnDrag: true
            },
            markerElement
        );
        map.addChild(marker);
        singleMarker = marker;
    };

    const updateMarkerPosition = (coords) => {
        singleMarker.update({
            coordinates: coords
        });
    };

    const clickCallback = async (object, event) => {
        setCoordinates(event.coordinates);
        if (singleMarker == null) {
            addMarker(event.coordinates);
        } else {
            updateMarkerPosition(event.coordinates);
        }
    }

    const mapListener = new YMapListener({
        onClick: clickCallback
    });
    map.addChild(mapListener);

    map
        // Добавляем слой для отображения схематической карты
        .addChild(new YMapDefaultSchemeLayer())
        // Слой с метками
        .addChild(new ymaps3.YMapDefaultFeaturesLayer({zIndex: 1800}))
}

function setCoordinates([lat, lon]) {
    document.getElementById('lat').value = lat.toFixed(6);
    document.getElementById('lon').value = lon.toFixed(6);
}
