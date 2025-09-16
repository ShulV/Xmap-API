
const api = axios.create({
    baseURL: '/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
});

api.interceptors.request.use(config => {
    console.log('➡️ Запрос:', config.method.toUpperCase(), config.baseURL + config.url);
    return config;
}, error => Promise.reject(error));

api.interceptors.response.use(response => {
    console.log('✅ Ответ:', response.status, response.data);
    return response;
}, error => {
    console.error('❌ Ошибка:', error.response ? error.response.status : error.message);
    return Promise.reject(error);
});

async function fetchSpotForMapDialog(spotId) {
    const url = `/spot/${spotId}/for-map`;
    const response = await api.get(url);
    return response.data;
}

async function fetchSpotsForMap() {
    const url = '/spot/list-for-map';
    const response = await api.get(url);
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
