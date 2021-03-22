package be.vinci.pae.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.UserDTO;
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
  private UserUCC userUcc;

  @Inject
  private DomaineFactory domaineFactory;


  /**
   * Login the user if exists or send error message.
   * 
   * @param json object containing a username and password.
   * @return a user if user exists in database and matches password.
   */
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

    if (user == null) {
      return Response.status(Status.UNAUTHORIZED).entity("Username or password incorrect")
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

  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    if (!json.hasNonNull("username")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("email")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("lastname")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("firstname")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("street")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("building_number")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("postcode")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("commune")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("country")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("unit_number")) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }

    UserDTO user = domaineFactory.getUserDTO();
    Adress adress = domaineFactory.getAdress();

    user.setUserName(json.get("username").asText());
    user.setFirstName(json.get("firstname").asText());
    user.setLastName(json.get("lastname").asText());
    user.setEmail(json.get("email").asText());
    user.setPassword(json.get("password").asText());
    adress.setBuildingNumber(json.get("building_number").asText());
    adress.setCommune(json.get("commune").asText());
    adress.setPostCode(json.get("postcode").asText());
    adress.setStreet(json.get("street").asText());
    adress.setUnitNumber(json.get("unit_number").asText());
    adress.setCountry(json.get("country").asText());
    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);
    user.setRegistrationDate(timestamp);
    UserDTO userDTO = userUcc.register(user, adress);

    if (userDTO == null) {
      return Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build();
    }

    return Response.ok(MediaType.APPLICATION_JSON).build();
  }

}
