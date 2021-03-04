package be.vinci.pae.api;

import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.services.DataServiceUserCollection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;

@Singleton
@Path("/users")
public class UserResource {

  @Inject
  private DataServiceUserCollection dataService; // TODO Changer pour UserUCC

  @Inject
  private UserFactory userFactory;

  // TODO UserDTO ????

  // TODO methode login

}
