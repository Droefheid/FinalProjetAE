package be.vinci.pae.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeBoss;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.api.utils.ResponseMaker;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.user.UserUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/users")
public class UserResource {

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
    if (json.get("username").asText().equals("") && json.get("password").asText().equals("")) {
      throw new PresentationException("Username and password needed", Status.BAD_REQUEST);
    }
    if (json.get("password").asText().equals("")) {
      throw new PresentationException("Password needed", Status.BAD_REQUEST);
    }


    UserDTO user = this.userUcc.login(json.get("username").asText(), json.get("password").asText());
    if (!user.isConfirmed()) {
      throw new PresentationException("This account hasn't been confirmed by an admin yet",
          Status.BAD_REQUEST);
    }
    return ResponseMaker.createResponseWithToken(user);
  }

  /**
   * Get the user with an ID if exists or send error message.
   * 
   * @param id id of the user.
   * @return a user if user exists in database and matches the id.
   */
  @GET
  @Path("/{id}")
  @AuthorizeBoss
  public Response getUserById(@PathParam("id") int id) {
    // Check credentials.
    if (id < 1) {
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }

    UserDTO user = this.userUcc.getUser(id);

    return ResponseMaker.createResponseWithToken(user);
  }

  /**
   * Get the user from an id in a token in header.
   * 
   * @param request header with the token.
   * @return a new token and the user.
   */
  @GET
  @Path("/me")
  @Authorize
  public Response getUser(@Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");

    if (currentUser == null) {
      throw new PresentationException("User not found", Status.BAD_REQUEST);
    }
    return ResponseMaker.createResponseWithToken(currentUser);
  }


  /**
   * get all users with a search request.
   * 
   * @return list of users searched.
   */
  @GET
  @Path("/search/{search}")
  @AuthorizeBoss
  public Response allSearchUser(@PathParam("search") String search) {
    List<UserDTO> listUsers = new ArrayList<UserDTO>();
    listUsers = userUcc.getAllSearchedUser(search);


    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("list", listUsers);
  }

  /**
   * register a user if correct parameters are sent.
   * 
   * @param json object containing user information and address.
   * @return ok if user has been inserted or an exception.
   */
  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    if (json.get("username").asText().equals("")) {
      throw new PresentationException("Username is needed ", Status.BAD_REQUEST);
    }
    if (json.get("email").asText().equals("")) {
      throw new PresentationException("email is needed", Status.BAD_REQUEST);
    }
    if (json.get("password").asText().equals("")) {
      throw new PresentationException("password is needed ", Status.BAD_REQUEST);
    }
    if (json.get("lastname").asText().equals("")) {
      throw new PresentationException("Lastname is needed ", Status.BAD_REQUEST);
    }
    if (json.get("firstname").asText().equals("")) {
      throw new PresentationException("Firstname is needed ", Status.BAD_REQUEST);
    }

    VisitResource.checkJsonAddress(json);

    UserDTO user = domaineFactory.getUserDTO();

    user.setUserName(json.get("username").asText());
    user.setFirstName(json.get("firstname").asText());
    user.setLastName(json.get("lastname").asText());
    user.setEmail(json.get("email").asText());
    user.setPassword(json.get("password").asText());

    AddressDTO addressDTO = domaineFactory.getAdressDTO();
    addressDTO = VisitResource.createFullFillAddress(addressDTO, -1,
        json.get("building_number").asText(), json.get("commune").asText(),
        json.get("postcode").asText(), json.get("street").asText(),
        json.get("unit_number").asText(), json.get("country").asText());
    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);
    user.setRegistrationDate(timestamp);
    userUcc.register(user, addressDTO);

    return Response.ok().build();
  }

  /**
   * get all users.
   * 
   * @return list of all users.
   */
  @GET
  @AuthorizeBoss
  public Response allUsers() {
    List<UserDTO> listUsers = new ArrayList<UserDTO>();
    listUsers = userUcc.getAll();

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("list", listUsers);
  }

  /**
   * get all users not confirmed.
   * 
   * @return list of all users not confirmed.
   */
  @GET
  @Path("/notConfirmed")
  @AuthorizeBoss
  public Response allUsersNotConfirmed() {
    List<UserDTO> listUsers = new ArrayList<UserDTO>();
    listUsers = userUcc.getAllConfirmed(false);

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("list", listUsers);
  }


  /**
   * update confirmation.
   * 
   * @param json object containing user information and address.
   * @return list of all users.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeBoss
  public Response updateConfirmed(JsonNode json) {
    UserDTO user = domaineFactory.getUserDTO();
    UserDTO currentUser = this.userUcc.getUser(json.get("userId").asInt());

    if (currentUser == null || currentUser.getUserName() == null) {
      throw new PresentationException("User not found", Status.BAD_REQUEST);
    }
    user.setID(currentUser.getID());
    boolean isBoss = json.get("isBoss").asBoolean();
    user.setBoss(isBoss);
    boolean confirmed = json.get("isConfirmed").asBoolean();
    user.setConfirmed(confirmed);
    boolean antiqueDealer = json.get("isAntiqueDealer").asBoolean();
    user.setAntiqueDealer(antiqueDealer);
    this.userUcc.updateConfirmed(user);
    return Response.ok(MediaType.APPLICATION_JSON).build();
  }

  /**
   * returns an address corresponding to an id.
   * 
   * @param id of the address
   * @return the address if it exists throws an exception otherwise.
   */
  @GET
  @Path("/getAddress/{id}")
  @AuthorizeBoss
  public Response getAddress(@PathParam("id") int id) {
    AddressDTO address = domaineFactory.getAdressDTO();

    if (id <= 0) {
      throw new PresentationException("id of address is incorrect");
    }

    address = userUcc.getAddressById(id);
    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("address", address);
  }

  /**
   * returns an address list corresponding if it belongs to you.
   * 
   * @param json object containing visits information.
   * @return the address if it exists throws an exception otherwise.
   */
  @POST
  @Path("/getVisitsAddress")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response getVisitAddress(JsonNode json, @Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("User not found", Status.BAD_REQUEST);
    }

    List<AddressDTO> address = new ArrayList<>();

    for (JsonNode jsonNode : json.get("visits").findValues("addressId")) {
      address.add(userUcc.getVisitAddress(jsonNode.asInt(), currentUser.getID()));
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("address", address);
  }

}
