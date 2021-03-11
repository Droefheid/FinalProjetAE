import { API_URL, ALERT_BOX } from "./server.js";
import { setUserSessionData } from "./session.js"

let isLocalToken = null;

const VerifyUserToken = (id, isLocalItem) => {
    isLocalToken = isLocalItem;
    fetch(API_URL + "users/" + id, {
        method: "POST", 
        headers: {
          "Content-Type": "application/json",
        },
    })
    .then((response) => {
        if (!response.ok)
        throw new Error(
            "Error code : " + response.status + " : " + response.statusText
        );
        return response.json();
    })
    .then((data) => onUserFound(data))
    .catch((err) => onError(err));
};

const onUserFound = (userData) => {
    const user = { ...userData, isAutenticated: true };
    setUserSessionData(user, isLocalToken);
};

const onError = (err) => {
    let messageBoard = document.querySelector("#messageBoard");
    let errorMessage = "";
    if (err.message.includes("401")) {
        messageBoard.innerHTML = 'Wrong username or password.';
    }else{
        errorMessage = err.message;
        ALERT_BOX(messageBoard, errorMessage);
    }
};

export default VerifyUserToken;