import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { API_URL } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let registerPage = `
<div class="container">
 <div class="d-flex justify-content-center h-100">
  <div class="card">
    <div class="card-header">
      <h3><center>Sign up</center></h3>
    </div>
    <div class="card-body">
      <form>
        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-user"></i></span>
          </div>
          <input type="text" class="form-control" id="username" placeholder="Username">
          
        </div>
      
        <div class="row">

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" id="firstname" placeholder="First name">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" id="lastname" placeholder="Last name">
                          </div>
                            </div>
        </div>


        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fas fa-envelope"></i></span>
        </div>
        <input type="email" class="form-control" id="email" placeholder="Email">
      </div>


      <div class="row">

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" id="password" placeholder="Password">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" class="form-control" id="confirm_password" placeholder="Confirm password">
                          </div>
                            </div>
        </div>


        <hr>

        <div class="row">

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-road"></i></span>
                            </div>
                            <input type="text" class="form-control" id="street" placeholder="Street">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fa fa-address-book" aria-hidden="true"></i></span>
                            </div>
                            <input type="text" class="form-control" id="building_number" placeholder="Number">
                          </div>
                            </div>

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fa fa-inbox" aria-hidden="true"></i></span>
                            </div>
                            <input type="text" class="form-control" id="unit_number" placeholder="Post box">
                          </div>
                            </div>
        </div>

        <div class="row">

        <div class="col-sm">
        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fas fa-city"></i></span>
        </div>
        <input type="text" class="form-control" id="commune" placeholder="Commune">
      </div>
        </div>


        <div class="col-sm">
        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fas fa-flag"></i></span>
        </div>
        <input type="text" class="form-control" id="country" placeholder="Country">
        </div>
        </div>
        </div>

       
        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fa fa-map-pin" aria-hidden="true"></i></span>
        </div>
        <input type="text" class="form-control" id="postcode" placeholder="Postal code">
      </div>
       
        <div class="form-group">
          <input type="submit" value="Register" class="btn btn-lg btn-outline-primary btn-block">
        </div>
      </form>
    </div>
    <div class="card-footer">
      <div class="d-flex justify-content-center">
      </div>
      <div id="messageBoardForm"></div>
    </div>
  </div>
 </div>
</div>`;



const RegisterPage = () => {
  Sidebar();
  let page = document.querySelector("#content");
  page.innerHTML = registerPage;
  let registerForm = document.querySelector("form");
  registerForm.addEventListener("submit", onRegister);
};

const onRegister = (e) => {
  e.preventDefault();
  let user = {
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    lastname: document.getElementById("lastname").value,
    firstname: document.getElementById("firstname").value,
    street: document.getElementById("street").value,
    building_number: document.getElementById("building_number").value,
    postcode: document.getElementById("postcode").value,
    commune: document.getElementById("commune").value,
    country: document.getElementById("country").value,
    unit_number: document.getElementById("unit_number").value,
    username: document.getElementById("username").value
  };

  fetch(API_URL + "users/register", {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    body: JSON.stringify(user), // body data type must match "Content-Type" header
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then(errMsg => { throw new Error(errMsg) })
        .catch((err) => onError(err));
      }
      else
        return onUserRegistration();
    })
};

const onUserRegistration = () => {
  alert("You have been registered. An admin must now validate your inscription.");
  RedirectUrl("/");
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
   messageBoard.innerHTML = err;
  
};

export default RegisterPage;
