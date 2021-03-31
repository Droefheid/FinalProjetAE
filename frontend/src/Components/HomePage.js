import Sidebar from "./SideBar";

let page = document.querySelector("#content");

let homePage = `
   <div class="centrer">
      <div class="row" >
         <h3 >Home Page</h3>
      </div> 
      <div class="row">
         <p>When using the .navbar-brand class on images, Bootstrap 4 will automatically style the image to fit the navbar.</p>
      </div>

      <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
      <div class="carousel-inner">
         <div class="carousel-item active">
            <img class="d-block w-25" src="assets/Images/Bureau_1.png" alt="First slide">
         </div>
         <div class="carousel-item">
            <img class="d-block w-25" src="assets/Images/Bureau_1.png" alt="Second slide">
         </div>
         <div class="carousel-item">
            <img class="d-block w-25" src="assets/Images/Bureau_1.png" alt="Third slide">
         </div>
         </div>
         <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
         </a>
         <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
         </a>
      </div>



   </div>
   
`;
// TODO il faut remplacer les 3 images de Test par un caroussel.

const HomePage = () => {    
   Sidebar(true);

   return page.innerHTML = homePage;
};

export default HomePage;
