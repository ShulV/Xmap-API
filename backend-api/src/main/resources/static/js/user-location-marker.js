async function addOrUpdateUserLocationMarker(map, currentMarker = null, source = null) {
    const location = await getUserLocation(false);
    if (!location?.longitude || !location?.latitude) return currentMarker;

    const coordinates = [Number(location.longitude), Number(location.latitude)];
    if (!Number.isFinite(coordinates[0]) || !Number.isFinite(coordinates[1])) return currentMarker;

    if (currentMarker) {
        currentMarker.update({coordinates});
        return currentMarker;
    }

    const markerProps = source ? {coordinates, source} : {coordinates};
    const userLocationMarker = new ymaps3.YMapMarker(markerProps, createUserLocationPinElement());
    map.addChild(userLocationMarker);
    return userLocationMarker;
}

function createUserLocationPinElement() {
    const marker = document.createElement('div');
    marker.className = 'user-location-pin';

    const dot = document.createElement('div');
    dot.className = 'user-location-pin__dot';

    marker.appendChild(dot);
    return marker;
}
