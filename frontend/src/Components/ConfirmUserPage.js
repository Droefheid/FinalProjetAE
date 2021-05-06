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
    <div class="card-header">
  <div class="col-sm-3" id="list"> </div>
  </div>
  </div>
  <div class="col-sm-3"  id="confirmUserDesc"></div>
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

  let table = `
          <nav id="nav_user">
            <ul class="list-group">`;
  data.list.forEach((element) => {
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
  <div id="description_user">
  <p> Username: ${data.user.username}</p>
  <p> Lastname: ${data.user.lastName}</p>
  <p> Firstname: ${data.user.firstName}</p>
  <p> Email: ${data.user.email}</p>
    <input type="hidden" id="id" value="${data.user.id}">
    <br>

    <input class="form-check-input" type="checkbox" id="is_confirmed" >
    <label class="form-check-label" for="is_confirmed">Is confirmed </label>
    <br>
    <input class="form-check-input" type="checkbox" id="is_antique_dealer" >
    <label class="form-check-label" for="is_antique_dealer">Is antique dealer </label>
    <br>
    <input class="form-check-input" type="checkbox" id="is_boss" >
    <label class="form-check-label" for="is_boss">Is Boss </label>
  <br>
  <input class="btn btn-primary" type="button" id="button_confirmed" value="Submit">
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

const getCoordinates = (obj) => {
  let address = obj.address;
  let addressFinal= address.street+" " + address.buildingNumber+" " + address.country+" " + address.commune+" " + address.postCode;
  let test = "Rue du duc 29 bruxelles";
  const Http = new XMLHttpRequest();
  const url=`http://api.positionstack.com/v1/forward?access_key=c9f2d2aaa769991d9e4d60e371687223&query=${test}`;
  Http.open("GET", url);
  Http.send();

  Http.onreadystatechange=function(){
    if(this.readyState==4 && this.status==200){
      let obj = JSON.parse(Http.response);
      createMap(obj.data[0].latitude,obj.data[0].longitude);
    }
  }
};

const createMap = (latitude, longitude) => {
  var attribution = new ol.control.Attribution({
    collapsible: false
  });

  var map = new ol.Map({
      controls: ol.control.defaults({attribution: false}).extend([attribution]),
      layers: [
          new ol.layer.Tile({
              source: new ol.source.OSM({
                  url: 'https://tile.openstreetmap.be/osmbe/{z}/{x}/{y}.png',
                  attributions: [ ol.source.OSM.ATTRIBUTION, 'Tiles courtesy of <a href="https://geo6.be/">GEO-6</a>' ],
                  maxZoom: 18
              })
          })
      ],
      target: 'map',
      view: new ol.View({
          center: ol.proj.fromLonLat([4.35247, 50.84673]),
          maxZoom: 18,
          zoom: 8
      })
  });

  var layer = new ol.layer.Vector({
    source: new ol.source.Vector({
        features: [
            new ol.Feature({
                geometry: new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude]))
            })
        ]
    })
});
map.addLayer(layer);
}


export default ConfirmUserPage;
