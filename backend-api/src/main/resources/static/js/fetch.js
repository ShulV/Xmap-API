
const api = axios.create({
    baseURL: '/api',
    // timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
});

api.interceptors.request.use(config => {
    console.log('➡️ Запрос:', config.method.toUpperCase(), config.baseURL + config.url);
    if (config.params) {
        console.log('   📌 params:', config.params);
    }
    if (config.data) {
        console.log('   📦 data:', config.data);
    }
    // if (config.headers) {
    //     console.log('   🏷 headers:', config.headers);
    // }
    return config;
}, error => Promise.reject(error));

api.interceptors.response.use(response => {
    console.log('✅ Ответ:', response.status, response.data);
    return response;
}, error => {
    console.error('❌ Ошибка:', error.response ? error.response.status : error.message);
    return Promise.reject(error);
});

// ----------------------------------------------------------------------------------------

async function getSpotForMapDialog(spotId, locationLon = null, locationLat = null) {
    const url = `/spot/${spotId}/for-map`;
    const response = await api.get(url, { params: { locationLon: locationLon, locationLat: locationLat } });
    return response.data;
}

async function getSpotsForMap() {
    const url = '/spot/list/for-map';
    const response = await api.post(url, restoreSpotFilter(), null);
    return response.data;
}

async function getSpotsForCards(pageNumber = 0, pageSize = 10) {
    const url = '/spot/list/for-cards';
    const response = await api.post(
        url,
        restoreSpotFilter(),
        { params: { pageNumber: pageNumber, pageSize: pageSize } }
    );
    return response.data;
}

async function getCitiesBySubstring(substring) {
    const url = '/city/list';
    const response = await api.get(url, { params: { substring } });
    return response.data;
}

async function getCityById(id) {
    const url = `/city/${id}`;
    const response = await api.get(url);
    return response.data;
}

async function getCityByName(name) {
    const url = `/city`;
    const response = await api.get(url, { params: { name } });
    return response.data;
}

async function changeSpotAddingRequestStatus(status, spotAddingRequestId) {
    const url = `/spot-adding-request/change-status/${status}`;
    const response = await api.patch(
        url,
        null,
        { params: { spotAddingRequestId } }
    );
    return response.data;
}
