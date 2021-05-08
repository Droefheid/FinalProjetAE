import { RedirectUrl } from "./Router.js";
import { getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

const ConfirmVisits = () => {
  Sidebar(true);

  let list = `
  <div class="containerForm" id="confirmVisitDesc">
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header" >
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div class="col-sm-3"  ></div>
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
  const visit = e.target.parentElement.parentElement.id;
  if (visit == "nav_user") return;

  if (visit == null) return;

  let id = getTokenSessionDate();
  fetch(API_URL + "visits/" + visit, {
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
  <a href="#" class="previous">&laquo; Previous</a>
  <div id="messageBoardForm"></div>
  <table id="description_user" class="table table-striped table-bordered" style="width:100%" >
  <thead>
  <input type="hidden" id="id" value="${data.visit.id}">
  <input type="hidden" id="id_user" value="${user.id}">
            <tr>
                <th>Username</th>
                <th>Lastname</th>
                <th>Firstname</th>
                <th>Email</th>  
                <th>Request date</th>
                <th>Time slot</th>
                <th>Explanatory note</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${user.username}</td>
                <td>${user.lastName}</td>
                <td>${user.firstName}</td>
                <td>${user.email}</td>
                <td> ${data.visit.requestDate}</td>
                <td>${data.visit.timeSlot}</td>
                <td>${data.visit.explanatoryNote}</td>
            </tr>
            <tr>

           <td> 
    <button id="id_truebtn" class="btn btn-success" >Yes</button> </td>
    <td><button id="id_falsebtn" class="btn btn-danger">No</button>
  </td>
            </tr>
       </tbody>
  </table>
  `;

  info.innerHTML = description;
  let btn = document.getElementById("id_truebtn");
  let btn2 = document.getElementById("id_falsebtn");
  btn.addEventListener("click", onConfirmVisit);
  btn2.addEventListener("click", onDenyVisit);
};

const onConfirmVisit = (e) => {
  e.preventDefault();
  let info = document.querySelector("#confirmVisitDesc");
  let visitID = document.getElementById("id").value;
  let userID = document.getElementById("id_user").value;
  let confirm = true;

  let description = `
  <a href="#" class="previous">&laquo; Previous</a>
  <div id="messageBoardForm"></div>
  Date of visit 
  <br>
  <input placeholder="Please input the date of the visit" class="textbox-n" type="text" onfocus="(this.type='datetime-local')" onblur="(this.type='datetime-local')" id="datetime-local" />
  <input type="hidden" id="id" value="${visitID}">
  <input type="hidden" id="id_user" value="${userID}">
  <button id="button_confirmed" class="btn btn-success" >Submit</button>
  `;
  info.innerHTML = description;
  let confirmed = document.getElementById("button_confirmed");
  confirmed.addEventListener("click", onConfirm);
};

const onConfirm = () => {
  let visit_id = document.getElementById("id").value;
  let user_id = document.getElementById("id_user").value;
  let confirm = true;
  let date_time = document.getElementById("datetime-local").value;
  let explanatory_note = "";
  let visit = {
    visitId: visit_id,
    userId: user_id,
    isConfirmed: confirm,
    dateTime: date_time,
    explanatoryNote: explanatory_note,
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
  e.preventDefault();
  let info = document.querySelector("#confirmVisitDesc");

  let visitID = document.getElementById("id").value;
  let userID = document.getElementById("id_user").value;

  let description = `
  <a href="#" class="previous">&laquo; Previous</a>
  <div id="messageBoardForm"></div>
  Explanatory note
  <br>
  <input type="textarea" id="explanatory_note" >
  <input type="hidden" id="id" value="${visitID}">
  <input type="hidden" id="id_user" value="${userID}">
  <button id="button_confirmedDeny" class="btn btn-success" >Submit</button>
  `;
  info.innerHTML = description;
  let confirmDeny = document.getElementById("button_confirmedDeny");
  confirmDeny.addEventListener("click", onConfirmDeny);
};

const onConfirmDeny = () => {
  let visit_id = document.getElementById("id").value;
  let user_id = document.getElementById("id_user").value;
  let explanatory_note = document.getElementById("explanatory_note").value;
  let date_time = "";

  let visit = {
    visitId: visit_id,
    userId: user_id,
    explanatoryNote: explanatory_note,
    dateTime: date_time,
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
    } else return onConfirmedDenyVisit();
  });
};

const onConfirmedDenyVisit = () => {
  alert("Visit has not been confirmed");
  RedirectUrl("/confirmVisits");
};

const onConfirmedVisit = () => {
  alert("Visit has been confirmed");
  RedirectUrl("/confirmVisits");
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

export default ConfirmVisits;
