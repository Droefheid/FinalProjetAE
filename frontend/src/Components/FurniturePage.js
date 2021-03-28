import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";


let table = `
  
          <nav id="nav_furniture">
            <ul class="list-group">

            <li id="" class="list-group-item" data-toggle="collapse" href="#collapse" role="button" aria-expanded="false" aria-controls="collapse">
                <div class="row">
                  <div class="col-sm-4">
                    <img src="assets/Images/Bureau_1.png" class="rounded" style="width:100%;"/>
                  </div>
                  <div class="col-sm-">
                    <p>
                      <h5>Table stylisée en bois</h5>
                      Type :
                    </p>
                  </div>
                </div>
              </li>

              <li class="list-group-item" data-toggle="collapse" href="#collapse" role="button" aria-expanded="false" aria-controls="collapse">
                
                <div class="row">
                  <div class="col-sm-4">
                    <img src="assets/Images/Bureau_1.png" class="rounded" style="width:100%;"/>
                  </div>
                  <div class="col-sm-">
                    <p>
                      <h5>Table stylisée en bois</h5>
                      Type :
                    </p>
                  </div>
                </div>
              </li>

              <li class="list-group-item" data-toggle="collapse" href="#collapse" role="button" aria-expanded="false" aria-controls="collapse">
                
                <div class="row">
                  <div class="col-sm-4">
                    <img src="assets/Images/Bureau_1.png" class="rounded" style="width:100%;"/>
                  </div>
                  <div class="col-sm-">
                    <p>
                      <h5>Table stylisée en bois</h5>
                      Type :
                    </p>
                  </div>
                </div>
              </li>          
            </ul>
          </nav>
          
          <div class="row" id="description_furniture">
            <h4>Meuble en bois stylisée</h4>
            <img src="assets/Images/Bureau_1.png" style="width:20%;" />
          </div>`;



const FurniturePage = () => {
  Sidebar(true);

  let page = document.querySelector("#content");
  page.innerHTML = table;

  fetch("/api/furnitures", {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok)
        throw new Error(
          "Error code : " + response.status + " : " + response.statusText
        );
      return response.json();
    })
    .then((data) => onFurnitureList(data))
    .catch((err) => onError(err));
};

const onFurnitureList = (data) => {
  let page = document.querySelector("#content");
  if (!data) return;

  let table = `` ;

  data.foreach((element) => {

    table+= ``;

  });

  table += ``;
  page.innerHTML = table;
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  let errorMessage = "";
  messageBoard.classList.add("d-block");
};

export default FurniturePage;