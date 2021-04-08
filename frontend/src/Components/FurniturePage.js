import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import { user_me } from "../index.js";

let page = document.querySelector("#page");

const FurniturePage = async () => {
  Sidebar(true);

  page.innerHTML = `
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

  var table = `
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
         <form class="btn" id="option">
         <input id="idUpdate" value="${data.furniture.furnitureId}" hidden>
         <input class="btn-primary" type="submit" value="Introduce option">
         </form>
         <form class="btn" id="updateB">
         <input id="idOption" value="${data.furniture.furnitureId}" hidden>
         <input class="btn-primary" type="submit" value="Update">
        </form>
     
  </div>`;

  info.innerHTML = description; 
  let updateButton = document.querySelector("#updateB");
  let optionButton = document.querySelector("#option");
  optionButton.addEventListener("submit", onOption);
  updateButton.addEventListener("submit", onUpdate);
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

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  messageBoard.innerHTML = err;
};

export default FurniturePage;