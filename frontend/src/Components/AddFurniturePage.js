import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";
import { getUserSessionData, getTokenSessionDate } from "../utils/session";
import Navbar from "./Navbar.js";

let page = document.querySelector("#page");

const AddFurniturePage = () => {   
   const user = getUserSessionData();
   if (!user || !user.isBoss) {
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
  Sidebar(true, false);


   let addFurniturePage = `
   <form id="addFurniture">

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Title</span>
        </div>
        <input type="text" id="titleFurniture" class="form-control" aria-describedby="basic-addon1" required>
      </div>

      <div class="input-group mb-3 ">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">Purchase price</span>
        </div>
        <input type="number" id="purchaseFurniture" class="form-control" aria-describedby="basic-addon1">
      </div>     

      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <label class="input-group-text" for="inputGroupSelect01">State</label>
        </div>
        <select class="custom-select" id="stateId">
          <option value="ER" selected>En restauration</option>
          <option value="M">En magasin</option>
        </select>
      </div>

      <input class="form-control mb-3 " type="datetime-local"
      value="2021-01-01T13:00:00" id="datetime-local">
      <p class="text-muted">*Pick-up date</p>


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

      <input type="file" id="files" name="files" multiple>
      <p class="text-muted">*Please select all photos at once.</p>

      <button type="button" id="addForm" class="btn btn-primary" style="width:100%;">Add</button>
      <div id="messageBoardForm" style="margin-top:30px;"></div>
    </form>
`;
   page.innerHTML = addFurniturePage;

   const addFurnitures = document.getElementById("addForm")
   addFurnitures.addEventListener("click", onAddFurniture);
};

const onAddFurniture = () => {

  
  let id = getTokenSessionDate();

  let title = document.getElementById("titleFurniture").value;
  let purchasePrice = document.getElementById("purchaseFurniture").value;
  let state = document.getElementById("stateId").value;
  let type = document.getElementById("typeId").value;
  let seller = document.getElementById("sellerId").value;
  let pickUpDate = document.getElementById("datetime-local").value;

  //Check if title is correct
  if(!title) {
    let messageBoard = document.getElementById("messageBoardForm");
    messageBoard.innerHTML = '<div class="alert alert-danger">Title is missing.</div>';
    return;
  }
  //Check if the purchase price is <= 0 
  if(purchasePrice <= 0) {
    let messageBoard = document.getElementById("messageBoardForm");
    messageBoard.innerHTML = '<div class="alert alert-danger">Purchase price is negative or equals zero.</div>';
    return;
  }
  //Check id the date is well before now. TODO
  var now = new Date();
  if(pickUpDate < now){
    let messageBoard = document.getElementById("messageBoardForm");
    messageBoard.innerHTML = '<div class="alert alert-danger">Pick-up date is in the future</div>';
    return;
  }

  let furniture = {
    "title": title,
    "purchasePrice": purchasePrice,
    "state": state,
    "type": type,
    "seller": seller,
    "pickUpDate": pickUpDate,
  }

  fetch(API_URL + "furnitures/", {
    method: "POST",
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
        return response.json().then((data) => onFurnitureAdded(data));
    })
}

const onFurnitureAdded = (data) => {
  
  let furnitureId = data.furniture.furnitureId;
  console.log(data,furnitureId);
  
  const input = document.getElementById('files');
  const formData = new FormData();
  for(let i = 0; i < input.files.length; i++){
      //console.log(input.files[i]);
      formData.append("photo"+i, input.files[i]);
  }
  //console.log("Formdata: ", formData, "get('photo0'): ", formData.get("photo"));

  let id = getTokenSessionDate();

  fetch(API_URL + "photos/uploadPhotosFurniture", {
    method: "POST",
    body: formData,
    headers:{
      "Authorization": id,
      "furnitureId": furnitureId,
    }
  })
  .then((response) => {
      if (!response.ok) {
        return response.text().then((err) => onError(err));
      }
      else
        return response.json().then((data) => onFurnitureAndPhotoAdded(data));
    })
};

const onFurnitureAndPhotoAdded = (data) => {

  alert("Furniture has been added");
  RedirectUrl(`/furniture`);
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  messageBoard.innerHTML = err;
};

export default AddFurniturePage;