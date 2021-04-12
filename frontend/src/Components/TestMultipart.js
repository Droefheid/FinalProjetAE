import { getTokenSessionDate } from "../utils/session";
import Sidebar from "./SideBar";
import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { API_URL, ALERT_BOX } from "../utils/server.js";

let formPage = `<form id="photo" class="form-inline" enctype="multipart/form-data">
    <input type="file" id="files" name="file" multiple>
    <div id="showImg"></div>
    <input type="submit" value="update" class="btn btn-lg btn-primary btn-block">
</form>`;

const TestMultipartPage = () => {   
    Sidebar(true);
    let page = document.querySelector("#page");
    page.innerHTML = formPage;

    let form = document.querySelector("#photo");
    form.addEventListener("submit", onSubmit);
    let uploadImage = document.querySelector("#files");
    uploadImage.addEventListener("change", onUpload);
}

const onUpload = (e) => {
    let files = e.target.files;
    
    // Reset visuel
    document.getElementById('showImg').innerHTML = "";

    // Add visuel and 
    for(let i = 0; i < files.length; i++){
        let reader = new FileReader();
        reader.onloadend = function() {
            document.getElementById('showImg').innerHTML += `<img id="blah" src="` 
            + reader.result + `" style="width: 100px" alt="your image" />`;
        }
        reader.readAsDataURL(files[i]);
    }
}

const onSubmit = (e) => {
    e.preventDefault();
    
    const input = document.getElementById('files');
    //console.log(input);

    /**************************************/
    const formData = new FormData();
    for(let i = 0; i < input.files.length; i++){
        console.log(input.files[i]);
        formData.append("photo"+i, input.files[i]);
    }
    console.log("Formdata: ", formData, "get('photo0'): ", formData.get("photo0"));
    /**************************************/

    let id = getTokenSessionDate();
    fetch(API_URL + "furnitures/test1", {
        method: "POST", 
        body: formData, 
        headers: {
            "Authorization": id,
        },
    })
    .then((response) => {
        if (!response.ok) {
          return response.text().then((err) => onError(err));
        }
        else
          return response.json().then((data) => onFurnitureUpdate(data));
    });
}

const onFurnitureUpdate = (furnitureData) => {
    // re-render the navbar for the authenticated user
    Navbar();
    RedirectUrl("/updateFurniture");
};
  
const onError = (err) => {
    let messageBoard = document.querySelector("#messageBoard");
    if(err.message) ALERT_BOX(messageBoard, err.message);
    else ALERT_BOX(messageBoard, err);
};
 
export default TestMultipartPage;