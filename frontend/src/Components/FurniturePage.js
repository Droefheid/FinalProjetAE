import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";



let furniturePage = `<div class="container">
<div class="d-flex justify-content-center h-100">
 <div class="card">
   <div class="card-header">
     <h3><center>Furnitures</center></h3>
   </div>
   <div class="card-body">
   <div class="card-footer">
     <div class="d-flex justify-content-center">
     </div>
     <div id="messageBoard"></div>
   </div>
 </div>
</div>
</div>`;



const FurniturePage = () => {
  let page = document.querySelector("#page");
  page.innerHTML = furniturePage;
};


const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  let errorMessage = "";
  messageBoard.classList.add("d-block");
};

export default FurniturePage;