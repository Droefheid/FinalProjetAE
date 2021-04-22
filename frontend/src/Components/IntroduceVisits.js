import { RedirectUrl } from "./Router.js";
import { API_URL } from "../utils/server.js";
import { getTokenSessionDate } from "../utils/session";
import Sidebar from "./SideBar.js";

let introduceVisits = `
<div class="containerForm">
 <div class="d-flex justify-content-center h-100">
  <div class="card">
    <div class="card-header">
      <h3><center>Introduce a visit</center></h3>
    </div>
    <div class="card-body">

    <div class="row" id = "flexbox">
    <div class="column">
    <h5><center>Concerned funitures<center></h5>
    <hr>
    <input type="image" src="assets/Images/Bureau_1.png" style="float:right" width="200" height="200"> 
    <br/>
    <p><a href="assets/Images/Bureau_1.png" download>Download picture</a></p>   <!-- Href a remplacer avec la photo concernee -->
    </div>


    <div class="column">

    <h5><center>Location of the furnitures <center></h5>
    <hr>
      <form>
        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fa fa-calendar" aria-hidden="true"></i></span>
          </div>
        
          <input placeholder="Date of visit" class="textbox-n" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" id="request_date" />
          
        </div>
      

        


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
        </div>


        <div class="input-group form-group">
        <div class="input-group-prepend">
        <span class="input-group-text"><i class="fa fa-map-pin" aria-hidden="true"></i></span>
        </div>
        <input type="text" class="form-control" id="postcode" placeholder="Postal code">
      </div>


      <div class="row">

                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-flag"></i></span>
                            </div>
                            <input type="text" class="form-control" id="country" placeholder="Country">
                          </div>
                            </div>


                            <div class="col-sm">
                            <div class="input-group form-group">
                            <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                            </div>
                            <input type="text" class="form-control" id="unit_number" placeholder="Post box">
                          </div>
                            </div>
        </div>


        <hr>

      

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
          <span class="input-group-text"><i class="fas fa-info"></i></span>
        </div>
        <textarea class="form-control" placeholder="Chairs in perfect condition"  id="explanatory_note" ></textarea>
 
        </div>
        </div>
        </div>

        <div class="form-group">
          <input type="submit" value="Send request" class="btn btn-lg btn-outline-primary btn-block">
        </div>
      </form>
    
    </div>

    <div class="column">
    <h5><center>Time slot of availability<center></h5>
    <hr>
    <textarea class="form-control" placeholder="Available on mondays"  id="date_hours_visit" ></textarea>
      
    </div>

    
  </div>
  <div class="card-footer">
      <div class="d-flex justify-content-center">
      </div>
      <div id="messageBoardForm"></div>
    </div>
 </div>
 </div>
</div>`;

const IntroduceVisits = () => {
  Sidebar();
  let page = document.querySelector("#page");
  page.innerHTML = introduceVisits;
  let introduceVisitsForm = document.querySelector("form");
  introduceVisitsForm.addEventListener("submit", onIntroduceVisits);
};

const onIntroduceVisits = (e) => {
  e.preventDefault();

  let visit = {
    request_date: document.getElementById("request_date").value,
    explanatory_note: document.getElementById("explanatory_note").value,
    street: document.getElementById("street").value,
    building_number: document.getElementById("building_number").value,
    postcode: document.getElementById("postcode").value,
    commune: document.getElementById("commune").value,
    country: document.getElementById("country").value,
    unit_number: document.getElementById("unit_number").value,
  };
  let id = getTokenSessionDate();
  fetch(API_URL + "visits/introduceVisits", {
    method: "POST", // *GET, POST, PUT, DELETE, etc.
    body: JSON.stringify(visit), // body data type must match "Content-Type" header
    headers: {
      "Content-Type": "application/json",
      Authorization: id,
    },
  }).then((response) => {
    if (!response.ok) {
      return response.text().then((err) => onError(err));
    } else return onVisitRequest();
  });
};

const onVisitRequest = () => {
  alert("Your request has been sent. An admin must now confirm your visit.");
  RedirectUrl("/");
};

const onError = (err) => {
  let messageBoard = document.querySelector("#messageBoardForm");
  messageBoard.innerHTML = err;
};

export default IntroduceVisits;
