import { getUserSessionData } from "../utils/session";
import Sidebar from "./SideBar";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { type } from "jquery";

let page = document.querySelector("#page");

let modifPage = `
<div class="container-fluid bg-danger relative">Hello World<div>
<form class="form-inline">
    <div class="row">
        <div class="col-sm-6 bg-info">
            <label for="title">Titre: </label>
            <input type="text" class="form-control" id="title" placeholder="Enter title" name="title" required>
            <label for="state">Etat: </label>
            <div class="form-group">
                <select class="form-control" id="state" name="state">
                    <option>En vente</option>
                    <option>En restauration</option>
                    <option>Vendu</option>
                    <option>En livraison</option>
                </select>
            </div>
            <label for="depositDate">Date de dépot: </label>
            <input type="text" class="form-control" id="depositDate" placeholder="Enter date of deposit" name="depositDate" required>
            <label for="seller">Vendeur: </label>
            <input type="text" class="form-control" id="seller" placeholder="Enter seller" name="seller" required>
            <label for="type">Type: </label>
            <input type="text" class="form-control" id="type" placeholder="Enter type" name="type" required>
        </div>
        <div class="col-sm-6 bg-warning">
            <label for="furnitureDateCollection">Date emporter: </label>
            <input type="text" class="form-control" id="furnitureDateCollection" placeholder="Enter furnitureDateCollection" name="furnitureDateCollection" required>
            <label for="dateOfSale">Date de vente: </label>
            <input type="text" class="form-control" id="dateOfSale" placeholder="Enter date Of Sale" name="dateOfSale" required>
            <label for="saleWithdrawalDate">Date de retrait: </label>
            <input type="text" class="form-control" id="saleWithdrawalDate" placeholder="Enter sale With drawal Date" name="saleWithdrawalDate" required>
            <label for="purchasePrice">Prix d'achat: </label>
            <input type="text" class="form-control" id="purchasePrice" placeholder="Enter purchase Price" name="purchasePrice" required>
            <label for="sellingPrice">Prix de vente: </label>
            <input type="text" class="form-control" id="sellingPrice" placeholder="Enter selling Price" name="sellingPrice" required>
            <label for="specialSalePrice">Prix antiquaire: </label>
            <input type="text" class="form-control" id="specialSalePrice" placeholder="Enter special Sale Price" name="specialSalePrice" required>
            <label for="buyer">Acheteur: </label>
            <input type="text" class="form-control" id="buyer" placeholder="Enter buyer" name="buyer" required>
            <label for="delivery">Livraison: </label>
            <input type="text" class="form-control" id="delivery" placeholder="Enter delivery" name="delivery" required>
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

    if(!title || !state){
        let messageBoard = document.querySelector("#messageBoardForm");
        messageBoard.innerHTML = '<div class="alert alert-danger">Something is missing.</div>';
        return;
    }
  
    let furniture = {
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