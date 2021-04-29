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
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

    VisitDTO visit = this.visitUcc.getVisit(id);
    return createResponseWithObjectNodeWith1PutPOJO("visit", visit);
  }


  /**
   * create a response with a ObjectNode with 1 putPOJO.
   * 
   * @param <E> the type of the object.
   * @param namePOJO the name of the POJO put.
   * @param object object to put.
   * @return a response.ok build with the ObjectNode inside.
   */
  private <E> Response createResponseWithObjectNodeWith1PutPOJO(String namePOJO, E object) {
    ObjectNode node = jsonMapper.createObjectNode().putPOJO(namePOJO, object);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
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
    if (json.get("request_date").asText().equals("")) {
      throw new PresentationException("Request date is needed ", Status.BAD_REQUEST);
    }
    if (json.get("explanatory_note").asText().equals("")) {
      throw new PresentationException("explanatory note is needed", Status.BAD_REQUEST);
    }
    if (json.get("street").asText().equals("")) {
      throw new PresentationException("street is needed ", Status.BAD_REQUEST);
    }
    if (json.get("building_number").asText().equals("")) {
      throw new PresentationException("building number is needed ", Status.BAD_REQUEST);
    }
    if (json.get("postcode").asText().equals("")) {
      throw new PresentationException("postcode is needed ", Status.BAD_REQUEST);
    }
    if (json.get("commune").asText().equals("")) {
      throw new PresentationException("commune is needed ", Status.BAD_REQUEST);
    }
    if (json.get("country").asText().equals("")) {
      throw new PresentationException("country is needed ", Status.BAD_REQUEST);
    }
    if (json.get("unit_number").asText().equals("")) {
      throw new PresentationException("unit number is needed ", Status.BAD_REQUEST);
    }

    if (json.get("time_slot").asText().equals("")) {
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
    if (currentUser == null || !currentUser.isBoss()) {
      throw new PresentationException("You dont have the permission.", Status.BAD_REQUEST);
    }

    AddressDTO addressDTO = domaineFactory.getAdressDTO();

    addressDTO.setBuildingNumber(json.get("building_number").asText());
    addressDTO.setCommune(json.get("commune").asText());
    addressDTO.setPostCode(json.get("postcode").asText());
    addressDTO.setStreet(json.get("street").asText());
    addressDTO.setUnitNumber(json.get("unit_number").asText());
    addressDTO.setCountry(json.get("country").asText());



    visitUcc.introduceVisit(visit, addressDTO, currentUser);

    return Response.ok().build();
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
   * get all visits.
   * 
   * @return list of all visits.
   */
  @GET
  @Path("/notConfirmed")
  @AuthorizeBoss
  public Response allVisitsConfirmed() {
    List<VisitDTO> listVisits = new ArrayList<VisitDTO>();
    listVisits = visitUcc.getAllNotConfirmed();

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("list", listVisits);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }
}
