import { getUserSessionData } from "../utils/session";
import Sidebar from "./SideBar";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";

let page = document.querySelector("#page");

let modifPage = `
<div class="container-fluid bg-danger relative">Hello World<div>
<form class="form-inline">
    <div class="row">
        <div class="col-sm-6 bg-info">
            <label for="title">Titre</label>
            <input type="text" class="form-control" id="title" placeholder="Enter title" name="title" required>
            <label for="state">Etat</label>
            <input type="text" class="form-control" id="state" placeholder="Enter state" name="state" required>
            <label for="sel1">Etat: </label>
            <div class="form-group">
                <select class="form-control" id="sel1">
                    <option>En vente</option>
                    <option>En restauration</option>
                    <option>Vendu</option>
                    <option>En livraison</option>
                </select>
            </div>
            <label for="dropDate">Date de dépot</label>
            <input type="text" class="form-control" id="dropDate" placeholder="Enter date of drop" name="dropDate" required>
            <label for="seller">Vendeur</label>
            <input type="text" class="form-control" id="seller" placeholder="Enter seller" name="seller" required>
            <label for="type">Type</label>
            <input type="text" class="form-control" id="type" placeholder="Enter type" name="type" required>
        </div>
        <div class="col-sm-6 bg-warning">
            <label for="takeAway">Date emporter</label>
            <input type="text" class="form-control" id="takeAway" placeholder="Enter date of take away" name="takeAway" required>
        </div>
    </div>
</form><div>`;

const ModificationFurniturePage = () => {    
    Sidebar(true);

    const user = getUserSessionData();
    if (!user || !user.isBoss) {
        // re-render the navbar for the authenticated user.
        Navbar();
        RedirectUrl("/");
    } else loginForm.addEventListener("submit", onSubmit);
 
    return page.innerHTML = modifPage;
};

const onSubmit = (e) => {
    e.preventDefault();
    let title = document.getElementById("title").value;
    let state = document.getElementById("state").value;

    if(!title || !state){
        let messageBoard = document.querySelector("#messageBoardForm");
        messageBoard.innerHTML = '<div class="alert alert-danger">Something is missing.</div>';
        return;
    }
  
    let furniture = {
      "title": username,
      "state": password,
    };
  
    fetch(API_URL + "furnitures/update", {
      method: "POST", 
      body: JSON.stringify(furniture), 
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok)
          throw new Error(
            "Error code : " + response.status + " : " + response.statusText
          );
        return response.json();
      })
      .then((data) => onFurnitureUpdate(data))
      .catch((err) => onError(err));
};
  
const onFurnitureUpdate = (furnitureData) => {
    // re-render the navbar for the authenticated user
    Navbar();
    RedirectUrl("/");
};
  
const onError = (err) => {
    let messageBoard = document.querySelector("#messageBoardForm");
    let errorMessage = "";
    if (err.message.includes("401")) {
      messageBoard.innerHTML = '<div class="alert alert-danger">Wrong username or password.</div>';
    }else{
      errorMessage = err.message;
      ALERT_BOX(messageBoard, errorMessage);
    }
};
 
 export default ModificationFurniturePage;