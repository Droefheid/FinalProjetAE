package be.vinci.pae.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/users")
public class UserResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserFactory userFactory;

  @Inject
  private UserUCC userUcc;

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("username") && !json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Username and Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("username")) {
      return Response.status(Status.UNAUTHORIZED).entity("Username needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }


    UserDTO user = this.userUcc.login(json.get("username").asText(), json.get("password").asText());

    if (user == null)
      return Response.status(Status.UNAUTHORIZED).entity("Username or password incorrect")
          .type(MediaType.TEXT_PLAIN).build();

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

}
