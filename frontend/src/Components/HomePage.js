import { API_URL, ALERT_BOX } from "../utils/server.js";
import Sidebar from "./SideBar";

let page = document.querySelector("#page");

const HomePage = () => {   
  fetch(API_URL + "furnitures/", {
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
      return response.json().then((data) => onFurnitureList(data));
  });
};

const onFurnitureList = (data) => {
  let homePage = `<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">`;
    let photos = data.photos;
    let isAnActiveAlready = false;
    let j = 0;
    for (let i = 0; i < photos.length; i++) {
      if(photos[i]){
        homePage += `<i data-target="#carouselExampleIndicators" data-slide-to="` + j + `" class="fa fa-circle carouselCircle mr-1 `;
        j++;
        if(!isAnActiveAlready) {
          homePage += `active`;
          isAnActiveAlready = true;
        }
        homePage += `"></i>`;
      }
    }
    homePage += `</ol>
    <div class="carousel-inner">`;
    //console.log(photos);
    isAnActiveAlready = false;
    for (let i = 0; i < photos.length; i++) {
      if(photos[i] && photos[i].picture.startsWith("data")){
        homePage += `<div class="carousel-item`;
        if(!isAnActiveAlready) {
          homePage += ` active`;
          isAnActiveAlready = true;
        }
        homePage += `"><img class="d-block" src="` + photos[i].picture + `" alt="First slide" >
        </div>`;
      }else if(photos[i] && !photos[i].picture.startsWith("data")){
        homePage += `<div class="carousel-item`;
        if(!isAnActiveAlready) {
          homePage += ` active`;
          isAnActiveAlready = true;
        }
        homePage += `"><img class="d-block" src="assets/Images/Bureau_1.png" alt="First slide" >
        </div>`;
      }
    }
    homePage += `</div>
      <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <i class="material-icons carouselArrow">keyboard_arrow_left</i>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <i class="material-icons carouselArrow">keyboard_arrow_right</i>
      </a>
    </div>`;

   Sidebar(true, true);
   return page.innerHTML = homePage;
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  if(err.message) ALERT_BOX(messageBoard, err.message);
  else ALERT_BOX(messageBoard, err);
};

export default HomePage;
