function getUserLocation() {
    // Москва, Кремль
    const defaultLocation = {longitude: 37.620393, latitude: 55.75396};

    return new Promise((resolve, reject) => {
        if (!navigator.geolocation) {
            resolve(defaultLocation);
            return;
        }

        navigator.geolocation.getCurrentPosition(
            position => {
                resolve({
                    longitude: position.coords.longitude.toFixed(6),
                    latitude: position.coords.latitude.toFixed(6)
                });
            },
            error => {
                resolve(defaultLocation);
            }
        );
    });
}