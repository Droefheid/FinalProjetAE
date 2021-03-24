/* In a template literal, the ` (backtick), \ (backslash), and $ (dollar sign) characters should be 
escaped using the escape character \ if they are to be included in their template value. 
By default, all escape sequences in a template literal are ignored.*/
import { getUserSessionData, setUserSessionData } from "../utils/session.js";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { API_URL, ALERT_BOX } from "../utils/server.js";
import Sidebar from "./SideBar.js";

let remember = false;

let loginPage = `<div class="container">
<div class="d-flex justify-content-center h-100 mt-4">
  <div class="card">
    <div class="card-header">
      <h3>Sign In</h3>
    </div>
    <div class="card-body">
      <form>
        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-user"></i></span>
          </div>
          <input type="text" class="form-control" id="username" placeholder="username">
          
        </div>
        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-key"></i></span>
          </div>
          <input type="password" class="form-control" id="password" placeholder="password">
        </div>
        <div class="row align-items-center remember">
          <input type="checkbox" id="remember">Remember Me
        </div>
        <div class="form-group">
          <input type="submit" value="login" class="btn float-right login_btn">
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

const LoginPage = () => {
  Sidebar();

  let page = document.querySelector("#page");
  page.innerHTML = loginPage;
  let loginForm = document.querySelector("form");
  const user = getUserSessionData();
  if (user) {
    // re-render the navbar for the authenticated user.
    Navbar();
    RedirectUrl("/");
  } else loginForm.addEventListener("submit", onLogin);
};

const onLogin = (e) => {
  e.preventDefault();
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  let user = {
    "username": username,
    "password": password,
  };

  remember = document.getElementById("remember").checked;

  fetch(API_URL + "users/login", {
    method: "POST", 
    body: JSON.stringify(user), 
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
    .then((data) => onUserLogin(data))
    .catch((err) => onError(err));
};

const onUserLogin = (userData) => {
  const user = { ...userData, isAutenticated: true };
  console.log("est passer", user);
  setUserSessionData(user, remember);
  // re-render the navbar for the authenticated user
  Navbar();
  RedirectUrl("/");
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  let errorMessage = "";
  if (err.message.includes("401")) {
    messageBoard.innerHTML = '<div class="alert alert-danger">Wrong username or password.</div>';
  }else{
    errorMessage = err.message;
    ALERT_BOX(messageBoard, errorMessage);
  }
};

export default LoginPage;
