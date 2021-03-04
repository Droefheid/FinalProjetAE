package be.vinci.pae.api;

import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.services.DataServiceUserCollection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Singleton
@Path("/users")
public class UserResource {

  @Inject
  private DataServiceUserCollection dataService; // TODO Changer pour UserUCC

  @Inject
  private UserFactory userFactory;

  // TODO UserDTO ????

  @POST
  @Path("init")
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO init() {
    UserDTO user = this.userFactory.getPublicUser();
    user.setID(1);
    user.setBoss(true);
    user.setAntiqueDealer(true);
    user.setLastName("Livi");
    user.setFirstName("Satcho");
    user.setUsername("Livi Satcho");
    user.setAdressID(1);
    user.setEmail("LiviSatcho@hotmzil.com");
    user.setConfirmed(true);
    user.setRegistrationDate("27-02-21");
    user.setPassword(user.hashPassword("password"));
    this.dataService.addUser(user);
    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    return Json.filterPublicJsonView(user, UserDTO.class);
  }

  @GET
  @Path("me")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public UserDTO getUser(@Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    return Json.filterPublicJsonView(currentUser, UserDTO.class);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<UserDTO> getAllUsers() {
    System.out.println("List after serialization : "
        + Json.filterPublicJsonViewAsList(this.dataService.getUsers(), UserDTO.class).toString());
    return Json.filterPublicJsonViewAsList(this.dataService.getUsers(), UserDTO.class);
  }

}
