import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let page = document.querySelector("#page");

let addFurniturePage = `
<div class="input-group mb-3 ">
  <div class="input-group-prepend">
    <span class="input-group-text" id="basic-addon1">Title</span>
  </div>
  <input type="text" class="form-control" aria-describedby="basic-addon1">
</div>
<div class="input-group mb-3">
  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">State</label>
  </div>
  <select class="custom-select" id="inputGroupSelect01">
    <option selected>En restauration</option>
    <option value="1">En magasin</option>
    <option value="2">En vente</option>
    <option value="3">Sous option</option>
  </select>
</div>
<input class="form-control mb-3 " type="datetime-local" value="2011-08-19T13:45:00" id="datetime-local">
<div class="input-group mb-3">
  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Seller</label>
  </div>
  <select class="custom-select" id="inputGroupSelect01">
    <option selected>Choose...</option>
    <option value="1">One</option>
    <option value="2">Two</option>
    <option value="3">Three</option>
  </select>
</div>
<div class="input-group mb-3">
  <div class="input-group-prepend">
    <label class="input-group-text" for="inputGroupSelect01">Type</label>
  </div>
  <select class="custom-select" id="inputGroupSelect01">
    <option selected>Choose...</option>
    <option value="1">One</option>
    <option value="2">Two</option>
    <option value="3">Three</option>
  </select>
</div>
`;

const AddFurniturePage = () => {   
   Sidebar(true);
   return page.innerHTML = addFurniturePage;
};


export default AddFurniturePage;