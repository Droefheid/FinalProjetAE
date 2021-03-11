const API_URL = "/api/";
const ALERT_BOX = (messageBoard, errorMessage) => {
    messageBoard.innerHTML = '<div id="alertBox" class="alert alert-danger alert-dismissible mt-2">';
    let alertBox = document.querySelector("#alertBox");
    alertBox.innerText = errorMessage;
    // Add close button of alert
    alertBox.innerHTML += '<button type="button" class="close" data-dismiss="alert">&times;</button>';
}
export { API_URL, ALERT_BOX };