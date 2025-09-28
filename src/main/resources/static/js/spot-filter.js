const FILTER_KEY = "spot-filter";
const VIEW_MODE_YMAP = "YMAP";
const VIEW_MODE_CARDS = "CARDS";

const radiusMap = {
    0: {label: "Не выбрано", radius: null},
    1: {label: "500 м.", radius: 500},
    2: {label: "1 км.", radius: 1000},
    3: {label: "1.5 км.", radius: 1500},
    4: {label: "2 км.", radius: 2000},
    5: {label: "3 км.", radius: 3000},
    6: {label: "4 км.", radius: 4000},
    7: {label: "5 км.", radius: 5000},
    8: {label: "10 км.", radius: 10000},
    9: {label: "20 км.", radius: 20000},
    10: {label: "100 км.", radius: 100000},
};

const urlParams = new URLSearchParams(window.location.search);
const viewMode = urlParams.get("viewMode");

const btnFind = document.getElementById("spotFilterSearchBtnId");
const radiusInput = document.getElementById("radiusInputId");
const radiusLabel = document.getElementById("radiusLabelId");
const cityInput = document.getElementById("cityInputId");

storeSpotFilter = (filter) => {
    let storedFilter = JSON.stringify(filter);
    sessionStorage.setItem(FILTER_KEY, storedFilter);
}

restoreSpotFilter = () => {
    const strFilter = sessionStorage.getItem(FILTER_KEY);
    return strFilter ? JSON.parse(strFilter) :
        {cityId: null, radius: null, locationLat: null, locationLon: null};
}

updateSpotFilterCityId = (cityId) => {
    const filter = restoreSpotFilter();
    filter.cityId = cityId;
    storeSpotFilter(filter);
}

updateSpotFilterRadiusAndLocation = (radius, locationLat, locationLon) => {
    const filter = restoreSpotFilter();
    filter.radius = radius;
    filter.locationLat = locationLat;
    filter.locationLon = locationLon;
    storeSpotFilter(filter);
}

getSpotFilterCityId = () => {
    return restoreSpotFilter().cityId;
}

// ----------------------------------------------------------

updateRangeBackground = (el) => {
    const value = (el.value - el.min) / (el.max - el.min) * 100;
    el.style.background = `linear-gradient(
    to right,
    var(--range-left-bg-color) 0%,
    var(--range-left-bg-color) ${value}%,
    var(--range-right-bg-color) ${value}%,
    var(--range-right-bg-color) 100%
  )`;
}

updateRadiusLabel = () => {
    const value = parseInt(radiusInput.value, 10);
    radiusLabel.textContent = radiusMap[value] ? `(${radiusMap[value].label})` : "";
}

updateRadiusDataInFilter = async (radiusInput) => {
    if (radiusInput.value) {
        const location = await getUserLocation(false);
        if (location.latitude && location.longitude) {
            updateSpotFilterRadiusAndLocation(
                radiusMap[radiusInput.value].radius, location.latitude, location.longitude);
        } else {
            console.log('Фильтр не обновлен, т.к. не удалось получить местоположение');
            updateSpotFilterRadiusAndLocation(null, null, null);
        }
    } else {
        updateSpotFilterRadiusAndLocation(null, null, null);
    }
}

searchWithFilter = async () => {
    if (cityInput.value) {
        const city = await getCityByName(cityInput.value);
        if (city) {
            updateSpotFilterCityId(city.id);
        } else {
            cityInput.value = "";
            updateSpotFilterCityId(null);
        }
    } else {
        updateSpotFilterCityId(null);
    }
    await updateRadiusDataInFilter(radiusInput);

    if (viewMode === VIEW_MODE_YMAP) {
        await updatePoints();
    } else if (viewMode === VIEW_MODE_CARDS) {

    }
}

// первый раз при загрузке --------------------------------------------
updateRangeBackground(radiusInput);
updateRadiusLabel();
document.addEventListener("DOMContentLoaded", async () => {
    btnFind.addEventListener("click", async () => searchWithFilter());

    radiusInput.addEventListener("input", async () => {
        updateRangeBackground(radiusInput);
        updateRadiusLabel();
        await updateRadiusDataInFilter(radiusInput);
    });
});