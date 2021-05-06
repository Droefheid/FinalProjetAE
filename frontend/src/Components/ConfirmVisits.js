import { RedirectUrl } from "./Router.js";
import { getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const ConfirmVisitPage = () => {
  Sidebar(true);

  let list = `
  <div class="containerForm">
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header">
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div class="col-sm-3"  id="confirmVisitDesc"></div>
  <div id="messageBoardForm"></div>
  </div>
  </div>
  `;
  let id = getTokenSessionDate();
  page.innerHTML = list;
  fetch(API_URL + "visits/notConfirmed", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else return response.json().then((data) => onVisitList(data));
  });
};

const onVisitList = (data) => {
  if (!data) return;
  showVisitList(data.users, data.visits);
};

const showVisitList = (users, visits) => {
  let visitList = document.querySelector("#list");
  let table = `
  <nav id="nav_visit">
    <ul class="list-group">`;

  let name;
  visits.forEach((element) => {
    users.forEach((user) => {
      if (user.id == element.userId) {
        name = user.username;
      }
    });

    table += `
    <li id="${element.id}" class="list-group-item" data-toggle="collapse"
    href="#collapse${element.id}" role="button"
    aria-expanded="false" aria-controls="collapse${element.id}">
      <div class="row" id="${element.id}" >
      
        <div class="col-sm-">
          <p> 
          <h5>${name}</h5>               
          </p>
        </div>
      </div>
      </li>`;
  });

  table += `  
</ul>
</nav>
`;
  visitList.innerHTML = table;
  const viewUsers = document.querySelectorAll("li");
  viewUsers.forEach((elem) => {
    elem.addEventListener("click", onClick);
  });
};

const onClick = (e) => {
  e.preventDefault();
  const visitId = e.target.parentElement.parentElement.id;
  if (visitId == "nav_visit") return;

  if (visitId == null) return;

  let id = getTokenSessionDate();
  fetch(API_URL + "visits/" + visitId, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else
      return response.json().then((data) => onConfirmVisitDescription(data));
  });
};

const onConfirmVisitDescription = (data) => {
  let id = getTokenSessionDate();
  fetch(API_URL + "users/" + data.visit.userId, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else
      return response.json().then((obj) => visitDescription(obj.user, data));
  });
};

const visitDescription = (user, data) => {
  data.visit.requestDate = createTimeStamp(data.visit.requestDate);
  let info = document.querySelector("#confirmVisitDesc");
  let description = `
  <div id="description_user">
  <p> Username: ${user.username}</p>
  <p> Lastname: ${user.lastName}</p>
  <p> Firstname: ${user.firstName}</p>
  <p> Email: ${user.email}</p>
  <p> Request Date: ${data.visit.requestDate}</p>
  <p> Time slot: ${data.visit.timeSlot}</p>
  <p> Explanatory note: ${data.visit.explanatoryNote}</p>
  <input type="hidden" id="id" value="${data.visit.id}">
  <input type="hidden" id="id_user" value="${user.id}">
  </div>
  <div id="id_confrmdiv">
    <button id="id_truebtn" class="btn btn-success" >Yes</button>
    <button id="id_falsebtn" class="btn btn-danger">No</button>
  </div>
  `;

  info.innerHTML = description;

  let btn = document.getElementById("id_truebtn");
  let btn2 = document.getElementById("id_falsebtn");
  btn.addEventListener("click", onConfirmVisit);

  btn2.addEventListener("click", onDenyVisit);
};

const onConfirmVisit = (e) => {
  e.preventDefault();
  let visitID = document.getElementById("id").value;
  let userID = document.getElementById("id_user").value;
  let confirmed = true;

  let visit = {
    visitId: visitID,
    user: userID,
    isConfirmed: confirmed,
  };

  let id = getTokenSessionDate();
  fetch(API_URL + "visits/", {
    method: "PUT",
    body: JSON.stringify(visit),
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else return onConfirmedVisit();
  });
};

const onDenyVisit = (e) => {
  let info = document.querySelector("#confirmVisitDesc");

  info.innerHTML = description;
};

const onConfirmedVisit = () => {
  RedirectUrl(`/confirmVisits`);
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

const createTimeStamp = (dateString) => {
  let Timestamp = new Date(dateString);
  let timeSplit = Timestamp.toLocaleString().split("/");
  return (
    timeSplit[2].substr(0, 4) +
    "-" +
    timeSplit[1] +
    "-" +
    timeSplit[0] +
    " " +
    Timestamp.toLocaleTimeString()
  );
};

export default ConfirmVisitPage;
