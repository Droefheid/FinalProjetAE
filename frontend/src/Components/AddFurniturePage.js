import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import Navbar from "./Navbar.js";

const STATES = [ "ER", "M", "EV", "O", "V", "EL", "L", "AE", "E", "R", "RE" ];

let page = document.querySelector("#page");



const AddFurniturePage = () => {   
   Sidebar(true);

   const user = getUserSessionData();
   if (!user || !user.isBoss) {
       // re-render the navbar for the authenticated user.
       Navbar();
       RedirectUrl("/");
   } else {
       // Fetch pour recup
       fetch(API_URL + "furnitures/infosAdd/", {
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
               return response.json().then((data) => onCreateAddPage(data));
           });
   }
}
const onCreateAddPage = (data) => { 


   let addFurniturePage = `
   <form id="addFurniture">

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Title</span>
        </div>
        <input type="text" id="titleFurniture" class="form-control" aria-describedby="basic-addon1">
      </div>

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Purchase price</span>
        </div>
        <input type="number" id="purchaseFurniture" class="form-control" aria-describedby="basic-addon1">
      </div>     

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Selling price</span>
        </div>
        <input type="number" id="sellingFurniture" class="form-control" aria-describedby="basic-addon1">
      </div>

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Special sale price</span>
        </div>
        <input type="number" id="specialFurniture" class="form-control" aria-describedby="basic-addon1">
      </div>

      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <label class="input-group-text" for="inputGroupSelect01">State</label>
        </div>
        <select class="custom-select" id="stateId">
          <option value="ER" selected>En restauration</option>
          <option value="EM">En magasin</option>
          <option value="EV">En vente</option>
          <option value="SO">Sous option</option>
        </select>
      </div>

      <input class="form-control mb-3 " type="datetime-local"
      value="2021-01-01T13:00:00" id="datetime-local">

      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <label class="input-group-text" for="inputGroupSelect01">Type</label>
        </div>
          <select class="custom-select" id="typeId">`;
              data.types.forEach(type => {
                addFurniturePage += `
                <option value="${type.typeId}">${type.name}</option>`;
              });
              addFurniturePage += `
          </select>
      </div>

      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <label class="input-group-text" for="sellerId">Seller</label>
        </div>
          <select class="custom-select" id="sellerId">`;
              data.users.forEach(user => {
                addFurniturePage += `
                <option value="${user.id}">${user.username}</option>`;
              });
              addFurniturePage += `
          </select>
      </div>
      <button type="button" id="addForm" class="btn btn-primary" style="width:100%;">Add</button>
    </form>
`;

   page.innerHTML = addFurniturePage;

   const addFurnitures = document.getElementById("addForm")
   addFurnitures.addEventListener("click", onAddFurniture);
};

const onAddFurniture = () => {


  let title = document.getElementById("titleFurniture").value;
  let purchasePrice = document.getElementById("purchaseFurniture").value;
  let sellingPrice = document.getElementById("sellingFurniture").value;
  let specialSalePrice = document.getElementById("specialFurniture").value;
  let state = document.getElementById("stateId").value;
  let type = document.getElementById("typeId").value;
  let seller = document.getElementById("sellerId").value;
  let pickUpDate = document.getElementById("datetime-local").value;

  console.log(title, purchasePrice, sellingPrice,  specialSalePrice, state, type, seller , pickUpDate);

  let furniture = {
    "title": document.getElementById("titleFurniture").value,
    "purchasePrice": document.getElementById("purchaseFurniture").value,
    "sellingPrice": document.getElementById("sellingFurniture").value,
    "specialSalePrice": document.getElementById("specialFurniture").value,
    "state": document.getElementById("stateId").value,
    "type": document.getElementById("typeId").value,
    "seller": document.getElementById("sellerId").value,
    "pickUpDate": document.getElementById("datetime-local").value,
  }

  fetch(API_URL + "furnitures/", {
    method: "POST",
    body: JSON.stringify(furniture),
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return response.json().then(() => onFurnitureAdded());
    })
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  messageBoard.innerHTML = err;
};


export default AddFurniturePage;