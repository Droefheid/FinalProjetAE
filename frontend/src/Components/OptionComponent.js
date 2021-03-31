
import { API_URL } from "../utils/server.js";
import { getUserSessionData } from "../utils/session.js";

let optionPage = `
<div id="option">
  <img class="img-fluid" src="assets/Images/Bureau_1.png"  style="width:350px;">

  <div class="row">
    <div id="furniture" class="col-sm-8">
        <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
        <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
        <h4 style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;"> Types : </h4>
    </div>


<label class="col-2 col-form-label">Date de fin de l'option</label>
<div id="messageBoardForm"></div>
<div>
    <div class="form-group row">
  
  <div class="col-10">
    <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="datetime-local">
  </div>
</div>
    <div>
    <input type="submit" class="btn btn-info">Introduire votre option</input>
    </div>
  </div>
</div>
</div>
`;


const OptionPage = () => {
  let page = document.querySelector("#content");
  page.innerHTML = optionPage;
  let optionForm = document.querySelector("form");
  optionForm.addEventListener("submit", introduceOption);
};

const introduceOption = (e) => {
  e.preventDefault();
  let userID = getUserSessionData().id;
  let datetime = document.getElementById("datetime-local").value;
  let furniture = document.getElementById(""); // a completer

  let option ={
    "userID":userID,
    "optionTerm":datetime,
    "furniture":furniture
  }

  fetch(API_URL + "options/introduceOption", {
    method: "POST",
    body: JSON.stringify(option),
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return response.json().then(() => onOptionIntroduced());
    })
};

const onOptionIntroduced = () => {
  alert("An option has been introduced");
  RedirectUrl("/");
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

 export default OptionPage;
