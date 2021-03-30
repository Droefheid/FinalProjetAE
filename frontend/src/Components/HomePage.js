import Sidebar from "./SideBar";

let page = document.querySelector("#content");

let homePage = `
<div class="row">
   <h3>Page d'accueil</h3>
</div>
<div class="row">
   <p>When using the .navbar-brand class on images, Bootstrap 4 will automatically style the image to fit the navbar.</p>
</div>

<div class="container-fluid row justify-content-center">
   <img class="ml-0 mr-2 mt-2 mb-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
   <img class="m-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
   <img class="m-2" src="assets/Images/Bureau_1.png" alt="logo" style="width:100px;">
</div>`;
// TODO il faut remplacer les 3 images de Test par un caroussel.

const HomePage = () => {    
   Sidebar(true);

   return page.innerHTML = homePage;
};

export default HomePage;
