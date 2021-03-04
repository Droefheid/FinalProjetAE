package be.vinci.pae.api;

import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.services.DataServiceUserCollection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/users")
public class UserResource {

  @Inject
  private DataServiceUserCollection dataService; // TODO Changer pour UserUCC

  @Inject
  private UserFactory userFactory;

  // TODO UserDTO ????

  // TODO methode login
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(Json json) {
    return null;
  }

}
