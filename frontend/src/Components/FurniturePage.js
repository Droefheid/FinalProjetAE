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
    console.log("onClick");
    const furnitureId = e.target.parentElement.parentElement.id;

    if(furnitureId == 'nav_furniture') return;
  
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
  console.log(data.furniture);

  let info = document.querySelector("#furnitureDesc");

  let description = `
  <div id="description_furniture">
    <h4>${data.furniture.furnitureTitle}</h4>
    <img src="assets/Images/Bureau_1.png" style="width:20%;"/>
    <p>Type : ${data.furniture.type} </br>
       State : ${data.furniture.state}
         </p>
         <button type="button" class="btn btn-primary">Option</button>
         <a class="btn btn-info" id="update">Modifier</a>
         <form id="updateB">
          <input id="id" value="${data.furniture.furnitureId}" hidden>
          <input type="submit" value="Update" class="btn btn-lg btn-outline-primary btn-block">
         </form>
  </div>`;

  info.innerHTML = description; 
  let updateButton = document.querySelector("#updateB");
  updateButton.addEventListener("submit", onUpdate);
};

const onUpdate = (e) => {
  e.preventDefault();
  let furnitureId = document.getElementById("id").value;
  user_me.furnitureId = furnitureId;
  RedirectUrl(`/updateFurniture`);
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  messageBoard.innerHTML = err;
};

export default FurniturePage;