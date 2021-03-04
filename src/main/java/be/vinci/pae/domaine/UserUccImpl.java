package be.vinci.pae.domaine;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.services.DataServiceUserCollection;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class UserUccImpl implements UserUCC {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private User user;

  @Inject
  private DataServiceUserCollection userDao;

  @Override
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(String username, String password) {
    // Get and check credentials
    if ((username == null || username == "") && (password == null || password == "")) {
      return Response.status(Status.UNAUTHORIZED).entity("Pseudo and Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (username == null || username == "") {
      return Response.status(Status.UNAUTHORIZED).entity("Pseudo needed").type(MediaType.TEXT_PLAIN)
          .build();
    }
    if (username == null || username == "") {
      return Response.status(Status.UNAUTHORIZED).entity("Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }

    // Try to login
    User user = (User) this.userDao.getUser(username); // TODO Autorise ????
    if (user == null || !user.checkPassword(password)) {
      return Response.status(Status.UNAUTHORIZED).entity("Pseudo or password incorrect")
          .type(MediaType.TEXT_PLAIN).build();
    }
    // Create token
    String token;
    try {
      token =
          JWT.create().withIssuer("auth0").withClaim("user", user.getID()).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      throw new WebApplicationException("Unable to create token", e, Status.INTERNAL_SERVER_ERROR);
    }
    // Build response

    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    UserDTO publicUser = Json.filterPublicJsonView(user, UserDTO.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  @Override
  public Response register(UserDTO userDTO) {
    // TODO Auto-generated method stub
    return null;
  }

}
