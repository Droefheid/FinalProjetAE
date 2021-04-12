import { setLayout } from "./utils/render.js";
import { checkTokenOnLoad } from "./utils/session.js";
import { Router } from "./Components/Router.js";
import Navbar from "./Components/Navbar.js";

/* use webpack style & css loader*/
import "./stylesheets/style.css";
/* load bootstrap css (web pack asset management) */
import 'bootstrap/dist/css/bootstrap.css';
/* load bootstrap module (JS) */
import 'bootstrap';


const HEADER_TITLE = "LiVi Satcho Antiquaire";
const FOOTER_TEXT = `Contact : livisatcho@hotmail.com`;

let user_me = { itself: null, furnitureId: null };

checkTokenOnLoad();

Navbar();
Router();

setLayout(HEADER_TITLE, FOOTER_TEXT);

export { user_me };