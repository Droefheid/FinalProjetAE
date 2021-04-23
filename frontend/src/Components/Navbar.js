let navBar = document.querySelector("#navBar");
import { getUserSessionData } from "../utils/session.js";

const Navbar = () => {
  // Big Navbar
  let navbar = `<!-- Big Navbar -->
  <nav class="navbar navbar-inverse navbar-expand-sm navbar-dark bg-dark mx-auto" id="navBar">
    <!-- Logo -->
    <a class="navbar-brand" href="#" data-uri="/">
      <img src="assets/Images/logoAE_v2.png" class="logo_size" alt="Logo">
    </a>

    <!-- Button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Collapse body -->
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-item nav-link" href="#" data-uri="/">Home</a>
      </div>`;

  // Choose how to finish the Navbar.
  let user = getUserSessionData();    
  if (user) {
    navbar += `
      <div class="navbar navbar-nav ml-auto pt-2">
        <a class="nav-item nav-link" href="#">${user.username}</a>
        <a class="nav-item nav-link" href="#" data-uri="/logout">Logout</a>
        <a class="btn btn-info" href="#" data-uri="/seeMyFurniture" style="margin:15px;">See my furniture</a>
      <a class="btn btn-info" href="#" data-uri="" style="margin:15px;">Introduce a visit</a>
  
    <a class="btn btn-info" href="#" data-uri="/confirmUser" style="margin:15px;">Confirm a user</a>
      </div>
    </div>
  </nav>`;


 
  } else {
    navbar += `
      <div class="navbar navbar-nav ml-auto pt-2">
        <a class="nav-item nav-link" href="#" data-uri="/register">Register</a>
        <a class="nav-item nav-link" href="#" data-uri="/login">Log in</a>
        
      </div>
    </div>
  </nav>`;
  }

  // Secondary Navbar
  navbar += `<!-- Secondary Navbar -->
  <nav class="navbar navbar-expand-sm bg-light navbar-light pt-0" id="navBar">
	  <div class="absolute"><a class="navbar-brand" href="#" data-uri="/">Livi Satcho</a></div>
    <div class="navbar navbar-nav ml-auto mr-auto pt-3" >
      <a class="btn btn-info" href="#" data-uri="/furniture" style="margin:15px;">See all the furnitures</a>
    </div>
  </nav>`;

  return (navBar.innerHTML = navbar);
};

export default Navbar;

