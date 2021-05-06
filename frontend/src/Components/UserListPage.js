import { getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const UserListPage = async () => {
  Sidebar(true, true);
  let list = `
  <div class="containerForm">
  <h4>List of users </h4>
<div class="d-flex justify-content-center h-100 mt-4" id="userInfo" >

  <div class="col-sm-6" id="list"  > </div>
  </div>
  </div>
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
    page.innerHTML = "<h3> There aren't any users </h3>";
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
  let info = document.querySelector("#userInfo");
  let description = `
  <a href="#" class="previous">&laquo; Previous</a>
  <table id="description_user" class="table table-striped table-bordered" style="width:100%" >
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
       </tbody>
  </table>
    `;

  info.innerHTML = description;
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

export default UserListPage;
