import { getUserSessionData } from "../utils/session.js";
import { RedirectUrl } from "./Router.js";

let sideBar = document.querySelector("#sideBar");
let movingRow = document.querySelector("#movingRow");

const Sidebar = (needed, secondNeeded) => {
  if(!needed) {
      // Remove padding.
      movingRow.className = "row justify-content-center mt-5";
      return (sideBar.innerHTML = "");
  }

  // Add padding.
  //movingRow.className.replace("noPaddingForSideBar", "smoothTransition");
  movingRow.className = "row justify-content-center mt-5 smoothTransition";

  let sidebar = "";

  // Add new navbar on the left if is boss
  let user = getUserSessionData();
  if(user && user.isBoss){
    sidebar += `
    <div class="onLeft">
      <div class="navbar navbar-nav ml-auto mr-auto pt-3">
        <button class="btn btn-info mb-1 samebutton" id="confirmUser">List of inscriptions</button>
        <button class="btn btn-info mb-1 samebutton" id="userList">List of clients</button>
        <button class="btn btn-info mb-1 samebutton" id="addFurniture">Add furniture</button>
        <button class="btn btn-info mb-1 samebutton" id="confirmVisits">Confirm visits</button>
        <button class="btn btn-info mb-1 samebutton" id="visitListPage">List of visits</button>
      </div>
    </div>`;
  }

  if(secondNeeded){
    // SideBar Content.
    sidebar += `<!-- SideBar -->
    <div id="mySidenav" class="`;
    //if(user && user.isBoss) sidebar += `sidenav`;
    //else sidebar += `sidenavBoss`;
    sidebar += `sidenav`;
    sidebar += `">
      <form class="mb-5 pb-4">
          <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Tout
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Armoire
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Bahut
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Bibliothèque
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> ...
            </label>
          </div>
        <button type="submit" class="btn btn-primary">Rechercher</button>
      </form>
    </div>`;
    // TODO Remplacer les checkbox d'exemple par une boucle sur une reponse FETCH.

    // Button for SideBar.
    sidebar += `<!-- Button for SideBar -->
    <div id="mySidenavButton" class="transitionButton">
      <a class="closebtn" onclick='
        let i = document.getElementById("mySidenav").style.width;
        if(!i) i = "0px";
        if(i != "0px"){
            //Close
            document.getElementById("mySidenav").style.width = "0px";
            document.getElementById("mySidenavButton").style.marginLeft = "0px";
            i--;
        }else{
            // Open
            document.getElementById("mySidenav").style.width = "150px";
            document.getElementById("mySidenavButton").style.marginLeft = "150px";
            i++;
        }
    '>&#9776;</a>
    </div><div class="pb-4"></div>`;
  }

  sideBar.innerHTML = sidebar;
  
  // Change position of sidebar if is boss
  if(secondNeeded && user && user.isBoss){
    let mySidenav = document.querySelector("#mySidenav");
    let mySidenavButton = document.querySelector("#mySidenavButton");

    mySidenav.className += " patron";
    mySidenavButton.className += " patron";
  }

  // Create listener.
  if(secondNeeded && user && user.isBoss){
    /*document.getElementById("confirmUser").removeEventListener("click", getConfirmUser);
    document.getElementById("userList").removeEventListener("click", getUserList);
    document.getElementById("addFurniture").removeEventListener("click", getAddFurniture);
    document.getElementById("confirmVisits").removeEventListener("click", getConfirmVisits);
    document.getElementById("visitListPage").removeEventListener("click", getVisitListPage);*/


    document.getElementById("confirmUser").addEventListener("click", getConfirmUser);
    document.getElementById("userList").addEventListener("click", getUserList);
    document.getElementById("addFurniture").addEventListener("click", getAddFurniture);
    document.getElementById("confirmVisits").addEventListener("click", getConfirmVisits);
    document.getElementById("visitListPage").addEventListener("click", getVisitListPage);
  }
};

const getConfirmUser = () => {
  RedirectUrl("/confirmUser");
}

const getUserList = () => {
  RedirectUrl("/userList");
}

const getAddFurniture = () => {
  RedirectUrl("/addFurniture");
}

const getConfirmVisits = () => {
  RedirectUrl("/confirmVisits");
}

const getVisitListPage = () => {
  RedirectUrl("/visitListPage");
}

export default Sidebar;