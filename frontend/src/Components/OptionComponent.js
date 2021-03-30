let option = document.querySelector("#option");



let optionPage = `
<div id = "title_furniture">
   <h3>Titre de Meuble </h3>
</div>

<div id="option">
  <img class="img-fluid" src="assets/Images/Bureau_1.png"  style="width:350px;">

  <div class="row">
    <div class="col-sm-8">
        <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
        <img class="img-fluid"  src="assets/Images/Bureau_1.png"  style="width:200px;">
        <h4 style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;"> Types : </h4>
    </div>


<label class="col-2 col-form-label">Date de fin de l'option</label>
<div>
    <div class="form-group row">
  
  <div class="col-10">
    <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="example-datetime-local-input">
  </div>
</div>
    <div>
    <button class="btn btn-info">Introduire votre option</button>
    </div>
  </div>

</div>
</div>
`;



const OptionPage = () => {    
    
 
    return page.innerHTML = optionPage;
 };
 
 export default OptionPage;
