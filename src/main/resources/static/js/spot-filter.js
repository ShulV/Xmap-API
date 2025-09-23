const FILTER_KEY = "spot-filter";
const VIEW_MODE_YMAP = "YMAP";
const VIEW_MODE_CARDS = "CARDS";


storeSpotFilter = function (filter) {
    let storedFilter = JSON.stringify(filter);
    sessionStorage.setItem(FILTER_KEY, storedFilter);
    console.log("storedFilter: ");
    console.log(storedFilter);
}

restoreSpotFilter = function () {
    const strFilter = sessionStorage.getItem(FILTER_KEY);
    console.log(`storedFilter str: ${strFilter}`);
    let restoredFilter = strFilter ? JSON.parse(strFilter) : {"cityId": null};
    console.log("restoredFilter: ");
    console.log(restoredFilter);

    return restoredFilter;
}

updateSpotFilterCityId = function (cityId) {
    const filter = restoreSpotFilter();
    filter.cityId = cityId;
    storeSpotFilter(filter);
}

getSpotFilterCityId = function () {
    return restoreSpotFilter().cityId;
}


document.addEventListener("DOMContentLoaded", () => {
    const btnFind = document.querySelector("#spotFilterSearchBtnId");
    const urlParams = new URLSearchParams(window.location.search);
    const viewMode = urlParams.get("viewMode");

    btnFind.addEventListener("click", async () => {
        const input = document.getElementById("cityInput");
        if (input.value) {
            const city = await getCityByName(input.value);
            if (city) {
                updateSpotFilterCityId(city.id);
            } else {
                updateSpotFilterCityId(null);
            }
        }
        if (viewMode === VIEW_MODE_YMAP) {
            await updatePoints();
        } else if (viewMode === VIEW_MODE_CARDS) {

        }
    });
});

// range
const distanceInput = document.getElementById("distanceInputId");

function updateBackground(el) {
    const value = (el.value - el.min) / (el.max - el.min) * 100;
    el.style.background = `linear-gradient(
    to right,
    var(--range-left-bg-color) 0%,
    var(--range-left-bg-color) ${value}%,
    var(--range-right-bg-color) ${value}%,
    var(--range-right-bg-color) 100%
  )`;
}

distanceInput.addEventListener("input", () => updateBackground(distanceInput));
updateBackground(distanceInput);

const distanceLabel = document.getElementById("distanceLabelId");

const distanceMap = {
    1: "500 м.",
    2: "1 км.",
    3: "1.5 км.",
    4: "2 км.",
    5: "3 км.",
    6: "4 км.",
    7: "5 км.",
    8: "10 км.",
    9: "20 км.",
    10: "100 км.",
    11: "1000 км."
};

function updateDistanceLabel() {
    const value = parseInt(distanceInput.value, 10);
    distanceLabel.textContent = distanceMap[value] ? `(${distanceMap[value]})` : "";
}

// первый раз при загрузке
updateDistanceLabel();

// при изменении ползунка
distanceInput.addEventListener("input", updateDistanceLabel);
