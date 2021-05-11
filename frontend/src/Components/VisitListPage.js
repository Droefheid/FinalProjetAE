import { RedirectUrl } from "./Router.js";
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import Navbar from "./Navbar.js";
import { getCoordinates } from "../utils/map.js";

let page = document.querySelector("#page");

const VisitListPage = () => {
  const user = getUserSessionData();
  if (!user || !user.isBoss) {
    Navbar();
    RedirectUrl("/");
    return;
  }

  Sidebar(true, false);

  let list = `
  <div class="containerForm">
  <h4>List of visits</h4>
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header" id="confirmVisitDesc">
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div class="col-sm-3 pb-5"  id="confirmVisitDesc"></div>
  <div id="messageBoardForm"></div>
  </div>
  </div>
  `;
  let id = getTokenSessionDate();
  page.innerHTML = list;
  fetch(API_URL + "visits/confirmed", {
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

  if (!visits) return;
  if (visits == 0) {
    page.innerHTML = "<h3> There aren't any visits to confirm </h3>";
    return;
  }

  let table = `<nav id="nav_user">
  <ul class="list-group">`;

  //console.log(visits);
  //console.log(users);

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

const visitDescription = (user,data) => {
  //console.log(data);
  data.visit.requestDate = createTimeStamp(data.visit.requestDate);
  //let info = document.querySelector("#confirmVisitDesc");
  let description = `
  <a href="#" class="previous">&laquo; Previous</a>
  <table id="description_user" class="table table-striped table-bordered" style="width:100%" >
  <thead>
            <tr>
                <th>Username</th>
                <th>Lastname</th>
                <th>Firstname</th>
                <th>Email</th> 
                <th>Request Date</th>  
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
                <td>${data.visit.requestDate}</td>
                <td>${data.visit.timeSlot}</td>
                <td>${data.visit.explanatoryNote}</td>
          
            </tr>
       </tbody>
  </table>`;
  let photos = data.photos;
  for (let i = 0; i < photos.length; i++) {
    //console.log(photos[i]);
    description += `<img src="` + photos[i].picture + `" class="width-200px" alt="` + photos[i].name +`" />`;
  }

  getAdresse(data.visit.addressId,description);
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
  let info = document.querySelector("#confirmVisitDesc");

  let descriptionFinal = description;
  descriptionFinal +=`
  <div id="map"></div>
  <div id="popup" class="ol-popup">
     <a href="#" id="popup-closer" class="ol-popup-closer"></a>
     <div id="popup-content"></div>
  </div>`;
  getCoordinates(address);
  info.innerHTML = descriptionFinal;
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

export default VisitListPage;
