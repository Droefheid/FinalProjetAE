import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { setUserSessionData } from "../utils/session.js";
import { API_URL } from "../utils/server.js";

/* In a template literal, the ` (backtick), \ (backslash), and $ (dollar sign) characters should be 
escaped using the escape character \ if they are to be included in their template value. 
By default, all escape sequences in a template literal are ignored.*/


/*let registerPage = `<form>
<div class="form-group">
  <label for="email">Email</label>
  <input class="form-control" id="email" type="text" name="email" placeholder="Enter your email" required="" pattern="^\\w+([.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+\$" />
</div>
<div class="form-group">
  <label for="password">Password</label>
  <input class="form-control" id="password" type="password" name="password" placeholder="Enter your password" required="" pattern=".*[A-Z]+.*" />
</div>
<button class="btn btn-primary" id="btn" type="submit">Submit</button>
<!-- Create an alert component with bootstrap that is not displayed by default-->
<div class="alert alert-danger mt-2 d-none" id="messageBoard"></div><span id="errorMessage"></span>
</form>`;
*/

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
                            <input type="text" class="form-control" id="first_name" placeholder="First name">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" class="form-control" id="last_name" placeholder="Last name">
                          </div>
                            </div>
        </div>


        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fas fa-envelope"></i></span>
        </div>
        <input type="email" class="form-control" id="username" placeholder="Email">
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
                            <input type="password" class="form-control" id="password" placeholder="Road">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fa fa-address-book" aria-hidden="true"></i></span>
                            </div>
                            <input type="number" class="form-control" id="confirm_password" placeholder="Number">
                          </div>
                            </div>

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><i class="fa fa-inbox" aria-hidden="true"></i></span>
                            </div>
                            <input type="text" class="form-control" id="confirm_password" placeholder="Post box">
                          </div>
                            </div>
        </div>

        <div class="row">

        <div class="col-sm">
        <div class="input-group form-group">
        <div class="input-group-prepend">
          <span class="input-group-text"><i class="fas fa-city"></i></span>
        </div>
        <input type="text" class="form-control" id="municipality" placeholder="Municipality">
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
        <input type="text" class="form-control" id="postal_code" placeholder="Postal code">
      </div>
       
        <div class="form-group">
          <input type="submit" value="Register" class="btn btn-lg btn-outline-primary btn-block">
        </div>
      </form>
    </div>
    <div class="card-footer">
      <div class="d-flex justify-content-center">
      </div>
      <div id="messageBoard"></div>
    </div>
  </div>
 </div>
</div>`;



const RegisterPage = () => {
  let page = document.querySelector("#page");
  page.innerHTML = registerPage;
  let registerForm = document.querySelector("form");
  registerForm.addEventListener("submit", onRegister);
};

const onRegister = (e) => {
  e.preventDefault();
  let user = {
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
  };

  fetch(API_URL + "users/", {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    body: JSON.stringify(user), // body data type must match "Content-Type" header
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok)
        throw new Error(
          "Error code : " + response.status + " : " + response.statusText
        );
      return response.json();
    })
    .then((data) => onUserRegistration(data))
    .catch((err) => onError(err));
};

const onUserRegistration = (userData) => {
  console.log("onUserRegistration", userData);
  const user = { ...userData, isAutenticated: true };
  setUserSessionData(user);
  // re-render the navbar for the authenticated user
  Navbar();
  RedirectUrl("/list");
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoard");
  let errorMessage = "";
  if (err.message.includes("409"))
    errorMessage = "This user is already registered.";
  else errorMessage = err.message;
  messageBoard.innerText = errorMessage;
  // show the messageBoard div (add relevant Bootstrap class)
  messageBoard.classList.add("d-block");
};

export default RegisterPage;
