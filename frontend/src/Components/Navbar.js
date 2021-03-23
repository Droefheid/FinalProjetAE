let navBar = document.querySelector("#navBar");
import { getUserSessionData } from "../utils/session.js";
import { user_me } from "../index.js";

const Navbar = () => {
  let navbar = `
  <nav class="navbar navbar-inverse navbar-expand-sm navbar-dark bg-dark mx-auto" id="navBar">
    <!-- Logo -->
    <a class="navbar-brand" href="#" data-uri="/">LiVi Satcho</a>

    <!-- Button -->
    <button
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

    <!-- Collapse body -->
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-item nav-link" href="#" data-uri="/">Home</a>`;

  // Choose how to finish the Navbar.
  let user = getUserSessionData();    
  if (user) {
    navbar += `
        <a class="nav-item nav-link" href="#">${user.username}</a>
        <a class="nav-item nav-link" href="#" data-uri="/logout">Logout</a>
      </div>
    </div>
  </nav>`;
  } else {
    navbar += `
        <a class="nav-item nav-link" href="#" data-uri="/register">Register</a>
        <a class="nav-item nav-link" href="#" data-uri="/login">Log in</a>
      </div>
    </div>
  </nav>`;
  }

  return (navBar.innerHTML = navbar);
};

export default Navbar;
