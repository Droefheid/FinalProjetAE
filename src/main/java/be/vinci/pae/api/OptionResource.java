package be.vinci.pae.api;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.option.OptionUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/options")
public class OptionResource {

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
  public Response introduceOption(JsonNode json) {
    if (json.get("optionTerm").asText().equals("")) {
      throw new PresentationException("You must enter a date", Status.BAD_REQUEST);
    }
    if (json.get("furnitureID").asText().equals("")) {
      throw new PresentationException("You must choose a furniture", Status.BAD_REQUEST);
    }
    if (json.get("userID").asText().equals("")) {
      throw new PresentationException("UserID is empty");
    }

    OptionDTO option = domaineFactory.getOptionDTO();
    LocalDateTime now = LocalDateTime.now();

    option.setBeginningOptionDate(Timestamp.valueOf(now));
    option.setCustomer(json.get("userID").asInt());
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

    return Response.ok(MediaType.APPLICATION_JSON).build();

  }

  /**
   * deletes an option.
   * 
   * @return Response ok or error.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response deleteOption(JsonNode json) {

    if (json.get("furnitureID").asText().equals("")) {
      throw new PresentationException("You must choose a furniture", Status.BAD_REQUEST);
    }
    if (json.get("userID").asText().equals("")) {
      throw new PresentationException("UserID is empty");
    }
    if (json.get("optionID").asInt() == 0) {
      throw new PresentationException("optionID is empty");
    }

    OptionDTO option = optionUCC.getOption(json.get("optionID").asInt());

    if (option.getCustomer() != json.get("userID").asInt()) {
      throw new PresentationException("Furniture was not reserved by you");
    }

    furnitureUCC.findById(json.get("furnitureID").asInt());
    optionUCC.stopOption(option);
    return Response.ok(MediaType.APPLICATION_JSON).build();
  }
}
