import { getUserSessionData } from "../utils/session";
import Sidebar from "./SideBar";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { API_URL, ALERT_BOX } from "../utils/server.js";

const STATES = [ "ER", "M", "EV", "O", "V", "EL", "L", "E", "R", "RE" ];

let page = document.querySelector("#page");

let modifPage = `
<div class="container-fluid bg-danger relative">Hello World<div>
<form id="update" class="form-inline">
    <input id="furnitureId" value="1" hidden>
    <div class="row">
        <div class="col-sm-6 bg-info">
            <label for="title">Titre: </label>
            <input type="text" class="form-control" id="title" placeholder="Enter title" name="title" required>
            <label for="state">Etat: </label>
            <div class="form-group">
                <select class="form-control" id="state" name="state">
                    <option value="ER">En restauration</option>
                    <option value="M">En magasin</option>
                    <option value="EV">En vente</option>
                    <option value="O">Sous option</option>
                    <option value="V">Vendu</option>
                    <option value="EL">En livraison</option>
                    <option value="L">Livré</option>
                    <option value="E">Emporté</option>
                    <option value="R">Reservé</option>
                    <option value="RE">Retiré</option>
                </select>
            </div>
            <label for="depositDate">Date de dépot: </label>
            <input type="text" class="form-control" id="depositDate" placeholder="Enter date of deposit" name="depositDate">
            <label for="seller">Vendeur: </label>
            <div class="form-group">
                <select class="form-control" id="seller" name="seller">
                    <option value="1">Livi Satcho</option>
                    <option value="2">George</option>
                    <option value="3">Michael</option>
                </select>
            </div>
            <label for="type">Type: </label>
            <div class="form-group">
                <select class="form-control" id="type" name="type">
                    <option value="1">Armoire</option>
                    <option value="2">Bahut</option>
                    <option value="3">Bibliotheque</option>
                </select>
            </div>
        </div>
        <div class="col-sm-6 bg-warning">
            <label for="furnitureDateCollection">Date emporter: </label>
            <input type="text" class="form-control" id="furnitureDateCollection" placeholder="Enter furnitureDateCollection" name="furnitureDateCollection" required>
            <label for="dateOfSale">Date de vente: </label>
            <input type="text" class="form-control" id="dateOfSale" placeholder="Enter date Of Sale" name="dateOfSale">
            <label for="saleWithdrawalDate">Date de retrait: </label>
            <input type="text" class="form-control" id="saleWithdrawalDate" placeholder="Enter sale With drawal Date" name="saleWithdrawalDate">
            <label for="purchasePrice">Prix d'achat: </label>
            <input type="text" class="form-control" id="purchasePrice" placeholder="Enter purchase Price" name="purchasePrice" required>
            <label for="sellingPrice">Prix de vente: </label>
            <input type="text" class="form-control" id="sellingPrice" placeholder="Enter selling Price" name="sellingPrice">
            <label for="specialSalePrice">Prix antiquaire: </label>
            <input type="text" class="form-control" id="specialSalePrice" placeholder="Enter special Sale Price" name="specialSalePrice">
            <label for="buyer">Acheteur: </label>
            <input type="text" class="form-control" id="buyer" placeholder="Enter buyer" name="buyer">
            <label for="delivery">Livraison: </label>
            <input type="text" class="form-control" id="delivery" placeholder="Enter delivery" name="delivery">
            <input type="submit" value="update" class="btn btn-lg btn-primary btn-block">
        </div>
    </div>
</form><div>`;

const ModificationFurniturePage = () => {    
    Sidebar(true);

    const user = getUserSessionData();
    /*if (!user || !user.isBoss) {
        // re-render the navbar for the authenticated user.
        Navbar();
        RedirectUrl("/");
    } else {*/
        page.innerHTML = modifPage;
        let form = document.querySelector("#update");
        form.addEventListener("submit", onSubmit);
    //}
};

const onSubmit = (e) => {
    e.preventDefault();

    let furnitureId = document.getElementById("furnitureId").value;
    let title = document.getElementById("title").value;
    let type = document.getElementById("type").value;
    let buyer = document.getElementById("buyer").value;
    let purchasePrice = document.getElementById("purchasePrice").value;
    let furnitureDateCollection = document.getElementById("furnitureDateCollection").value;
    let sellingPrice = document.getElementById("sellingPrice").value;
    let specialSalePrice = document.getElementById("specialSalePrice").value;
    let delivery = document.getElementById("delivery").value;
    let state = document.getElementById("state").value;
    let depositDate = document.getElementById("depositDate").value;
    let dateOfSale = document.getElementById("dateOfSale").value;
    let saleWithdrawalDate = document.getElementById("saleWithdrawalDate").value;
    let seller = document.getElementById("seller").value;

    // Required field.
    if(!furnitureId || !title || !state || !purchasePrice || !seller 
        || !furnitureDateCollection || !type) {
        let messageBoard = document.querySelector("#messageBoardForm");
        messageBoard.innerHTML = '<div class="alert alert-danger">Something required is missing.</div>';
        return;
    }

    // Check if state is correct.
    if(!STATES.find(e => e == state)) {
        let err = { message: "The state is incorrect." };
        return onError(err);
    }

    // Check about depositDate.
    if(depositDate && state == "ER") {
        let err = { message: "You cant have a deposit date if the state is en restauration." };
        return onError(err);
    }
    if(state != "ER" && !depositDate) {
        let err = { message: "You need a deposit date if the furniture is not anymore en restauration." };
        return onError(err);
    }

    // Check about put for sale.
    if(sellingPrice && sellingPrice != "0" && (state == "ER" || state == "M")) {
        let err = { message: "You cant have a selling price if the state is en restauration or en magasin." };
        return onError(err);
    }
    if((!sellingPrice || sellingPrice == "0") && state != "ER" && state != "M") {
        let err = { message: "You need a selling price if the furniture is not anymore en restauration or en magasin." };
        return onError(err);
    }

    // Check for buyer.
    if(!buyer && (state == "V" || state == "EL" || state == "L" || state == "E" || state == "R")) {
        let err = { message: "You need a buyer if the state is vendu or en livraison or livre or emporte or reserve." };
        return onError(err);
    }
    if(buyer && state != "V" && state != "EL" && state != "L" && state != "E" && state != "R") {
        let err = { message: ("You cant have a buyer if the state is not vendu or not en livraison or not livre or" 
        + " not emporte or not reserve.") };
        return onError(err);
    }
    if(buyer && !dateOfSale) {
        let err = { message: "You need a date of sale if a buyer is specify." };
        return onError(err);
    }

    // Check about special sale.
    if(specialSalePrice && specialSalePrice != "0" && (state == "ER" || state == "M" || state == "EV" || state == "O" || state == "RE")) {
        let err = { message: ("You cant have a special sale price if the state is en restauration or en magasin or" 
        + " en vente or sous option or retire.") };
        return onError(err);
    }
    if((!specialSalePrice || specialSalePrice == "0") && state != "ER" && state != "M" && state != "EV" && state != "O" && state != "RE") {
        let err = { message: ("You need a special sale price if the furniture is not anymore en restauration or en magasin or" 
        + " en vente or sous option or retire.") };
        return onError(err);
    }
    if(specialSalePrice && !buyer) {
        let err = { message: "You need a buyer if the special price is specify." };
        return onError(err);
    }

    // Check for saleWithdrawalDate.
    if(saleWithdrawalDate && !buyer) {
        let err = { message: "You need a buyer if the sale with drawal date is specify." };
        return onError(err);
    }
    if(saleWithdrawalDate && delivery) {
        let err = { message: "You cant have a sale with drawal date and a delivery." };
        return onError(err);
    }

    // Check for delivery
    if(delivery && !buyer) {
        let err = { message: "You need a buyer if the delivery is specify." };
        return onError(err);
    }
  
    let furniture = {
      "furnitureId": furnitureId,
      "title": title,
      "type": type,
      "buyer": buyer,
      "purchasePrice": purchasePrice,
      "furnitureDateCollection": furnitureDateCollection,
      "sellingPrice": sellingPrice,
      "specialSalePrice": specialSalePrice,
      "delivery": delivery,
      "state": state,
      "depositDate": depositDate,
      "dateOfSale": dateOfSale,
      "saleWithdrawalDate": saleWithdrawalDate,
      "seller": seller,
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
    let messageBoard = document.querySelector("#messageBoard");
    let errorMessage = "";
    if (err.message.includes("401")) {
      messageBoard.innerHTML = '<div class="alert alert-danger">Wrong username or password.</div>';
    }else{
      errorMessage = err.message;
      ALERT_BOX(messageBoard, errorMessage);
    }
};
 
 export default ModificationFurniturePage;