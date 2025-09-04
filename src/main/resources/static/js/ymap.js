let mapCenter = {longitude: undefined, latitude: undefined};
getUserLocation().then(res => {
    mapCenter = res;
    initMap();
});

const getPointsFromDB = async () => {
    const spots = await fetchSpots();
    return spots.map(spot => ({
        type: 'Feature',
        id: spot.id,
        geometry: {coordinates: [spot.latitude, spot.longitude]},
        properties: {id: spot.id}
    }));
};

async function fetchSpots() {
    const getSpotListUrl = '/api/v1/spot/list-for-map'
    try {
        const response = await fetch(getSpotListUrl);
        return await response.json();
    } catch (err) {
        console.error('Ошибка при получении спотов:', err);
    }
}

async function initMap() {
    // Промис `ymaps3.ready` будет зарезолвлен, когда загрузятся все компоненты основного модуля API
    await ymaps3.ready;
    const {YMap, YMapDefaultSchemeLayer, YMapLayer, YMapFeatureDataSource} = ymaps3;
    const {YMapClusterer, clusterByGrid} = await ymaps3.import('@yandex/ymaps3-clusterer@0.0.1');

    const map = new YMap(
        document.getElementById('yandex-map-id'),
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
    map.addChild(new YMapDefaultSchemeLayer())
    //
        .addChild(new YMapFeatureDataSource({id: 'clusterer-source'}))
        .addChild(new YMapLayer({source: 'clusterer-source', type: 'markers', zIndex: 1800}));

    const contentPin = document.createElement('div');
    contentPin.innerHTML = '<img src="/assets/marker.svg" class="pin" alt="📌">';

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
        circle.classList.add('cluster');
        if (count <= 5) {
            circle.classList.add('cluster-sm');
        } else if (count <= 10) {
            circle.classList.add('cluster-md');
        } else {
            circle.classList.add('cluster-lg');
        }
        circle.innerHTML = `
        <div class="cluster-content">
            <span class="cluster-text">${count}</span>
        </div>
    `;
        return circle;
    }

    const clusterer = new YMapClusterer({
        method: clusterByGrid({gridSize: 64}),
        features: await getPointsFromDB(),
        marker,
        cluster
    });

    map.addChild(clusterer);
}
