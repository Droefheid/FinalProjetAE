let navBar = document.querySelector("#navBar");
import {getUserSessionData} from "../utils/session.js";
// destructuring assignment
const Navbar = () => {
  let navbar;
  let user = getUserSessionData().user;    
  if (user) {
    navbar = `<nav class="navbar navbar-inverse navbar-expand-md navbar-dark bg-dark mx-auto" id="navBar">
  <a class="navbar-brand" href="#" data-uri="/">LiVi Satcho</a><button
    class="navbar-toggler"
    type="button"
    data-toggle="collapse"
    data-target="#navbarNavAltMarkup"
    aria-controls="navbarNavAltMarkup"
    aria-expanded="false"
    aria-label="Toggle navigation"
  >
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link" href="#" data-uri="/">Home</a>
      <a class="nav-item nav-link" href="#">${user.username}</a>
      <a class="nav-item nav-link" href="#" data-uri="/logout">Logout</a>
    </div>
  </div>
  </nav>`;
  } else {
    navbar = `<nav class="navbar navbar-expand-md navbar-dark bg-dark mx-auto" id="navBar">
  <a class="navbar-brand" href="#" data-uri="/">LiVi Satcho</a><button
    class="navbar-toggler"
    type="button"
    data-toggle="collapse"
    data-target="#navbarNavAltMarkup"
    aria-controls="navbarNavAltMarkup"
    aria-expanded="false"
    aria-label="Toggle navigation"
  >
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link" href="#" data-uri="/">Home</a>
      <a class="nav-item nav-link" href="#" data-uri="/register">Register</a>
      <a class="nav-item nav-link" href="#" data-uri="/login">Log in</a>
    </div>
  </div>
  </nav>`;
  }

  return (navBar.innerHTML = navbar);
};

export default Navbar;
