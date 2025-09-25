async function getUserLocation(withDefault= true) {
    // Москва, Кремль longitude: 37.620393, latitude: 55.75396
    // Барнаул longitude: 83.743100, latitude: 53.344500
    const defaultLocation = {longitude: 83.743100, latitude: 53.344500};
    const emptyLocation = {longitude: null, latitude: null};
    return new Promise((resolve, reject) => {
        if (!navigator.geolocation) {
            if (withDefault) {
                resolve(defaultLocation);
            } else {
                resolve(emptyLocation);
            }
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
                if (withDefault) {
                    resolve(defaultLocation);
                } else {
                    resolve(emptyLocation);
                }
            }
        );
    });
}