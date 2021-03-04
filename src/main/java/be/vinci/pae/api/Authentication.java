package be.vinci.pae.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.AdressFactory;
import be.vinci.pae.domaine.User;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.services.DataServiceAdressCollection;
import be.vinci.pae.services.DataServiceUserCollection;
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
@Path("/auths")
public class Authentication {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private DataServiceUserCollection dataServiceUser;

  @Inject
  private DataServiceAdressCollection dataServiceAdress;

  @Inject
  private UserFactory userFactory;

  @Inject
  private AdressFactory adressFactory;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("pseudo") && !json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Pseudo and Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (!json.hasNonNull("pseudo")) {
      return Response.status(Status.UNAUTHORIZED).entity("Pseudo needed").type(MediaType.TEXT_PLAIN)
          .build();
    }
    if (!json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    String pseudo = json.get("username").asText();
    String password = json.get("password").asText();
    // Try to login
    UserDTO user = this.dataServiceUser.getUser(pseudo);
    User user2 = (User) user; // TODO ???? autorise?
    if (user == null || !user2.checkPassword(password)) {
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

  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Login and password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    /*
     * Response response = checkNonNullCredentialsRegister(json); if (response != null) return response;
     */
    String pseudo = json.get("pseudo").asText();
    String lastname = json.get("lastname").asText();
    String firstname = json.get("firstname").asText();
    String street = json.get("street").asText();
    int houseNumber = json.get("houseNumber").asInt();
    int mailBoxNumber = json.get("mailBoxNumber").asInt();
    int postalCode = json.get("postalCode").asInt();
    String commune = json.get("commune").asText();
    String country = json.get("country").asText();
    String email = json.get("email").asText();
    String password = json.get("password").asText();
    // Check if user exists
    if (this.dataServiceUser.getUser(pseudo) != null) {
      return Response.status(Status.CONFLICT).entity("This pseudo is already in use")
          .type(MediaType.TEXT_PLAIN).build();
    }
    // response = checkExistingCredentialsInDB(json); if (response != null) return response;
    // create adress
    Adress adress = this.adressFactory.getAdress();
    adress.setID(1);
    adress.setStreet(street);
    adress.setBuildingNumber(houseNumber);
    adress.setUnitNumber(mailBoxNumber);
    adress.setPostCode(postalCode);
    adress.setCommune(commune);
    adress.setCountry(country);
    this.dataServiceAdress.addAdress(adress);
    // create user
    UserDTO userDTO = this.userFactory.getPublicUser();
    userDTO.setID(1);
    userDTO.setBoss(true);
    userDTO.setAntiqueDealer(true);
    userDTO.setLastName("Livi");
    userDTO.setFirstName("Satcho");
    userDTO.setUsername("Livi Satcho");
    userDTO.setAdressID(adress.getID());
    userDTO.setEmail("LiviSatcho@hotmzil.com");
    userDTO.setConfirmed(true);
    userDTO.setRegistrationDate("27-02-21");
    // userDTO.setPassword(userDTO.hashPassword("password"));
    // this.dataServiceUser.addUser(userDTO);

    // Create token
    String token;
    try {
      token = JWT.create().withIssuer("auth0").withClaim("user", userDTO.getID())
          .sign(this.jwtAlgorithm);
    } catch (Exception e) {
      throw new WebApplicationException("Unable to create token", e, Status.INTERNAL_SERVER_ERROR);
    }
    // Build response

    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    String publicSerializedUser = Json.serializePublicJsonView(userDTO);
    UserDTO publicUser = Json.filterPublicJsonView(userDTO, UserDTO.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  private Response checkNonNullCredentialsRegister(JsonNode json) {
    if (!json.hasNonNull("pseudo") && !json.hasNonNull("lastname") && !json.hasNonNull("firstname")
        && !json.hasNonNull("street") && !json.hasNonNull("houseNumber")
        && !json.hasNonNull("mailBoxNumber") && !json.hasNonNull("postalCode")
        && !json.hasNonNull("commune") && !json.hasNonNull("country") && !json.hasNonNull("email")
        && !json.hasNonNull("password") && !json.hasNonNull("passwordVerify")) {
      return Response.status(Status.UNAUTHORIZED)
          .entity("Pseudo, lastname, firstname, street, house number, mailbox number, postal code, "
              + "commune, country, email, password and password verify needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    // Tous les combinaisons à 11
    // Tous les combinaisons à 10
    // Tous les combinaisons à 9
    // Tous les combinaisons à 8
    // Tous les combinaisons à 7
    // Tous les combinaisons à 6
    // Tous les combinaisons à 5
    // Tous les combinaisons à 4
    // Tous les combinaisons à 3
    // Tous les combinaisons à 2
    // Tous les combinaisons à 1
    if (!json.get("password").asText().equals(json.get("passwordVerify").asText())) {
      return Response.status(Status.CONFLICT)
          .entity("Password and password verify must be the same").type(MediaType.TEXT_PLAIN)
          .build();
    }

    return null;
  }

  private Response checkExistingCredentialsInDB(JsonNode json) {
    String username = json.get("username").asText();
    String email = json.get("email").asText();
    if (this.dataServiceUser.getUser(username) != null) {
      return Response.status(Status.CONFLICT).entity("This pseudo is already in use")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (this.dataServiceUser.getUser(email) != null) {
      return Response.status(Status.CONFLICT).entity("This email is already in use")
          .type(MediaType.TEXT_PLAIN).build();
    }

    return null;
  }

}
