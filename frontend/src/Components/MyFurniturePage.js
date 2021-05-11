import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import Navbar from "./Navbar.js";
import { user_me } from "../index.js";
import { getTokenSessionDate, getUserSessionData } from "../utils/session.js";

let page = document.querySelector("#page");

const MyFurniturePage = async () => {
  const user = getUserSessionData();
	if (!user) {
		Navbar();
		RedirectUrl(`/`);
    return;
  }

  Sidebar(true, false);

  page.innerHTML = `
    <div id="messageBoardForm"></div>
    <div id="list"></div>
    <div id="furnitureDesc" class="mb-5"></div> 
    `;
  let id = getTokenSessionDate();

  fetch(API_URL + "furnitures/myFurnitures", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization": id
    },
  })
  .then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    }
    else
      return response.json().then((data) => getTypes(data));
  });
};

const getTypes = (oldData) => {
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
      return response.json().then((data) => onFurnitureList(oldData,data.types));
  });
}
  
const onFurnitureList = (data, types) => {
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
  let furnitures = data.list;
  let photos = data.photos;
  for (let i = 0; i < furnitures.length; i++) {
    table += `
      <li id="${furnitures[i].furnitureId}" class="list-group-item" data-toggle="collapse"
      href="#collapse${furnitures[i].furnitureId}" role="button"
      aria-expanded="false" aria-controls="collapse${furnitures[i].furnitureId}">
        <div class="row" id="${furnitures[i].furnitureId}" >
          <div class="col-sm-4" id="${furnitures[i].furnitureId}">`;
          if(photos[i]) table += `<img src="${photos[i].picture}" alt="${photos[i].name}" class="rounded" style="width:100%;"/>`;
          table += `</div>
          <div class="col-sm-">
            <p>
              <h5>${furnitures[i].furnitureTitle}</h5>
              Type : ${types[furnitures[i].type].name}
            </p>
          </div>
        </div>
      </li>`;
  }

  table += `  
        </ul>
      </nav>
    `;
  furnitureList.innerHTML = table;

  const viewFurnitures = document.querySelectorAll("li");
  viewFurnitures.forEach((elem) => {
    elem.addEventListener("click", function (e) { onClick(e, types); });
  });
}

const onClick = (e, types) => {
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
            "Authorization": id
          },
        })
        .then((response) => {
          if (!response.ok) {
            return response.text().then((err) => onError(err));
          }
          else
            return response.json().then((data) => onFurnitureDescription(data, types));
        });
      }else{
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
            return response.json().then((data) => onFurnitureDescription(data, types));
        });
      }
  };
  
  const onFurnitureDescription = (data, types) => {
    let info = document.querySelector("#furnitureDesc");

    console.log(data);
  
    let description = `
    <div id="description_furniture">
      <h4>${data.furniture.furnitureTitle}</h4>
      <div id="showImg"></div>
      <p>Type : ${types[data.furniture.type].name} </br>
         State : ${data.furniture.state}
           </p>
           <span id="updateForm"></span>
       
    </div>`;
  
    info.innerHTML = description; 

    let photos = data.photos;
    let showImg = document.getElementById('showImg');
    photos.forEach(photo => {
      showImg.innerHTML += `<img class="width-15" src="` + photo.picture + `" alt="First slide" >`;
    });
  
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
  };
  
  const onUpdate = (e) => {
    e.preventDefault();
    let furnitureId = document.getElementById("id").value;
    user_me.furnitureId = furnitureId;
    RedirectUrl(`/updateFurniture`);
  }
  
  const onError = (err) => {
    let messageBoard = document.querySelector("#messageBoardForm");
    messageBoard.innerHTML = err;
  };
  
export default MyFurniturePage;