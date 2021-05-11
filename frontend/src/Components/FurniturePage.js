import { API_URL, ALERT_BOX } from "../utils/server.js";
import Sidebar from "./SideBar";
import { user_me } from "../index.js";
import { getTokenSessionDate, getUserSessionData } from "../utils/session.js";
import { RedirectUrl } from "./Router.js";

let page = document.querySelector("#page");

const FurniturePage = () => {
    Sidebar(true, false);

    fetch(API_URL + "furnitures/" + user_me.furnitureId, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
    })
    .then((response) => {
    if (!response.ok) {
        return response.text().then((err) => onError(err));
    }
    else
        return response.json().then((data) => fetchTypes(data));
    });
};

const fetchTypes = (oldData) => {
    fetch(API_URL + "types/", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
    })
    .then((response) => {
    if (!response.ok) {
        return response.text().then((err) => onError(err));
    }
    else
        return response.json().then((data) => onFurnitureDescription(oldData, data.types));
    });
}

const onFurnitureDescription = (data, types) => {
    let furniture = data.furniture;
    let photos = data.photos;

    page.innerHTML = `<div class="mb-5">
        <div id="messageBoardForm"></div>
        <h3>${furniture.furnitureTitle}</h3>
        <div id="showImg" class="row"></div>
        <div class="row">
            <p><strong> Types : </strong> ${types[furniture.type].name}</p>
        </div>
        <div class="row">
            <p><strong> State : </strong> ${furniture.state}</p>
        </div>
        <span id="optionform"></span>
        <span id="updateForm"></span>
    </div>`;

    let showImg = document.getElementById('showImg');
    photos.forEach(photo => {
        showImg.innerHTML += `<img class="img-fluid width-200px" src="${photo.picture}" alt="${photo.name}" >`;
    });

    // Set Buttons.
    const user = getUserSessionData();
    if(user && user.isBoss){
        let updateFurniture = document.querySelector("#updateForm");
        updateFurniture.innerHTML += `<form class="btn" id="updateB">
        <input id="idUpdate" value="${data.furniture.furnitureId}" hidden>
        <input class="btn-primary" type="submit" value="Update">
    </form>`;
    let updateButton = document.querySelector("#updateB");
    updateButton.addEventListener("submit", onUpdate);
    }

    if(user && data.furniture.state === "EV") {
        let divOption = document.querySelector("#optionform");
        divOption.innerHTML+= `<form class="btn" id="option">
        <input id="idOption" value="${data.furniture.furnitureId}" hidden>
        <input class="btn-primary" type="submit" value="Introduce option">
        </form>`;
        let optionButton = document.querySelector("#option");
        optionButton.addEventListener("submit", onOption);
    }else if(user && data.furniture.state === "O"){
        let id = getTokenSessionDate();
        fetch(API_URL + "options/"+ data.furniture.furnitureId, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":id
        },
        })
        .then((response) => {
        if (!response.ok) {
            return;
        }
        else
            return response.json().then((data) => showStopOptionButton(data));
        });
    }
};

const onUpdate = (e) => {
    e.preventDefault();
    let furnitureId = document.getElementById("idUpdate").value;
    user_me.furnitureId = furnitureId;
    RedirectUrl(`/updateFurniture`);
};

const onOption = (e) => {
    e.preventDefault();
    let furnitureId = document.getElementById("idOption").value;
    user_me.furnitureId = furnitureId;
    RedirectUrl(`/introduceOption`);
};

const showStopOptionButton = (data) => {
    let divOption = document.querySelector("#optionform");
    divOption.innerHTML +=`<form class="btn" id="option">
    <input id="furnitureID" value="${data.option.furniture}" hidden>
    <input id="optionID" value="${data.option.id}" hidden>
    <input class="btn-primary" type="submit" value="Stop option">
    </form>`;
    let optionButton = document.querySelector("#option");
    optionButton.addEventListener("submit", stopOption);
};
  
const stopOption = (e) => {
    e.preventDefault();
    let id =getTokenSessionDate();
    let furnitureID = document.getElementById("furnitureID").value;
    let optionID = document.getElementById("optionID").value;
    let option ={
      "furnitureID":furnitureID,
      "optionID":optionID,
    }
    fetch(API_URL + "options/", {
      method: "PUT",
      body: JSON.stringify(option),
      headers: {
        "Content-Type": "application/json",
        "Authorization":id
      },
    })
    .then((response) => {
        if (!response.ok) {
            return response.text().then((err) => onError(err));
        }
        else{
            alert("The option on this furniture has been stopped.");
            RedirectUrl(`/furniture`);
        }
    });
};

const onError = (err) => {
    let messageBoard = document.querySelector("#messageBoard");
    if(err.message) ALERT_BOX(messageBoard, err.message);
    else ALERT_BOX(messageBoard, err);
};

export default FurniturePage;