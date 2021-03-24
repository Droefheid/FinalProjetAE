let sideBar = document.querySelector("#sideBar");
let movingRow = document.querySelector("#movingRow");

const Sidebar = (needed) => {
    if(!needed) {
        // Remove padding.
        movingRow.className = "row justify-content-center mt-5";
        return (sideBar.innerHTML = "");
    }

    // Add padding.
    movingRow.className.replace("noPaddingForSideBar", "smoothTransition");
    movingRow.className += " smoothTransition";

    // SideBar Content.
    let sidebar = `<!-- SideBar -->
    <div id="mySidenav" class="sidenav">
      <form class="mb-5 pb-4">
          <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Tout
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Armoire
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Bahut
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> Bibliothèque
            </label>
          </div>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input mt-2" type="checkbox"> ...
            </label>
          </div>
        <button type="submit" class="btn btn-primary">Rechercher</button>
      </form>
    </div>`;
    // TODO Remplacer les checkbox d'exemple par une boucle sur une reponse FETCH.

    // Button for SideBar.
    sidebar += `<!-- Button for SideBar -->
    <div id="mySidenavButton" class="transitionButton">
      <a class="closebtn" onclick='
        let i = document.getElementById("mySidenav").style.width;
       if(!i) i = "0px";
        if(i != "0px"){
            //Close
            document.getElementById("mySidenav").style.width = "0px";
            document.getElementById("mySidenavButton").style.marginLeft = "0px";
            i--;
        }else{
            // Open
            document.getElementById("mySidenav").style.width = "150px";
            document.getElementById("mySidenavButton").style.marginLeft = "150px";
            i++;
        }
    '>&#9776;</a>
    </div><div class="pb-4"></div>`;

    return (sideBar.innerHTML = sidebar);
};

export default Sidebar;