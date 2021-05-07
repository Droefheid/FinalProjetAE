
import { getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const UserListPage = async () => {
    Sidebar(true, true);
  let list = `
  <div id="container" class="containerForm">
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header">
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div class="col-sm-7"  id="userInfo"></div>
  <div id="messageBoardForm"></div>
  </div>
  </div>
  `;
  let id = getTokenSessionDate();
  page.innerHTML = list;
  fetch(API_URL + "users/", {
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
        return response.json().then((data) => onUserList(data));
    })
};

const onUserList = (data) => {
  Sidebar(true);
  let userList = document.querySelector("#list");

  if (!data) return;
  
  let table = `
          <nav id="nav_user">
            <ul class="list-group">`;
  data.list.forEach(element => {
          table += `
        <li id="${element.id}" class="list-group-item" data-toggle="collapse"
              href="#collapse${element.id}" role="button"
              aria-expanded="false" aria-controls="collapse${element.id}">
                <div class="row" id="${element.id}" >
                
                  <div class="col-sm-">
                    <p>
                      <h5>${element.username}</h5>
                    </p>
                  </div>
                </div>
        </li>`;
  });

  table += `  
        </ul>
      </nav>
`;
  userList.innerHTML = table;

  const viewUsers = document.querySelectorAll("li");
  viewUsers.forEach((elem) =>{
    elem.addEventListener("click", onClick);
  })
}
 
const onClick = (e) => {
  e.preventDefault();
    const userId = e.target.parentElement.parentElement.id;
    if(userId == 'nav_user') return;

    if(userId == null) return;
  
    let id = getTokenSessionDate();
    fetch(API_URL + "users/" + userId, {
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
          return response.json().then((data) => onConfirmUserDescription(data));
      })
};

const onConfirmUserDescription = (data) => {
  let description = `
  <div id="description_user">
  <p> Username: ${data.user.username}</p>
  <p> Lastname: ${data.user.lastName}</p>
  <p> Firstname: ${data.user.firstName}</p>
  <p> Email: ${data.user.email}</p>
  <p> Registration date: ${createTimeStamp(data.user.registrationDate)}</p>
  <p> is a boss : ${data.user.isBoss}</p>
  <p> is an antique dealer: ${data.user.isAntiqueDealer}</p>
`;

  let id = getTokenSessionDate();
  fetch(API_URL + "furnitures/clientFurnitures/" + data.user.id, {
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
        return response.json().then((data) => furnitureInfo(data,description));
    });
};

const furnitureInfo = (lists,descripton) => {
  let info = document.querySelector("#userInfo");
  let descriptionFinal = descripton;
  descriptionFinal += `
    <p> furniture sold :${lists.seller.length} </p>  <form class="btn" id="seller">
    <input class="btn-primary" type="submit" value="See furniture">
    </form>
    <p> furniture bought : ${lists.buyer.length}  </p><form class="btn" id="buyer">
    <input class="btn-primary" type="submit" value="See furniture">
    </form>
    </div>`;
  info.innerHTML = descriptionFinal; 
  let sellerButton = document.querySelector("#seller");
  sellerButton.addEventListener("submit", function(e){
    e.preventDefault();
    onSeller(lists.seller)});
  let buyerButton = document.querySelector("#buyer");
  buyerButton.addEventListener("submit", function(e){
    e.preventDefault();
    onBuyer(lists.buyer)});
};

const onSeller = (furnitures) => {
  let data = {
    list:furnitures
  };
  onFurnitureList(data);
};

const onBuyer = (furnitures) => {
  let data = {
    list:furnitures
  };
  onFurnitureList(data);
}


const onFurnitureList = (data) => {
  let furnitureList = document.querySelector("#userInfo"); 
  if (!data) return;
  let table= `<div id ="furnitureList">`;
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
    </nav></div>
  `;
  furnitureList.innerHTML = table;

  const viewFurnitures = document.querySelectorAll("li");
  viewFurnitures.forEach((elem) =>{
  elem.addEventListener("click", onClickFurniture);
  });
}

const onClickFurniture = (e) => {
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
  let info = document.querySelector("#userInfo");
  let test = document.querySelector("#description_furniture");
  if(test) test.innerHTML="";
  let html = info.innerHTML;
  let description = `
  <div id="description_furniture">
    <h4>${data.furniture.furnitureTitle}</h4>
    <img src="assets/Images/Bureau_1.png" style="width:15%;"/>
    <p>Type : ${data.furniture.type} </br>
       State : ${data.furniture.state}
         </p>
  </div>`;
  console.log(info.innerHTML);
  info.innerHTML = description; 
  info.innerHTML +=html;

  
  const viewFurnitures = document.querySelectorAll("li");
  viewFurnitures.forEach((elem) =>{
  elem.addEventListener("click", onClickFurniture);
  });
};

const createTimeStamp = (dateString) => {
  let Timestamp = new Date(dateString);
  let timeSplit = Timestamp.toLocaleString().split("/");
  return timeSplit[2].substr(0, 4) + "-" + timeSplit[1] + "-" + timeSplit[0] + " " + Timestamp.toLocaleTimeString();
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};


export default UserListPage;