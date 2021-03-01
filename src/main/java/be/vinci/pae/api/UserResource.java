package be.vinci.pae.api;

import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.User;
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
  private DataServiceUserCollection dataService;

  @Inject
  private UserFactory userFactory;

  @POST
  @Path("init")
  @Produces(MediaType.APPLICATION_JSON)
  public User init() {
    User user = this.userFactory.getUser();
    user.setID(1);
    user.setBoss(true);
    user.setAntiqueDealer(true);
    user.setLastName("Livi");
    user.setFirstName("Satcho");
    user.setPseudo("Livi Satcho");
    user.setAdressID(1);
    user.setEmail("LiviSatcho@hotmzil.com");
    user.setConfirmed(true);
    user.setDateRegistration("27-02-21");
    user.setPassword(user.hashPassword("password"));
    this.dataService.addUser(user);
    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    return Json.filterPublicJsonView(user, User.class);
  }

  @GET
  @Path("me")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public User getUser(@Context ContainerRequest request) {
    User currentUser = (User) request.getProperty("user");
    return Json.filterPublicJsonView(currentUser, User.class);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<User> getAllUsers() {
    System.out.println("List after serialization : "
        + Json.filterPublicJsonViewAsList(this.dataService.getUsers(), User.class).toString());
    return Json.filterPublicJsonViewAsList(this.dataService.getUsers(), User.class);
  }

}
