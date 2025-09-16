// Константа для источника данных
const SPOTS_SOURCE_ID = 'spots-cluster-source';

let mapCenter = { longitude: undefined, latitude: undefined };
let mapInstance = null;

getUserLocation().then(res => {
    mapCenter = res;
    initMap();
});

async function getPointsFromDB() {
    try {
        const spots = await fetchSpotsForMap();
        return spots.map(createSpotFeature);
    } catch (err) {
        alert("Ошибка при получении спотов");
        return [];
    }
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
    mapInstance = new YMap(document.getElementById('yandex-map-id'), {
        location: {
            center: [mapCenter.longitude, mapCenter.latitude],
            zoom: 10
        }
    });

    // Базовые слои
    mapInstance.addChild(new YMapDefaultSchemeLayer());

    // Источник данных для кластеров
    mapInstance.addChild(new YMapFeatureDataSource({ id: SPOTS_SOURCE_ID }));
    mapInstance.addChild(new YMapLayer({ source: SPOTS_SOURCE_ID, type: 'markers', zIndex: 1800 }));

    // Кластеризатор
    const clusterer = new YMapClusterer({
        method: clusterByGrid({ gridSize: 64 }),
        features: await getPointsFromDB(),
        marker: createMarker,
        cluster: createCluster
    });

    mapInstance.addChild(clusterer);
}

function createMarker(feature) {
    return new ymaps3.YMapMarker(
        {
            coordinates: feature.geometry.coordinates,
            source: SPOTS_SOURCE_ID
        },
        createContentPin(feature)
    );
}

function createContentPin(feature) {
    const img = document.createElement('img');
    img.src = "/assets/marker.svg";
    img.className = "pin";
    img.alt = "📌";

    img.addEventListener('click', () => {
        showDialog(feature)
    });

    return img;
}

async function showDialog(feature) {
    let dialog = document.getElementById('map-dialog');
    if (!dialog) {
        dialog = document.createElement('div');
        dialog.id = 'map-dialog';
        dialog.className = 'map-dialog';
        document.body.appendChild(dialog);
    }
    const spot = await fetchSpotForMapDialog(feature.properties.id);

    dialog.innerHTML = `
        <h4>${spot.name}</h4>
        <img src="${spot.firstImageLink}" alt="картинка спота" class="map-dialog__image"/>
        <div class="map-dialog__btns">
            <button onclick="closeDialog()" class="btn btn-gray">Закрыть</button>
            <a href="/spot/${feature.properties.id}" class="btn btn-orange ml-20px">Перейти</a>
        </div>
    `;
}

function closeDialog() {
    const dialog = document.getElementById('map-dialog');
    if (dialog) dialog.remove();
}

function createCluster(coordinates, features) {
    const cluster = document.createElement('div');
    cluster.addEventListener('click', () => {
        showToast();
    });
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

function showToast(message) {
    const toastEl = document.getElementById('liveToast')
    const toast = bootstrap.Toast.getOrCreateInstance(toastEl);
    console.log(toastEl);
    console.log(toast);
    toast.show();
}