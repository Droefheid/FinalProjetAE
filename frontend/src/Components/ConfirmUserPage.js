import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const ConfirmUserPage = () => {
  Sidebar(true);

  let list = `
  
  <div class="container-fluid row justify-content-center" id="list"> </div>
  <div class="container-fluid row justify-content-center"  id="confirmUserDesc"></div>
  
  `;
  
  page.innerHTML = list;

  fetch(API_URL + "users/", {
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
        return response.json().then((data) => onUserList(data));
    })
};

const onUserList = (data) => {
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
  
    fetch(API_URL + "users/" + userId, {
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
          return response.json().then((data) => onConfirmUserDescription(data));
      })
};

const onConfirmUserDescription = (data) => {
  
  let info = document.querySelector("#confirmUserDesc");

  let description = `
  <div id="description_user">
    <h4>${data.user.username}</h4>
   
    <p>Last name : ${data.user.lastName} </br>
       First name : ${data.user.firstName}</br>
        Email : ${data.user.email}</br>
    </p>

    <div class="form-check">
  <input class="form-check-input" type="checkbox" id="is_confirmed" >
  <label class="form-check-label" for="flexCheckChecked">
    Is confirmed
  </label>

  <div class="form-check" id="${data.user.id}">
  <input class="form-check-input" type="checkbox" id="is_antique_dealer" >
  <label class="form-check-label" for="flexCheckChecked">
    Is antique dealer
  </label>
  <br>
  <input class="btn btn-primary" type="button" id="button_confirmed" value="Submit">
</div>
</div>
  </div>`;

  info.innerHTML = description; 

  let btn = document.getElementById("button_confirmed");
  btn.addEventListener("click",onConfirmUser);
};

const onConfirmUser = (e) =>{
  e.preventDefault();
  var userId = e.target.parentElement.id;
  var confirmed = false ;
  var antique_dealer = false;
 
  if (document.getElementById("is_confirmed").checked === true) {   
    confirmed = true;

  }

  if (document.getElementById("is_antique_dealer").checked === true) {   
    antique_dealer = true;
  }
  let user = { 
    "is_confirmed" : confirmed ,
    "is_antique_dealer" : antique_dealer,
    "user_id" : userId,
  };

  fetch(API_URL + "users/", {
    method: "PUT",
    body: JSON.stringify(user),
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return response.json().then((data) => onConfirmedUser());
    }) 
};
 
const onConfirmedUser = (e) => {

  RedirectUrl("/");
}


const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};


export default ConfirmUserPage;