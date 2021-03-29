import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";


const FurniturePage = () => {
  Sidebar(true);
  

  fetch(API_URL + "furnitures/allFurniture", {
    method: "GET",
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
    .then((data) => onFurnitureList(data))
    .catch((err) => onError(err));
};

const onFurnitureList = (data) => {
  
  let page = document.querySelector("#content");
  if (!data) return;

  let table = `
    <nav id="nav_furniture">
      <ul class="list-group">` ;

  data.foreach((element) => {

    table+= `
        <li id="" class="list-group-item" data-toggle="collapse" href="#collapse" role="button" aria-expanded="false" aria-controls="collapse">
                <div class="row">
                  <div class="col-sm-4">
                    <img src="assets/Images/Bureau_1.png" class="rounded" style="width:100%;"/>
                  </div>
                  <div class="col-sm-">
                    <p>
                      <h5>Title</h5>
                      Type : 
                    </p>
                  </div>
                </div>
        </li>
    `;
  });

  table += ` 
      </ul>
    </nav>`;

  page.innerHTML = table;
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  let errorMessage = "";
  messageBoard.classList.add("d-block");
};

export default FurniturePage;