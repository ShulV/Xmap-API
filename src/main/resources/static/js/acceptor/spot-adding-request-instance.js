document.addEventListener('DOMContentLoaded', function() {

    const moderateSpotAddingRequestFetch = async (status) => {
        await changeSpotAddingRequestStatus(status, spotAddingRequestId.toString());
        location.reload();
    };

    const btnAccept = document.getElementById("spotAddingRequestBtnAcceptId");
    const btnReject = document.getElementById("spotAddingRequestBtnRejectId");

    btnAccept.addEventListener("click", () => moderateSpotAddingRequestFetch('ACCEPTED'));
    btnReject.addEventListener("click", () => moderateSpotAddingRequestFetch('REJECTED'));

});
