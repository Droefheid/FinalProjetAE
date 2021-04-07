import { getUserSessionData } from "../utils/session";
import Sidebar from "./SideBar";

let page = document.querySelector("#page");

let homePage = `


<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block" src="assets/Images/Bureau_1.png" alt="First slide" >
    </div>
    <div class="carousel-item">
      <img class="d-block" src="assets/Images/logoAE_v2.png" alt="Second slide" >
    </div>
    <div class="carousel-item">
      <img class="d-block" src="assets/Images/Bureau_1.png" alt="Third slide" >
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>




`;

const HomePage = () => {   
   Sidebar(true);
   return page.innerHTML = homePage;
};

export default HomePage;
