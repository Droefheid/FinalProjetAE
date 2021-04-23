import { RedirectUrl } from "./Router.js";
import { user_me } from "../index.js";
import { API_URL } from "../utils/server.js";
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import Navbar from "./Navbar.js";

const OptionPage = () => {
  const user = getUserSessionData();
  if (!user || !user.isBoss || !user_me.furnitureId) {
      // re-render the navbar for the authenticated user.
      Navbar();
      RedirectUrl("/");
  }else{
    fetch(API_URL + "furnitures/" + user_me.furnitureId, {
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
      });
  }
};

const onFurnitureDescription = (data) => {
  let optionPage = `
  <div id="option">
  <div id="messageBoardForm"></div>
    <img class="img-fluid" src="assets/Images/Bureau_1.png"  style="width:350px;">
  
    <div class="row">
      <div id="furniture" class="column">
          <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
          <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
      </div>
      <div class="column">
          <h4 style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;"> Types : ${data.furniture.type} </h4>
        <label for="datetime-local" >Date de fin de l'option</label>
       <input class="form-control" type="datetime-local" value="2021-08-19T13:45:00" id="datetime-local">
       <form class="btn" id="introduceOption">
       <input type="submit" value="Introduce option" class="btn btn-info"></input>
       </form>
      </div>
  </div>
  `;

  let page = document.querySelector("#page");
  page.innerHTML = optionPage;
  let optionForm = document.querySelector("#introduceOption");
  optionForm.addEventListener("submit", introduceOption);
}

const introduceOption = (e) => {
  e.preventDefault();
  let id = getTokenSessionDate();
  let datetime = document.getElementById("datetime-local").value;
  let furniture = user_me.furnitureId;

  let option ={
    "optionTerm":datetime,
    "furnitureID":furniture
  }

  fetch(API_URL + "options/introduceOption", {
    method: "POST",
    body: JSON.stringify(option),
    headers: {
      "Content-Type": "application/json",
      "Authorization":id
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return onOptionIntroduced();
    })
};

const onOptionIntroduced = () => {
  alert("An option has been introduced");
  RedirectUrl(`/furniture`);
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

 export default OptionPage;
