import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import { user_me } from "../index.js";
import { getTokenSessionDate, getUserSessionData } from "../utils/session.js";

let page = document.querySelector("#page");

const FurniturePage = async () => {
  Sidebar(true);

  page.innerHTML = `
    <div id="messageBoardForm"></div>
    <div id="list"></div>
    <div id="furnitureDesc"></div> 
    `;

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
        return response.json().then((data) => onFurnitureList(data));
    })
};

const onFurnitureList = (data) => {

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
  data.list.forEach(element => {
    table += `
        <li id="${element.furnitureId}" class="list-group-item" data-toggle="collapse"
        href="#collapse${element.furnitureId}" role="button"
        aria-expanded="false" aria-controls="collapse${element.furnitureId}">
          <div class="row" id="${element.furnitureId}" >
            <div class="col-sm-4" id="${element.furnitureId}">
              <img src="assets/Images/Bureau_1.png" class="rounded" style="width:100%;"/>
            </div>
            <div class="col-sm-">
              <p>
                <h5>${element.furnitureTitle}</h5>
                Type : ${element.type}
              </p>
            </div>
          </div>
        </li>`;
  });

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
      })
};

const onFurnitureDescription = (data) => {
  let info = document.querySelector("#furnitureDesc");

  let description = `
  <div id="description_furniture">
    <h4>${data.furniture.furnitureTitle}</h4>
    <img src="assets/Images/Bureau_1.png" style="width:15%;"/>
    <p>Type : ${data.furniture.type} </br>
       State : ${data.furniture.state}
         </p>
         <span id="optionform"> </span>
         <span id="updateForm"></span>
     
  </div>`;

  info.innerHTML = description; 

  const user = getUserSessionData();
  if(user.isBoss){
    let updateFurniture = document.querySelector("#updateForm");
    updateFurniture.innerHTML += `<form class="btn" id="updateB">
    <input id="id" value="${data.furniture.furnitureId}" hidden>
    <input class="btn-primary" type="submit" value="Update">
   </form>`;
   let updateButton = document.querySelector("#updateB");
   updateButton.addEventListener("submit", onUpdate);
  }

  if(data.furniture.state !== "O" && data.furniture.state !== "V" ) {
    let divOption = document.querySelector("#optionform");
    divOption.innerHTML+= `<form class="btn" id="option">
    <input id="id" value="${data.furniture.furnitureId}" hidden>
    <input class="btn-primary" type="submit" value="Introduce option">
    </form>`;
    let optionButton = document.querySelector("#option");
    optionButton.addEventListener("submit", onOption);
  }else if(data.furniture.state === "O"){
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
      })
  }
};

const onUpdate = (e) => {
  e.preventDefault();
  let furnitureId = document.getElementById("id").value;
  user_me.furnitureId = furnitureId;
  RedirectUrl(`/updateFurniture`);
}

const onOption = (e) => {
  e.preventDefault();
  let furnitureId = document.getElementById("id").value;
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
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

export default FurniturePage;