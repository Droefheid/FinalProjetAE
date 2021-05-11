import { RedirectUrl } from "./Router.js";
import { API_URL, ALERT_BOX } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import { user_me } from "../index.js";
import { getTokenSessionDate, getUserSessionData } from "../utils/session.js";

let page = document.querySelector("#page");

const FurnituresPage = async () => {
  Sidebar(true, true);
  page.innerHTML = `<div id="messageBoardForm"></div>
  <div class="loader"></div>`;  

  fetch(API_URL + "furnitures/", {
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
      return response.json().then((data) => getTypeNames(data));
  });
};

const getTypeNames = (data) => {
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
        return response.json().then((types) => onFurnitureList(types,data));
    });
}

const onFurnitureList = (types,data) => {
  page.innerHTML = `
    <div id="messageBoardForm"></div>
    <div id="list"></div>
    <div id="furnitureDesc"></div> 
    `;

  let furnitureList = document.querySelector("#list");

  if (!data) return;
  let table = `
          <div class="input-group rounded" id="search_furniture_list">
            <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
              aria-describedby="search-addon" />
            <span class="input-group-text border-0" id="search-addon">
              <i class="fas fa-search"></i>
            </span>
          </div>
          <nav id="nav_furniture">
            <ul class="list-group">`;
  const user = getUserSessionData();
  let photos = data.photos;
  let furnitures = data.furnitures;
  for (let i = 0; i < furnitures.length; i++) {
    let type = types.types[furnitures[i].type-1];
    if(furnitures[i].state && (furnitures[i].state != "ER" || (user && user.isBoss))){
      table += `
      <li id="${furnitures[i].furnitureId}" class="list-group-item" data-toggle="collapse"
      href="#collapse${furnitures[i].furnitureId}" role="button"
      aria-expanded="false" aria-controls="collapse${furnitures[i].furnitureId}">
        <div class="row" id="${furnitures[i].furnitureId}" >
          <div class="col-sm-4" id="${furnitures[i].furnitureId}">`;
          if(photos[i] && photos[i].picture.startsWith("data")) table += `<img class="rounded max_width" src="` + photos[i].picture + `" alt="` + photos[i].name +`" />`;
          table += `</div>
          <div class="col-sm-">
            <p>
              <h5>${furnitures[i].furnitureTitle}</h5>
              Type : ${type.name}
            </p>
          </div>
        </div>
      </li>`;
    }
  }

  table += `  
        </ul>
      </nav>
`;
  furnitureList.innerHTML = table;

  const viewFurnitures = document.querySelectorAll("li");
  viewFurnitures.forEach((elem) =>{
    elem.addEventListener("click", onClick);
  })
}
 
const onClick = (e) => {
  let furnitureId = -1;
  if(e.target.id){
    furnitureId = e.target.id;
  }else {
    furnitureId = e.target.parentElement.parentElement.id;
  }

  if(furnitureId == 'nav_furniture') return;
  if(furnitureId == null ) return;


  let id = getTokenSessionDate();
  if(id){
    fetch(API_URL + "furnitures/" + furnitureId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": id,
      },
    })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return response.json().then((data) => onFurnitureDescription(data));
    });
  } else {
    fetch(API_URL + "furnitures/" + furnitureId, {
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
        return response.json().then((data) => onFurnitureDescription(data));
    });
  }
};

const onFurnitureDescription = (data) => {
  let info = document.querySelector("#furnitureDesc");

  let description = `
  <div id="description_furniture">
    <h4>${data.furniture.furnitureTitle}</h4>
    <div id="showImg"></div>
    <p>Type : ${data.furniture.type} </br>
       State : ${data.furniture.state}
         </p>
         <span id="optionform"></span>
         <span id="updateForm"></span>
     
  </div>`;
  
  info.innerHTML = description; 
  
  let photos = data.photos;
  let showImg = document.getElementById('showImg');
  photos.forEach(photo => {
    showImg.innerHTML += `<img class="width-100px" src="${photo.picture}" alt="${photo.name}" >`;
  });

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
}

const onOption = (e) => {
  e.preventDefault();
  let furnitureId = document.getElementById("idOption").value;
  user_me.furnitureId = furnitureId;
  RedirectUrl(`/introduceOption`);
}

const showStopOptionButton = (data) => {
  let divOption = document.querySelector("#optionform");
  divOption.innerHTML +=`<form class="btn" id="option">
  <input id="furnitureID" value="${data.option.furniture}" hidden>
  <input id="optionID" value="${data.option.id}" hidden>
  <input class="btn-primary" type="submit" value="Stop option">
  </form>`;
  let optionButton = document.querySelector("#option");
  optionButton.addEventListener("submit", stopOption);
}

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
    })
}


const onError = (err) => {
  page.innerHTML = `<div id="messageBoardForm">`;
  let messageBoard = document.querySelector("#messageBoard");
  if(err.message) ALERT_BOX(messageBoard, err.message);
  else ALERT_BOX(messageBoard, err);
};

export default FurnituresPage;