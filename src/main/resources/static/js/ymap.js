// Константа для источника данных
const SPOTS_SOURCE_ID = 'spots-cluster-source';

let mapCenter = { longitude: undefined, latitude: undefined };

getUserLocation().then(res => {
    mapCenter = res;
    initMap();
});

async function getPointsFromDB() {
    try {
        const spots = await fetchSpots();
        return spots.map(createSpotFeature);
    } catch (err) {
        console.error('Ошибка при получении спотов:', err);
        alert("Ошибка при получении спотов");
        return [];
    }
}

async function fetchSpots() {
    const url = '/api/v1/spot/list-for-map';
    const response = await fetch(url);

    if (!response.ok) {
        throw new Error('Ошибка при загрузке данных');
    }
    return response.json();
}

function createSpotFeature(spot) {
    return {
        type: 'Feature',
        id: spot.id,
        geometry: {
            coordinates: [spot.latitude, spot.longitude]
        },
        properties: { id: spot.id }
    };
}

async function initMap() {
    await ymaps3.ready;
    const { YMap, YMapDefaultSchemeLayer, YMapLayer, YMapFeatureDataSource } = ymaps3;
    const { YMapClusterer, clusterByGrid } = await ymaps3.import('@yandex/ymaps3-clusterer@0.0.1');

    // Создаем карту
    const map = new YMap(document.getElementById('yandex-map-id'), {
        location: {
            center: [mapCenter.longitude, mapCenter.latitude],
            zoom: 10
        }
    });

    // Базовые слои
    map.addChild(new YMapDefaultSchemeLayer());

    // Источник данных для кластеров
    map.addChild(new YMapFeatureDataSource({ id: SPOTS_SOURCE_ID }));
    map.addChild(new YMapLayer({ source: SPOTS_SOURCE_ID, type: 'markers', zIndex: 1800 }));

    // Кластеризатор
    const clusterer = new YMapClusterer({
        method: clusterByGrid({ gridSize: 64 }),
        features: await getPointsFromDB(),
        marker: createMarker,
        cluster: createCluster
    });

    map.addChild(clusterer);
}

function createMarker(feature) {
    return new ymaps3.YMapMarker(
        {
            coordinates: feature.geometry.coordinates,
            source: SPOTS_SOURCE_ID
        },
        createContentPin()
    );
}

function createContentPin() {
    const contentPin = document.createElement('div');
    contentPin.innerHTML = '<img src="/assets/marker.svg" class="pin" alt="📌">';
    return contentPin.firstChild;
}

function createCluster(coordinates, features) {
    const cluster = document.createElement('div');
    cluster.classList.add('cluster');

    const count = features.length;

    if (count <= 5) {
        cluster.classList.add('cluster-sm');
    } else if (count <= 10) {
        cluster.classList.add('cluster-md');
    } else {
        cluster.classList.add('cluster-lg');
    }

    cluster.innerHTML = `
        <div class="cluster-content">
            <span class="cluster-text">${count}</span>
        </div>
    `;

    return new ymaps3.YMapMarker(
        {
            coordinates,
            source: SPOTS_SOURCE_ID
        },
        cluster
    );
}
