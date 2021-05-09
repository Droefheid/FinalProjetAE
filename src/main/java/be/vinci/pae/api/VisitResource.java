package be.vinci.pae.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeBoss;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.api.utils.ResponseMaker;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
@Path("/visits")
public class VisitResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();


  @Inject
  private VisitUCC visitUcc;

  @Inject
  private UserUCC userUcc;

  @Inject
  private DomaineFactory domaineFactory;


  /**
   * Get the visit with an ID if exists or send error message.
   * 
   * @param id id of the visit.
   * @return a visit if visit exists in database and matches the id.
   */
  @GET
  @Path("/{id}")
  @AuthorizeBoss
  public Response getVisitById(@PathParam("id") int id) {
    // Check credentials.
    if (id < 1) {
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }

    Object[] listOfAll = this.visitUcc.getAllInfosOfVisit(id);
    PhotoResource.transformAllURLOfThePhotosIntoBase64Image((List<PhotoDTO>) listOfAll[1]);

    int i = 0;
    return ResponseMaker.createResponseWithObjectNodeWith3PutPOJO("visit", listOfAll[i++], "photos",
        listOfAll[i++], "photosVisit", listOfAll[i++]);
  }

  /**
   * introduce visit if correct parameters are sent.
   * 
   * @param json object containing user information and address.
   * @return ok if user has been inserted or an exception.
   */
  @POST
  @Path("/introduceVisits")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response introduceVisit(@Context ContainerRequest request, JsonNode json) {
    if (!json.hasNonNull("request_date") || json.get("request_date").asText().equals("")) {
      throw new PresentationException("Request date is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("explanatory_note") || json.get("explanatory_note").asText().equals("")) {
      throw new PresentationException("explanatory note is needed", Status.BAD_REQUEST);
    }
    checkJsonAddress(json);

    if (!json.hasNonNull("time_slot") || json.get("time_slot").asText().equals("")) {
      throw new PresentationException("time slot is needed ", Status.BAD_REQUEST);
    }
    VisitDTO visit = domaineFactory.getVisitDTO();

    String term = json.get("request_date").asText();
    LocalDateTime parsed = LocalDateTime.parse(term);
    visit.setRequestDate(Timestamp.valueOf(parsed));

    visit.setExplanatoryNote(json.get("explanatory_note").asText());
    visit.setTimeSlot(json.get("time_slot").asText());
    visit.setLabelFurniture(json.get("label_furniture").asText());

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("You dont have the permission.", Status.BAD_REQUEST);
    }

    AddressDTO addressDTO = domaineFactory.getAdressDTO();

    addressDTO.setBuildingNumber(json.get("building_number").asText());
    addressDTO.setCommune(json.get("commune").asText());
    addressDTO.setPostCode(json.get("postcode").asText());
    addressDTO.setStreet(json.get("street").asText());
    addressDTO.setUnitNumber(json.get("unit_number").asText());
    addressDTO.setCountry(json.get("country").asText());


    if (currentUser.isBoss()) {
      int id = json.get("user_id").asInt();
      if (id < 1 || userUcc.getUser(id) == null) {
        throw new PresentationException("User doesn't exist.", Status.BAD_REQUEST);
      }
      visit = visitUcc.introduceVisit(visit, addressDTO, id);
    } else {
      visit = visitUcc.introduceVisit(visit, addressDTO, currentUser.getID());
    }

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("visit", visit);
  }

  /**
   * get all visits.
   * 
   * @return list of all visits.
   */
  @GET
  @AuthorizeBoss
  public Response allVisits() {
    List<VisitDTO> listVisits = new ArrayList<VisitDTO>();
    listVisits = visitUcc.getAll();

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("list", listVisits);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

  /**
   * get all visits not confirmed.
   * 
   * @return list of all visits not confirmed with list of users.
   */
  @GET
  @Path("/notConfirmed")
  @AuthorizeBoss
  public Response allVisitsNotConfirmed() {
    List<VisitDTO> listVisits = new ArrayList<VisitDTO>();
    listVisits = visitUcc.getAllNotConfirmed();
    List<UserDTO> listUser = new ArrayList<UserDTO>();
    for (VisitDTO visit : listVisits) {
      listUser.add(userUcc.getUser(visit.getUserId()));
    }
    return ResponseMaker.createResponseWithObjectNodeWith2PutPOJO("visits", listVisits, "users",
        listUser);
  }

  /**
   * get all visits.
   * 
   * @return list of all visits.
   */
  @GET
  @Path("/confirmed")
  @AuthorizeBoss
  public Response allVisitsConfirmed() {
    List<VisitDTO> listVisits = new ArrayList<VisitDTO>();
    listVisits = visitUcc.getAllConfirmed();
    List<UserDTO> listUser = new ArrayList<UserDTO>();
    for (VisitDTO visit : listVisits) {
      listUser.add(userUcc.getUser(visit.getUserId()));
    }
    return ResponseMaker.createResponseWithObjectNodeWith2PutPOJO("visits", listVisits, "users",
        listUser);
  }

  /**
   * update confirmation.
   * 
   * @return list of all users.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeBoss
  public Response updateConfirmed(@Context ContainerRequest request, JsonNode json) {
    if (!json.hasNonNull("visit_id") || json.get("visit_id").asText().equals("")) {
      throw new PresentationException("Visit id is needed ", Status.BAD_REQUEST);
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");

    if (currentUser == null) {
      throw new PresentationException("User not found", Status.BAD_REQUEST);
    }
    VisitDTO visit = domaineFactory.getVisitDTO();
    visit = visitUcc.getVisit(json.get("visit_id").asInt());
    visit.setIsConfirmed(true);
    this.visitUcc.updateConfirmed(visit);
    return Response.ok().build();
  }

  @DELETE
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response delete(@PathParam("id") int id, @Context ContainerRequest request) {
    if (id < 1) {
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("User not found", Status.BAD_REQUEST);
    }

    VisitDTO visit = visitUcc.getVisit(id);
    if (visit == null || visit.getUserId() != currentUser.getID()) {
      throw new PresentationException("You can't delete this visit.", Status.BAD_REQUEST);
    }

    visitUcc.delete(id);
    return Response.ok().build();
  }



  // ******************** Public static's Methods ********************

  /**
   * Verify json to check address variables.
   * 
   * @param json node with required objects.
   */
  public static void checkJsonAddress(JsonNode json) {
    if (!json.hasNonNull("street") || json.get("street").asText().equals("")) {
      throw new PresentationException("street is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("building_number") || json.get("building_number").asText().equals("")) {
      throw new PresentationException("building number is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("postcode") || json.get("postcode").asText().equals("")) {
      throw new PresentationException("postcode is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("commune") || json.get("commune").asText().equals("")) {
      throw new PresentationException("commune is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("country") || json.get("country").asText().equals("")) {
      throw new PresentationException("country is needed ", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("unit_number") || json.get("unit_number").asText().equals("")) {
      throw new PresentationException("unit number is needed ", Status.BAD_REQUEST);
    }
  }

}
