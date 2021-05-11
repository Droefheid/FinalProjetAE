
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import { RedirectUrl } from "./Router.js";
import Sidebar from "./SideBar.js";
import Navbar from "./Navbar.js";

let page = document.querySelector("#page");

const UserListPage = async () => {
  const user = getUserSessionData();
  if (!user || !user.isBoss) {
    // re-render the navbar for the authenticated user.
    Navbar();
    RedirectUrl("/");
    return;
  }

  let list = `
  <div class="containerForm mb-5">
    <center><h4>List of users </h4></center>
    <span id="previous"></span>
    <div class="d-flex justify-content-center h-100 mt-4" id="userInfo" >
      <div class="col-sm-6" id="list"></div>
    </div>
  </div>
  <div id="messageBoardForm"></div>`;

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
  });
};

const onSearchListUser = () => {
  let searchWord = document.getElementById("searchBar").value;
  if(searchWord === undefined || searchWord == null || searchWord.length <= 0){
    UserListPage();
  }else {
    let id = getTokenSessionDate();

    fetch(API_URL + "users/search/" + searchWord, {
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
  }

}

const onUserList = (data) => {
  Sidebar(true, true);
  let userList = document.querySelector("#list");

  if (!data) return;

  if (data.list.length == 0) {
    page.innerHTML = "<h3> There aren't any users </h3>";
    return;
  }

  let table = `
          
  <div class="input-group" style="margin-bottom:10px;">
  <div class="form-outline">
    <input type="search" id="searchBar" class="form-control" placeholder="Search" />
  </div>
  <button type="button" id="submitSearch" class="btn btn-primary">
    <i class="fas fa-search"></i>
  </button>
</div>
          <nav id="nav_user">
            <ul class="list-group">`;

  data.list.forEach((element) => {
    table += `

        <li id="${element.id}" class="list-group-item" data-toggle="collapse"
        href="#collapse${element.id}" role="button"
        aria-expanded="false" aria-controls="collapse${element.id}">
          <div class="row" id="${element.id}" >
            <div class="col-sm-4" id="${element.id}">
              <img src="assets/Images/pic.jpg" class="rounded" style="width:100%;"/>
            </div>
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

  let buttonSearch = document.getElementById("submitSearch");
  buttonSearch.addEventListener("click", onSearchListUser);

  const viewUsers = document.querySelectorAll("li");
  viewUsers.forEach((elem) => {
    elem.addEventListener("click", onClick);
  });
};

const onClick = (e) => {
  e.preventDefault();
  const userId = e.target.parentElement.parentElement.id;
  if (userId == "nav_user") return;

  if (userId == null) return;

  let id = getTokenSessionDate();
  fetch(API_URL + "users/" + userId, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else
      return response.json().then((data) => onConfirmUserDescription(data));
  });
};

const onConfirmUserDescription = (data) => {
  let description = `
  <table id="description_user" class="table table-striped table-bordered" style="width:100%" >
    <thead>
      <tr>
          <th>Username</th>
          <th>Lastname</th>
          <th>Firstname</th>
          <th>Email</th> 
          <th>Furniture sold</th>
          <th>Furniture bought</th>
      </tr>
    </thead>
    <tbody>
      <tr>
          <td>${data.user.username}</td>
          <td>${data.user.lastName}</td>
          <td>${data.user.firstName}</td>
          <td>${data.user.email}</td>
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
  let info = document.querySelector("#list");
  console.log(lists);

  let descriptionFinal = descripton;

  descriptionFinal += `<td>${lists.seller.length}
      <form class="btn" id="seller">
        <input class="btn-primary" type="submit" value="See furniture">
      </form>
    </td>  
    <td>${lists.buyer.length}
      <form class="btn" id="buyer">
        <input class="btn-primary" type="submit" value="See furniture">
      </form>
    </td>
    </tr>
    </tbody>
  </table>`;

  document.querySelector("#previous").innerHTML += `<div class="row"><a href="#" class="previous">&laquo; Previous</a></div>`;

  info.className = "d-flex h-100 mt-4";

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
    list: furnitures
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
            <img src="assets/Images/Bureau_1.png" class="rounded max_width" />
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
    <img src="assets/Images/Bureau_1.png" class="width-15"/>
    <p>Type : ${data.furniture.type} </br>
       State : ${data.furniture.state}
         </p>
  </div>`;
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
