const SPOT_LIST_CONTAINER_ID = "spot-list-content-id";
const SPOT_PAGINATION_NAV_ID = "spot-pagination-nav-id";
const SPOT_PAGINATION_ID = "spot-pagination-id";
const SPOT_PAGINATION_COUNT_ID = "spot-pagination-count-id";

const DEFAULT_PAGE_NUMBER = 0;
const DEFAULT_PAGE_SIZE = 10;

const getPageParams = () => {
    const params = new URLSearchParams(window.location.search);
    const pageNumber = parseInt(params.get("pageNumber"), 10);
    const pageSize = parseInt(params.get("pageSize"), 10);
    return {
        pageNumber: Number.isNaN(pageNumber) ? DEFAULT_PAGE_NUMBER : pageNumber,
        pageSize: Number.isNaN(pageSize) ? DEFAULT_PAGE_SIZE : pageSize
    };
};

const updateUrlParams = (pageNumber, pageSize, mode = "push") => {
    const params = new URLSearchParams(window.location.search);
    params.set("viewMode", "CARDS");
    params.set("pageNumber", pageNumber);
    params.set("pageSize", pageSize);
    const newUrl = `${window.location.pathname}?${params.toString()}`;

    if (mode === "replace") {
        history.replaceState(null, "", newUrl);
    } else {
        history.pushState(null, "", newUrl);
    }
};

const clearContainer = (container) => {
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
};

const createSpotCard = (spot) => {
    const link = document.createElement("a");
    link.href = `/spot/${spot.id}`;
    link.className = "col card card-container shadow-sm rounded spot__item-card";

    const imgWrapper = document.createElement("div");
    imgWrapper.className = "spot__item-img-wrapper img-placeholder__wrapper";

    const imgBg = document.createElement("div");
    imgBg.className = "img-placeholder__bg";

    const imgShimmer = document.createElement("div");
    imgShimmer.className = "img-placeholder__shimmer";

    const img = document.createElement("img");
    img.src = spot.firstImageLink;
    img.alt = "spot image";
    img.className = "rounded d-block spot__item-img img-placeholder__original-img";

    imgWrapper.appendChild(imgBg);
    imgWrapper.appendChild(imgShimmer);
    imgWrapper.appendChild(img);

    const info = document.createElement("div");
    info.className = "spot__item-info";

    const title = document.createElement("h5");
    title.className = "mt-2 spot__item-title";
    title.textContent = spot.name;

    const meta = document.createElement("div");
    meta.className = "spot__item-meta";

    const distance = document.createElement("div");
    distance.className = "spot__item-meta-chip spot__item-distance";
    const formattedDistance = getFormattedDistance(spot.distance);
    distance.textContent = formattedDistance ? `${formattedDistance} от вас` : "без геометки";

    const insertedAt = document.createElement("div");
    insertedAt.className = "spot__item-meta-chip spot__item-inserted-date";
    insertedAt.textContent = spot.insertedAt;

    meta.appendChild(distance);
    meta.appendChild(insertedAt);

    info.appendChild(title);
    info.appendChild(meta);

    link.appendChild(imgWrapper);
    link.appendChild(info);
    return link;
};

const renderSpotList = (spots) => {
    const container = document.getElementById(SPOT_LIST_CONTAINER_ID);
    if (!container) return;

    clearContainer(container);

    if (!spots || spots.length === 0) {
        const empty = document.createElement("div");
        empty.textContent = "Нет доступных мест.";
        container.appendChild(empty);
        return;
    }

    spots.forEach(spot => {
        container.appendChild(createSpotCard(spot));
    });
};

const createPaginationLink = (pageNumber, pageSize, labelHtml, isActive) => {
    const li = document.createElement("li");
    li.className = "pagination-row__element";
    if (isActive) {
        li.classList.add("active");
    }

    const link = document.createElement("a");
    link.className = "pagination-row__page-link";
    link.href = `?viewMode=CARDS&pageNumber=${pageNumber}&pageSize=${pageSize}`;
    link.innerHTML = labelHtml;
    link.addEventListener("click", async (event) => {
        event.preventDefault();
        await loadSpotCards(pageNumber, pageSize, "push");
    });

    li.appendChild(link);
    return li;
};

const renderPagination = (page) => {
    const paginationNav = document.getElementById(SPOT_PAGINATION_NAV_ID);
    const pagination = document.getElementById(SPOT_PAGINATION_ID);
    const count = document.getElementById(SPOT_PAGINATION_COUNT_ID);
    if (!paginationNav || !pagination || !count) return;

    clearContainer(pagination);
    count.textContent = "";
    paginationNav.style.display = "none";

    if (!page || page.totalPages <= 1) return;
    paginationNav.style.display = "";

    const pageNumber = page.number;
    const pageSize = page.size;

    const firstSvg = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
            <g xmlns="http://www.w3.org/2000/svg" transform="matrix(-1 0 0 -1 22.5 32)">
                <path d="M5.6 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.84-5.84-5.84-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.44 6.44c-0.16 0.16-0.4 0.24-0.6 0.24zM0.84 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.84-5.84-5.84-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.44 6.44c-0.16 0.16-0.4 0.24-0.6 0.24z"></path>
            </g>
        </svg>
    `;

    const prevSvg = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
            <g xmlns="http://www.w3.org/2000/svg" transform="matrix(-1 0 0 -1 20 32)">
                <path d="M0.88 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.76-5.84-5.8-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.4 6.44c-0.2 0.16-0.4 0.24-0.6 0.24z"></path>
            </g>
        </svg>
    `;

    const nextSvg = `
        <svg viewBox="-12 0 32 32" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M0.88 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.76-5.84-5.8-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.4 6.44c-0.2 0.16-0.4 0.24-0.6 0.24z"></path>
        </svg>
    `;

    const lastSvg = `
        <svg viewBox="-9.5 0 32 32" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M5.6 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.84-5.84-5.84-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.44 6.44c-0.16 0.16-0.4 0.24-0.6 0.24zM0.84 23.28c-0.2 0-0.44-0.080-0.6-0.24-0.32-0.32-0.32-0.84 0-1.2l5.84-5.84-5.84-5.84c-0.32-0.32-0.32-0.84 0-1.2 0.32-0.32 0.84-0.32 1.2 0l6.44 6.44c0.16 0.16 0.24 0.36 0.24 0.6s-0.080 0.44-0.24 0.6l-6.44 6.44c-0.16 0.16-0.4 0.24-0.6 0.24z"></path>
        </svg>
    `;

    if (pageNumber > 0) {
        pagination.appendChild(createPaginationLink(0, pageSize, firstSvg, false));
        pagination.appendChild(createPaginationLink(pageNumber - 1, pageSize, prevSvg, false));
    }

    const start = Math.max(pageNumber - 2, 0);
    const end = Math.min(pageNumber + 2, page.totalPages - 1);

    for (let i = start; i <= end; i += 1) {
        pagination.appendChild(createPaginationLink(i, pageSize, `${i + 1}`, i === pageNumber));
    }

    if (pageNumber < page.totalPages - 1) {
        pagination.appendChild(createPaginationLink(pageNumber + 1, pageSize, nextSvg, false));
        pagination.appendChild(createPaginationLink(page.totalPages - 1, pageSize, lastSvg, false));
    }

    const totalElements = page.totalElements;
    if (typeof totalElements === "number" && totalElements > DEFAULT_PAGE_SIZE) {
        const lastDigit = totalElements % 10;
        const lastTwo = totalElements % 100;
        const suffix = (lastTwo >= 11 && lastTwo <= 14) ? "элементов" :
            (lastDigit === 1 ? "элемент" :
                (lastDigit >= 2 && lastDigit <= 4 ? "элемента" : "элементов"));
        count.textContent = `найдено ${totalElements} ${suffix}`;
    }
};

const loadSpotCards = async (pageNumber, pageSize, historyMode) => {
    try {
        const page = await getSpotsForCards(pageNumber, pageSize);
        renderSpotList(page.content);
        renderPagination(page);
        updateUrlParams(pageNumber, pageSize, historyMode);
    } catch (error) {
        const container = document.getElementById(SPOT_LIST_CONTAINER_ID);
        if (container) {
            clearContainer(container);
            container.textContent = "Ошибка при получении спотов.";
        }
    }
};

window.reloadSpotCards = async (pageNumber = DEFAULT_PAGE_NUMBER) => {
    const { pageSize } = getPageParams();
    await loadSpotCards(pageNumber, pageSize, "replace");
};

window.addEventListener("popstate", async () => {
    const params = new URLSearchParams(window.location.search);
    if (params.get("viewMode") !== "CARDS") return;
    const { pageNumber, pageSize } = getPageParams();
    await loadSpotCards(pageNumber, pageSize, "replace");
});

document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const viewMode = params.get("viewMode");
    if (viewMode !== "CARDS") return;

    const { pageNumber, pageSize } = getPageParams();
    await loadSpotCards(pageNumber, pageSize, "replace");
});
