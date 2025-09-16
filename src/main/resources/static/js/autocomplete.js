document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("cityInput");
    const suggestionsBox = document.getElementById("citySuggestions");

    let debounceTimer;
    let selectedCityId = null; // тут храним id выбранного города

    input.addEventListener("input", () => {
        const substring = input.value.trim();
        selectedCityId = null; // сбрасываем, если юзер начинает заново вводить

        clearTimeout(debounceTimer);

        if (substring.length < 1) {
            suggestionsBox.style.display = "none";
            return;
        }

        debounceTimer = setTimeout(async () => {
            const cities = await getCitiesBySubstring(substring); // [{id, name}, ...]

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
                    input.value = city.name;       // показываем название
                    selectedCityId = city.id;      // сохраняем id
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

    // Пример применения фильтра
    const btnFind = document.querySelector("#filterModal .btn.btn-orange");
    btnFind.addEventListener("click", async () => {
        const filter = {cityId: null};
        if (selectedCityId) {
            filter.cityId = selectedCityId; // 👈 передаём id, а не name
        }
        await updatePoints(filter);
    });
});
