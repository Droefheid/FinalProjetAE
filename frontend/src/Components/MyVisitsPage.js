import { API_URL, ALERT_BOX } from "../utils/server.js";
import { getTokenSessionDate, getUserSessionData } from "../utils/session.js";
import { RedirectUrl } from "./Router.js";
import Sidebar from "./SideBar";
import Navbar from "./Navbar.js";

let page = document.querySelector("#page");

const MyVisitsPage = () => { 
    const user = getUserSessionData();
	if (!user) {
		Navbar();
		RedirectUrl(`/`);
        return;
    }

    Sidebar(true, false);
    page.innerHTML = `<div class="loader"></div>`;  

    let id = getTokenSessionDate();
    fetch(API_URL + "visits/myVisits", {
        method: "GET",
        headers: {
        "Content-Type": "application/json",
        "Authorization": id
        },
    })
    .then((response) => {
        if (!response.ok) {
            return response.text().then((err) => onError(err));
        }
        else
            return response.json().then((data) => getAddress(data));
    });
};

const getAddress = (oldData) => {
    let list = {
        "visits": oldData.list,
    }
    let id = getTokenSessionDate();
    fetch(API_URL + "users/getVisitsAddress", {
        method: "POST",
        body: JSON.stringify(list),
        headers: {
        "Content-Type": "application/json",
        "Authorization": id
        },
    })
    .then((response) => {
        if (!response.ok) {
            return response.text().then((err) => onError(err));
        }
        else
            return response.json().then((data) => onVisitList(oldData, data.address));
    });
};

const onVisitList = (data, addressList) => {

    let visitList = `<div id="messageBoardForm"></div>
    <div id="list" class="table-responsive mb-5">
        <table id="description_user" class="table table-striped table-bordered" style="width:790px; height: 300px;" >
            <thead>
            <tr>
                <th>labelFurniture</th> 
                <th>requestDate</th>
                <th>timeSlot</th>
                <th>address</th>
                <th>isConfirmed</th>
                <th>dateAndHoursVisit</th>
                <th>explanatoryNote</th>
            </tr>
            </thead>
            <tbody>`;

    let visits = data.list;
    visits.forEach(visit => {
        visitList += `<tr>
            <td>${visit.labelFurniture}</td>
            <td><input type="datetime-local" class="form-control" id="pickUpDate" value="${createTimeStamp(visit.requestDate)}" name="pickUpDate" step="1" disabled></td>
            <td>${visit.timeSlot}</td>
            <td>`;
            addressList.forEach(address => {
                if(address.id == visit.addressId) visitList += `${address.country} ${address.postCode}, ${address.commune} 
                ${address.street}, ${address.buildingNumber} bt ${address.unitNumber}`;
            });
            visitList += `</td>
            <td>${visit.isConfirmed}</td>
            <td><input type="datetime-local" class="form-control" id="pickUpDate" value="`;
            if(visit.dateAndHoursVisit) visitList += `${createTimeStamp(visit.dateAndHoursVisit)}`;
            visitList += `" name="pickUpDate" step="1" disabled></td>
            <td>`;
            if(visit.explanatoryNote) visitList += `${visit.explanatoryNote}`;
            else {
                if(visit.isConfirmed) visitList += `Confirmed`;
                else visitList += `In progress`;
            }
            visitList += `</td>
        </tr>`;
    });

    visitList += `</tbody>
        </table>
    </div>`;
    page.innerHTML = visitList;
};

const createTimeStamp = (dateString) => {
    let Timestamp = new Date(dateString);
    let timeSplit = Timestamp.toLocaleString().split("/");
    return timeSplit[2].substr(0, 4) + "-" + timeSplit[1] + "-" + timeSplit[0] + "T" + Timestamp.toLocaleTimeString();
};

const onError = (err) => {
    page.innerHTML = `<div id="messageBoardForm">`;
    let messageBoard = document.querySelector("#messageBoard");
    if(err.message) ALERT_BOX(messageBoard, err.message);
    else ALERT_BOX(messageBoard, err);
};

export default MyVisitsPage;