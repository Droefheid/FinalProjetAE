import { RedirectUrl } from "./Router.js";
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import Navbar from "./Navbar.js";
import { getCoordinates } from "../utils/map.js";

let page = document.querySelector("#page");

const ConfirmUserPage = async () => {
  const user = getUserSessionData();
  if (!user || !user.isBoss) {
    // re-render the navbar for the authenticated user.
    Navbar();
    RedirectUrl("/");
    return;
 }

  let list = `
  <div class="d-flex justify-content-center containerForm">
    <div class="card">
      <div class="card-header d-flex justify-content-center" id="confirmUserDesc">
        <div class="col-sm-3" id="list"> </div>
      </div>
    </div>
    <div id="messageBoardForm"></div>
  </div>`;
  let id = getTokenSessionDate();
  page.innerHTML = list;
  fetch(API_URL + "users/notConfirmed", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else return response.json().then((data) => onUserList(data));
  });
};

const onUserList = (data) => {
  Sidebar(true, false);
  let userList = document.querySelector("#list");

  if (!data) return;

  if (data.list.length == 0) {
    page.innerHTML = "<h3> There aren't any users to confirm </h3>";
    return;
  }
  let table = `
<nav id="nav_user">
  <ul class="list-group ">`;
  data.list.forEach((element) => {
    table += `
    <li id="${element.id}" class="list-group-item" data-toggle="collapse"
    href="#collapse${element.id}" role="button"
    aria-expanded="false" aria-controls="collapse${element.id}">
      <div class="row" id="${element.id}" >
       
          <img src="assets/Images/pic.jpg" class="rounded" style="width:100%;"/>
     
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
      </nav>`;
  userList.innerHTML = table;

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
  let info = document.querySelector("#confirmUserDesc");
  info.className = `card-header`;
  let description = `
  <a href="#" class="previous">&laquo; Previous</a>
  <table id="description_user" class="table table-striped table-bordered" style="width:100%" >
  <input type="hidden" id="id" value="${data.user.id}">
  <thead>
            <tr>
                <th>Username</th>
                <th>Lastname</th>
                <th>Firstname</th>
                <th>Email</th>  
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${data.user.username}</td>
                <td>${data.user.lastName}</td>
                <td>${data.user.firstName}</td>
                <td>${data.user.email}</td>
            </tr>
            <tr>
                <td><input class="form-check-input" type="checkbox" id="is_antique_dealer" >
                <label class="form-check-label" for="is_antique_dealer">Is antique dealer </label></td>
                <td><input class="form-check-input" type="checkbox" id="is_boss" >
                <label class="form-check-label" for="is_boss">Is Boss </label></td>
                <td></td>
            </tr>
       </tbody>
  </table>
  <br>
  <input class="btn btn-primary" type="button" id="button_confirmed" value="Confirm">
  `;
  getAdresse(data.user.adressID,description);
};


const getAdresse = (address_id, description) => {
  let id = getTokenSessionDate();

  fetch(API_URL + "users/" + "getAddress/"+ address_id, {
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
        return response.json().then((obj) => afficherListAvecAddress(obj,description));
    });
};
const afficherListAvecAddress = (address, description) =>{
  let info = document.querySelector("#confirmUserDesc");

  let descriptionFinal = description;
  descriptionFinal +=`
  <div id="map"></div>
  <div id="popup" class="ol-popup">
     <a href="#" id="popup-closer" class="ol-popup-closer"></a>
     <div id="popup-content"></div>
 </div>`;
  getCoordinates(address);
  info.innerHTML = descriptionFinal;
  let btn = document.getElementById("button_confirmed");
  btn.addEventListener("click", onConfirmUser);
};


const onConfirmUser = (e) => {
  e.preventDefault();
  let userID = document.getElementById("id").value;
  let confirmed = true;
  let antique_dealer = document.getElementById("is_antique_dealer").checked;
  let is_boss = document.getElementById("is_boss").checked;

  let user = {
    userId: userID,
    isConfirmed: confirmed,
    isAntiqueDealer: antique_dealer,
    isBoss: is_boss,
  };

  let id = getTokenSessionDate();
  fetch(API_URL + "users/", {
    method: "PUT",
    body: JSON.stringify(user),
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else return onConfirmedUser();
  });

};

const onConfirmedUser = () => {
  RedirectUrl(`/confirmUser`);
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

export default ConfirmUserPage;
