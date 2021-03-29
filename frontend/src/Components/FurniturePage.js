import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";


const FurniturePage = () => {
  Sidebar(true);

  let page = document.querySelector("#content");
  
  let table = `

    <div class="input-group rounded" id="search_furniture_list">
      <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
        aria-describedby="search-addon"  />
      <span class="input-group-text border-0" id="search-addon">
        <i class="fas fa-search"></i>
      </span>
    </div>

    <nav id="nav_furniture">
      <ul class="list-group">
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
      </ul>
    </nav>

    <div id="description_furniture">
      <h4>Meuble en bois massif</h4>
      <img src="assets/Images/Bureau_1.png" style="width:25%;"/>
      <p>Texte Lambda</p>
    </div>`;

  page.innerHTML = table;
}

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  let errorMessage = "";
  messageBoard.classList.add("d-block");
};

export default FurniturePage;