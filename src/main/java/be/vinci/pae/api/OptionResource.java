package be.vinci.pae.api;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.OptionDTO;
import be.vinci.pae.domaine.OptionUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
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

  /**
   * introduire une option.
   * 
   * @return Response ok ou une erreur.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response introduceOption(JsonNode json) {
    // TODO verifier user
    if (json.get("optionTerm").asText().equals("")) {
      throw new PresentationException("You must enter a date", Status.BAD_REQUEST);
    }
    if (json.get("furnitureID").asText().equals("")) {
      throw new PresentationException("You must choose a furniture", Status.BAD_REQUEST);
    }

    OptionDTO option = domaineFactory.getOptionDTO();
    LocalDateTime now = LocalDateTime.now();
    option.setBeginningOptionDate(Timestamp.valueOf(now));
    option.setCustomer(json.get("userID").asInt());
    option.setFurniture(json.get("furnitureID").asInt());
    String term = json.get("optionTerm").asText();
    LocalDateTime optionTerm = LocalDateTime.parse(term);
    option.setOptionTerm(Timestamp.valueOf(optionTerm));
    optionUCC.introduceOption(option);

    return Response.ok(MediaType.APPLICATION_JSON).build();

  }


}
