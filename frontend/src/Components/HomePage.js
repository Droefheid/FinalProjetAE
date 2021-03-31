import { getUserSessionData } from "../utils/session";
import Sidebar from "./SideBar";

let page = document.querySelector("#page");

let homePage = `

<div class="row">
   <h3>Home Page</h3>
</div>

<div class="container-fluid row justify-content-center">
   <img class="ml-0 mr-2 mt-2 mb-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
   <img class="m-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
   <img class="m-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
</div>



<div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block w-100" src="assets/Images/Bureau_1.png" alt="First slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="assets/Images/Bureau_1.png" alt="Second slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="assets/Images/Bureau_1.png" alt="Third slide">
    </div>
  </div>
</div>
`;

const HomePage = () => {   
   Sidebar(true);
   return page.innerHTML = homePage;
};

export default HomePage;
