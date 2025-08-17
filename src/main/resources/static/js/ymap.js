let mapCenter = {longitude: undefined, latitude: undefined};
getUserLocation().then(res => {
    mapCenter = res;
    initMap();
});

const BOUNDS = [
    [54.58311, 25.9985],
    [56.30248, 24.47889]
];

let i = 0;

const rndPoint = (bounds) => [
    bounds[0][0] + (bounds[1][0] - bounds[0][0]) * Math.random(),
    bounds[1][1] + (bounds[0][1] - bounds[1][1]) * Math.random()
];

const getRandomPoints = (count, bounds) =>
    Array.from({length: count}, () => ({
        type: 'Feature',
        id: i++,
        geometry: {coordinates: rndPoint(bounds)},
        properties: {name: 'beer shop'}
    }));

async function initMap() {
    // Промис `ymaps3.ready` будет зарезолвлен, когда загрузятся все компоненты основного модуля API
    await ymaps3.ready;

    const {YMap, YMapDefaultSchemeLayer, YMapLayer, YMapFeatureDataSource} = ymaps3;

    const {YMapClusterer, clusterByGrid} = await ymaps3.import('@yandex/ymaps3-clusterer@0.0.1');

    // Иницилиазируем карту
    const map = new YMap(
        // Передаём ссылку на HTMLElement контейнера
        document.getElementById('yandex-map-id'),

        // Передаём параметры инициализации карты
        {
            location: {
                // Координаты центра карты
                center: [mapCenter.longitude, mapCenter.latitude],

                // Уровень масштабирования
                zoom: 10
            }
        }
    );

    // Добавляем слой для отображения схематической карты
    map.addChild(new YMapDefaultSchemeLayer());
    map
        .addChild(new YMapDefaultSchemeLayer())
        .addChild(new YMapFeatureDataSource({id: 'clusterer-source'}))
        .addChild(new YMapLayer({source: 'clusterer-source', type: 'markers', zIndex: 1800}));

    const contentPin = document.createElement('div');
    contentPin.innerHTML = '<img src="/assets/marker.svg" class="pin">';

    // Makes usual point Marker
    const marker = (feature) =>
        new ymaps3.YMapMarker(
            {
                coordinates: feature.geometry.coordinates,
                source: 'clusterer-source'
            },
            contentPin.cloneNode(true)
        );

    // Makes Cluster Marker
    const cluster = (coordinates, features) =>
        new ymaps3.YMapMarker(
            {
                coordinates,
                source: 'clusterer-source'
            },
            circle(features.length).cloneNode(true)
        );

    function circle(count) {
        const circle = document.createElement('div');
        circle.classList.add('circle');
        circle.innerHTML = `
        <div class="circle-content">
            <span class="circle-text">${count}</span>
        </div>
    `;
        return circle;
    }

    const clusterer = new YMapClusterer({
        method: clusterByGrid({gridSize: 64}),
        features: getRandomPoints(100, BOUNDS),
        marker,
        cluster
    });

    map.addChild(clusterer);
}
