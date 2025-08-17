
document.addEventListener('DOMContentLoaded', function() {
    const acceptSpotAddingRequestFetch = () => {
        const formData = new FormData();
        formData.append("spotAddingRequestId", spotAddingRequestId.toString());

        fetch(acceptSpotAddingRequestUrl, {
            method: "PATCH",
            body: formData
        }).then((response) => {
            if (response.ok) {
                console.log(response);
            } else {
                console.log("Network request failed with response " +
                    response.status + ": " + response.statusText + "; " + response.body
                );
                console.log(response);
            }
        }).catch(err => {
            console.log(err);
        });
    };

    const acceptSpotAddingRequest = () => {
        acceptSpotAddingRequestFetch();
    };

    const btnAccept = document.getElementById("spotAddingRequestId");

    btnAccept.addEventListener("click", () => acceptSpotAddingRequest());
});
