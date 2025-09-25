document.addEventListener("DOMContentLoaded", async () => {
    const input = document.getElementById("cityInputId");
    const suggestionsBox = document.getElementById("citySuggestionsId");
    let debounceTimer;

    const cacheCityId = getSpotFilterCityId();

    if (cacheCityId) {
        const city = await getCityById(cacheCityId);
        input.value = city.name;
    }

    input.addEventListener("input", () => {
        const substring = input.value.trim();

        clearTimeout(debounceTimer);

        if (substring.length < 1) {
            suggestionsBox.style.display = "none";
            return;
        }

        debounceTimer = setTimeout(async () => {
            const cities = await getCitiesBySubstring(substring);

            suggestionsBox.innerHTML = "";

            if (cities.length === 0) {
                suggestionsBox.style.display = "none";
                return;
            }

            cities.forEach(city => {
                const li = document.createElement("li");
                li.textContent = city.name;
                li.className = "list-group-item list-group-item-action";
                li.style.cursor = "pointer";

                li.addEventListener("click", () => {
                    input.value = city.name;
                    updateSpotFilterCityId(city.id);
                    suggestionsBox.style.display = "none";
                });

                suggestionsBox.appendChild(li);
            });

            suggestionsBox.style.display = "block";
        }, 300);
    });

    // Скрываем подсказки, если кликнули вне
    document.addEventListener("click", (e) => {
        if (!suggestionsBox.contains(e.target) && e.target !== input) {
            suggestionsBox.style.display = "none";
        }
    });
});
