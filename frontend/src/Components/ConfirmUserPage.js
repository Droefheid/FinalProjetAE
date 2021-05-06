import { RedirectUrl } from "./Router.js";
import { getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const ConfirmUserPage = async () => {
  Sidebar(true, true);

  let list = `
  <div class="containerForm">
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header" id="confirmUserDesc">
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div id="messageBoardForm"></div>
  </div>
  </div>
  `;
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
  let userList = document.querySelector("#list");

  if (!data) return;

  if (data.list.length == 0) {
    page.innerHTML = "<h3> There aren't any users to confirm </h3>";
    return;
  }
  let table = `
  <div class="input-group rounded" id="search_user_list">
  <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
    aria-describedby="search-addon" />
  <span class="input-group-text border-0" id="search-addon">
    <i class="fas fa-search"></i>
  </span>
</div>
<nav id="nav_user">
  <ul class="list-group">`;
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
      </nav>
`;
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
                 <td><input class="form-check-input" type="checkbox" id="is_confirmed" >
                 <label class="form-check-label" for="is_confirmed">Is confirmed </label></td>
                <td><input class="form-check-input" type="checkbox" id="is_antique_dealer" >
                <label class="form-check-label" for="is_antique_dealer">Is antique dealer </label></td>
                <td><input class="form-check-input" type="checkbox" id="is_boss" >
                <label class="form-check-label" for="is_boss">Is Boss </label></td>
                <td></td>
            </tr>
       </tbody>
  </table>
  <br>
  <input class="btn btn-primary" type="button" id="button_confirmed" value="Submit">`;

  info.innerHTML = description;

  let btn = document.getElementById("button_confirmed");
  btn.addEventListener("click", onConfirmUser);
};

const onConfirmUser = (e) => {
  e.preventDefault();
  let userID = document.getElementById("id").value;
  let confirmed = document.getElementById("is_confirmed").checked;
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
