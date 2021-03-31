package be.vinci.pae.api;


import java.util.ArrayList;
import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.FurnitureDTO;
import be.vinci.pae.domaine.FurnitureUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/furnitures")
public class FurnitureResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private FurnitureUCC furnitureUcc;

  @Inject
  DomaineFactory domaineFactory;

  /**
   * get all furnitures.
   * 
   * @return list of all furnitures.
   */
  @GET
  @Path("/allFurnitures")
  public Response allFurnitures() {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    listFurnitures = furnitureUcc.getAll();

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("list", listFurnitures);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  /**
   * Get the furniture with an ID if exists or send error message.
   * 
   * @param id id of the furniture.
   * @return a furniture if furniture exists in database and matches the id.
   */
  @GET
  @Path("/{id}")
  public Response getFurnitureById(@PathParam("id") int id) {
    // Check credentials.
    if (id < 1) {
      throw new BusinessException("Id cannot be under 1", HttpStatus.BAD_REQUEST_400);
    }
    FurnitureDTO furniture = this.furnitureUcc.findById(id);

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniture", furniture);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }


}
