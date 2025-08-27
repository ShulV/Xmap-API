
document.addEventListener('DOMContentLoaded', function() {
    const moderateSpotAddingRequestFetch = (action) => {
        const formData = new FormData();
        formData.append("spotAddingRequestId", spotAddingRequestId.toString());
        let url;
        if (action == 'accept') {
            url = acceptSpotAddingRequestUrl;
        } else if (action == 'reject') {
            url = rejectSpotAddingRequestUrl;
        }
        fetch(url, {
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
            location.reload();
        }).catch(err => {
            console.log(err);
        });
    };

    const acceptSpotAddingRequest = () => {
        acceptSpotAddingRequestFetch();
    };

    const btnAccept = document.getElementById("spotAddingRequestBtnAcceptId");
    const btnReject = document.getElementById("spotAddingRequestBtnRejectId");
    btnAccept.addEventListener("click", () => moderateSpotAddingRequestFetch('accept'));
    btnReject.addEventListener("click", () => moderateSpotAddingRequestFetch('reject'));

});
