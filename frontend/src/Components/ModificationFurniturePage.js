import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import Sidebar from "./SideBar";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { API_URL, ALERT_BOX } from "../utils/server.js";
import { user_me } from "..";

const STATES = [ "ER", "M", "EV", "O", "V", "EL", "L", "AE", "E", "R", "RE" ];

let page = document.querySelector("#page");

let modifPage = `
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
                    <option value="AE">a emporté</option>
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
            <input type="text" class="form-control" id="furnitureDateCollection" placeholder="Enter furnitureDateCollection" name="furnitureDateCollection">
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
            <div class="form-group">
                <select class="form-control" id="buyer" name="buyer">
                    <option value="" selected>Nobody</option>
                    <option value="1">Livi Satcho</option>
                    <option value="2">George</option>
                    <option value="3">Michael</option>
                </select>
            </div>
            <label for="delivery">Livraison: </label>
            <input type="text" class="form-control" id="delivery" placeholder="Enter delivery" name="delivery">
            <label for="pickUpDate">Pick-up date: </label>
            <input type="text" class="form-control" id="pickUpDate" placeholder="Enter pickUpDate" name="pickUpDate" required>
            <input type="submit" value="update" class="btn btn-lg btn-primary btn-block">
        </div>
    </div>
</form><div>`;

const ModificationFurniturePage = () => {    
    Sidebar(true);
    console.log(user_me.furnitureId);

    const user = getUserSessionData();
    if (!user || !user.isBoss) {
        // re-render the navbar for the authenticated user.
        Navbar();
        RedirectUrl("/");
    } else {
        // Fetch pour recup
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
                return response.json().then((data) => onPageCreate(data));
            });
    }
};

const onPageCreate = (data) => {
    let furniture = data.furniture;
    let Timestamp = new Date(furniture.pickUpDate);
    let timeSplit = Timestamp.toLocaleString().split("/");
    furniture.pickUpDate = timeSplit[2].substr(0, 4)+"-"+timeSplit[1]+"-"+timeSplit[0]+" "+Timestamp.toLocaleTimeString();
    
    let modifPage = `
    <form id="update" class="form-inline">
        <input id="furnitureId" value="${furniture.furnitureId}" hidden>
        <div class="row">
            <div class="col-sm-6 bg-info">
                <label for="title">Titre: </label>
                <input type="text" class="form-control" id="title" value="${furniture.furnitureTitle}" placeholder="Enter title" name="title" required>
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
                        <option value="AE">a emporté</option>
                        <option value="E">Emporté</option>
                        <option value="R">Reservé</option>
                        <option value="RE">Retiré</option>
                    </select>
                </div>
                <label for="depositDate">Date de dépot: </label>
                <input type="text" class="form-control" id="depositDate" `;
                if(furniture.depositDate) modifPage += ` value="${furniture.depositDate}"`;
                modifPage += `" placeholder="Enter date of deposit" name="depositDate">
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
                <input type="text" class="form-control" id="furnitureDateCollection"`;
                if(furniture.furnitureDateCollection) modifPage += ` value="${furniture.furnitureDateCollection}"`;
                modifPage += ` placeholder="Enter furnitureDateCollection" name="furnitureDateCollection">
                <label for="dateOfSale">Date de vente: </label>
                <input type="text" class="form-control" id="dateOfSale"`;
                if(furniture.dateOfSale) modifPage += ` value="${furniture.dateOfSale}"`;
                modifPage += ` placeholder="Enter date Of Sale" name="dateOfSale">
                <label for="saleWithdrawalDate">Date de retrait: </label>
                <input type="text" class="form-control" id="saleWithdrawalDate"`;
                if(furniture.saleWithdrawalDate) modifPage += ` value="${furniture.saleWithdrawalDate}"`;
                modifPage += ` placeholder="Enter sale With drawal Date" name="saleWithdrawalDate">
                <label for="purchasePrice">Prix d'achat: </label>
                <input type="text" class="form-control" id="purchasePrice" value="${furniture.purchasePrice}" placeholder="Enter purchase Price" name="purchasePrice" required>
                <label for="sellingPrice">Prix de vente: </label>
                <input type="text" class="form-control" id="sellingPrice" value="${furniture.sellingPrice}" placeholder="Enter selling Price" name="sellingPrice">
                <label for="specialSalePrice">Prix antiquaire: </label>
                <input type="text" class="form-control" id="specialSalePrice" value="${furniture.specialSalePrice}" placeholder="Enter special Sale Price" name="specialSalePrice">
                <label for="buyer">Acheteur: </label>
                <div class="form-group">
                    <select class="form-control" id="buyer" name="buyer">
                        <option value="" selected>Nobody</option>
                        <option value="1">Livi Satcho</option>
                        <option value="2">George</option>
                        <option value="3">Michael</option>
                    </select>
                </div>
                <label for="delivery">Livraison: </label>
                <input type="text" class="form-control" id="delivery"`;
                if(furniture.delivery) modifPage += ` value="${furniture.delivery}"`;
                modifPage += ` placeholder="Enter delivery" name="delivery">
                <label for="pickUpDate">Pick-up date: </label>
                <input type="text" class="form-control" id="pickUpDate"`;
                if(furniture.pickUpDate) modifPage += ` value="${furniture.pickUpDate}"`;
                modifPage += ` placeholder="Enter pickUpDate" name="pickUpDate" required>
                <input type="submit" value="update" class="btn btn-lg btn-primary btn-block">
            </div>
        </div>
    </form><div>`;

    page.innerHTML = modifPage;
    let form = document.querySelector("#update");
    form.addEventListener("submit", onSubmit);
}

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
    let pickUpDate = document.getElementById("pickUpDate").value;

    // Required field.
    if(!furnitureId || !title || !state || !purchasePrice || !seller 
        || !pickUpDate || !type) {
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

    // Check about put up for sale.
    if(sellingPrice && sellingPrice != "0" && (state == "ER" || state == "M")) {
        let err = { message: "You cant have a selling price if the state is en restauration or en magasin." };
        return onError(err);
    }
    if((!sellingPrice || sellingPrice == "0") && state != "ER" && state != "M") {
        let err = { message: "You need a selling price if the furniture is not anymore en restauration or en magasin." };
        return onError(err);
    }
    if(sellingPrice < 0) {
        let err = { message: "You cant have a negative selling price." };
        return onError(err);
    }

    // Check for buyer.
    if(!buyer && (state == "V" || state == "EL" || state == "L" || state == "AE" || state == "E" 
    || state == "R")) {
        let err = { message: "You need a buyer if the state is sold, on delivery, "
        + "delivered, to go, taken away or reserved." };
        return onError(err);
    }
    if(buyer && state != "V" && state != "EL" && state != "L" && state != "AE" 
    && state != "E" && state != "R") {
        let err = { message: ("You cant have a buyer if the state is not vendu or "
        + "not en livraison or not livre or not emporte or not reserve.") };
        return onError(err);
    }
    if(buyer && !dateOfSale) {
        let err = { message: "You need a date of sale if a buyer is specify." };
        return onError(err);
    }

    // Check for delivery
    if(delivery && !buyer) {
        let err = { message: "You need a buyer if the delivery is specify." };
        return onError(err);
    }
    if(!delivery && (state == "EL" || state == "L")) {
        let err = { message: "Delivery is needed if the state is on delivery or delivered." };
        return onError(err);
    }

    // Check for take away.
    if((state == "AE" || state == "E") && !furnitureDateCollection) {
        let err = { message: "Furniture date collection is needed if the state is"
        + " to go or take away." };
        return onError(err);
    }

    // Check about special sale.
    if(specialSalePrice && specialSalePrice != "0" && (state == "ER" || state == "M" 
    || state == "EV" || state == "O" || state == "RE")) {
        let err = { message: ("You cant have a special sale price if the state is "+
        "on restoration, in shop, on sale, under option or withdraw.") };
        return onError(err);
    }
    if(specialSalePrice && specialSalePrice != "0" && !buyer) {
        let err = { message: "You need a buyer if the special price is specify." };
        return onError(err);
    }
    if(specialSalePrice < 0) {
        let err = { message: "You cant have a negative special sale price." };
        return onError(err);
    }

    // Check for withdraw.
    if(!saleWithdrawalDate && state == "RE") {
        let err = { message: "Sale Withdrawal Date is needed if the state is withdraw." };
        return onError(err);
    }
    if(saleWithdrawalDate && state != "RE") {
        let err = { message: "The state need to be withdraw if a sale withdrawal date is specify." };
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
      "pickUpDate": pickUpDate,
    };
  
    let id = getTokenSessionDate();
    fetch(API_URL + "furnitures", {
      method: "PUT", 
      body: JSON.stringify(furniture), 
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
          return response.json().then((data) => onFurnitureUpdate(data));
      });
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