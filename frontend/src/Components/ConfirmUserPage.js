let option = document.querySelector("#confirmUser");



let confirmUserPage = `
<div class="container-fluid mt-4">
    
    <!-- table des utilisateurs -->
    <div class="card shadow mb-4">
      <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">User management</h6>
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table" id="dataTable" width="100%" cellspacing="0">
            <thead>
              <tr>
                <th>Last name</th>
                <th>First name</th>
                <th>Username</th>
                <th>Mail</th>
                <th>Registration Date</th>
                <th>Address</th>
                <th>User confirmation</th>
                <th>Antique dealer</th>
                <th>Is boss</th>
                <th>Save</th>
              </tr>
            </thead>
            <tbody id="list_users">    
          
            </tbody>
          </table>
        </div>
      </div>
    </div>
</div>
`;



const ConfirmUserPage = () => {    
    return page.innerHTML = confirmUserPage;
 };
 
 export default ConfirmUserPage;