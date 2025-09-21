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
        // let filter = restoreSpotFilter();
        // storeSpotFilter(filter);
        if (viewMode === VIEW_MODE_YMAP) {
            await updatePoints();
        } else if (viewMode === VIEW_MODE_CARDS) {

        }
    });
});
