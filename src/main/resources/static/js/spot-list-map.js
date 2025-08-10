document.addEventListener("DOMContentLoaded", function() {
    const listViewBtn = document.getElementById("list-view-btn-id");
    console.log(listViewBtn);

    const mapViewBtn = document.getElementById("map-view-btn-id");
    const spotMapContainer = document.getElementById("spot-map-container-id");
    const spotListContainer = document.getElementById("spot-list-container-id");

    const toggleSpotView = function () {
        spotMapContainer.classList.toggle("active");
        spotListContainer.classList.toggle("active");
        console.log("toggleSpotView");
    }
    listViewBtn.addEventListener("click", (e) => toggleSpotView());
    mapViewBtn.addEventListener("click", (e) => toggleSpotView());
})