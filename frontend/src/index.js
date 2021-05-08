import { setLayout } from "./utils/render.js";
import { checkTokenOnLoad } from "./utils/session.js";
import { Router } from "./Components/Router.js";
import Navbar from "./Components/Navbar.js";
import Sidebar from "./Components/SideBar.js";

/* use webpack style & css loader*/
import "./stylesheets/style.css";
/* load bootstrap css (web pack asset management) */
import 'bootstrap/dist/css/bootstrap.css';
/* load bootstrap module (JS) */
import 'bootstrap';


const HEADER_TITLE = "LiVi Satcho Antiquaire";
const FOOTER_TEXT = `Contact : livisatcho@hotmail.com`;

let user_me = { itself: null, furnitureId: null };
console.log("INDEX");

checkTokenOnLoad();
console.log(user_me);

Sidebar(true);
Navbar();
Router();

setLayout(HEADER_TITLE, FOOTER_TEXT);

export { user_me };