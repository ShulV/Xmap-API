const getFormattedDistance = (distanceInMeters) => {
    if (distanceInMeters) {
        const roundedDistance = Math.round(distanceInMeters);
        if (roundedDistance >= 1000) {
            const kilometers = (roundedDistance / 1000).toFixed(1);
            return `${kilometers} км`;
        } else {
            return `${roundedDistance} м.`;
        }
    } else {
        return null;
    }
}