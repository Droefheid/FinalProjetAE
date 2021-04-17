package be.vinci.pae.api;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.option.OptionUCC;
import be.vinci.pae.domaine.user.UserDTO;
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
@Path("/options")
public class OptionResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  DomaineFactory domaineFactory;

  @Inject
  OptionUCC optionUCC;

  @Inject
  FurnitureUCC furnitureUCC;

  /**
   * introduce an option.
   * 
   * @return Response ok or error.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/introduceOption")
  @Authorize
  public Response introduceOption(@Context ContainerRequest request, JsonNode json) {
    if (json.get("optionTerm").asText().equals("")) {
      throw new PresentationException("You must enter a date", Status.BAD_REQUEST);
    }
    if (json.get("furnitureID").asText().equals("")) {
      throw new PresentationException("You must choose a furniture", Status.BAD_REQUEST);
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("User not found.", Status.BAD_REQUEST);
    }

    OptionDTO option = domaineFactory.getOptionDTO();
    LocalDateTime now = LocalDateTime.now();

    option.setBeginningOptionDate(Timestamp.valueOf(now));
    option.setCustomer(currentUser.getID());
    option.setFurniture(json.get("furnitureID").asInt());
    String term = json.get("optionTerm").asText();
    LocalDateTime optionTerm = LocalDateTime.parse(term);

    int d = optionTerm.getDayOfYear() - now.getDayOfYear();
    if (optionTerm.getYear() != now.getYear()) {
      throw new PresentationException("Furniture can't be reserved for more than 5 days");
    }
    if (d >= 5) {
      throw new PresentationException("Furniture can't be reserved for more than 5 days");
    }
    option.setOptionTerm(Timestamp.valueOf(optionTerm));
    optionUCC.introduceOption(option);

    return Response.ok().build();

  }

  /**
   * deletes an option.
   * 
   * @return Response ok or error.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response deleteOption(@Context ContainerRequest request, JsonNode json) {

    if (json.get("furnitureID").asText().equals("")) {
      throw new PresentationException("You must choose a furniture", Status.BAD_REQUEST);
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("User not found.", Status.BAD_REQUEST);
    }

    if (json.get("optionID").asInt() == 0) {
      throw new PresentationException("optionID is empty");
    }

    OptionDTO option = optionUCC.getOption(json.get("optionID").asInt());

    if (option.getCustomer() != currentUser.getID()) {
      throw new PresentationException("Furniture was not reserved by you");
    }

    furnitureUCC.findById(json.get("furnitureID").asInt());
    optionUCC.stopOption(option);
    return Response.ok(MediaType.APPLICATION_JSON).build();
  }

  /**
   * 
   * Get the option belonging to a user and furniture.
   * 
   * @param request .
   * @param id .
   * @return the option or throws an exception.
   */
  @GET
  @Path("/{id}")
  @Authorize
  public Response getOption(@Context ContainerRequest request, @PathParam("id") int id) {
    if (id < 1) {
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null) {
      throw new PresentationException("User not found.", Status.BAD_REQUEST);
    }

    FurnitureDTO furniture = furnitureUCC.findById(id);

    if (!furniture.getState().equals(FurnitureDTO.STATES.UNDER_OPTION.getValue())) {
      throw new PresentationException("There is no option currently on this furniture");
    }

    OptionDTO option = optionUCC.findOption(furniture.getFurnitureId(), currentUser.getID());
    ObjectNode node = jsonMapper.createObjectNode().putPOJO("option", option);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }
}
