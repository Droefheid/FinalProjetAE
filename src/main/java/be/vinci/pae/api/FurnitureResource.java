package be.vinci.pae.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.FurnitureDTO;
import be.vinci.pae.domaine.FurnitureUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/furnitures")
public class FurnitureResource {

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private DomaineFactory domaineFactory;

  /**
   * get all furnitures.
   * 
   * @return list of all furnitures.
   */
  @GET
  @Path("/allFurnitures")
  public Response allFurnitures() {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    listFurnitures = furnitureUCC.getAll();

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("list", listFurnitures);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

  /**
   * update a furniture.
   * 
   * @return the furniture updated.
   */
  @POST
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateFurniture(JsonNode json) {

    checkAllCredentialFurniture(json); // pourrais renvoyer le type si besoin en dessous.
    FurnitureDTO furniture = createFullFillFurniture(json);


    furniture = furnitureUCC.update(furniture);

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniture", furniture);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

  private void checkAllCredentialFurniture(JsonNode json) {
    // Required Field.
    if (json.get("title").asText().equals("")) {
      throw new BusinessException("Title is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("type").asText().equals("")) {
      throw new BusinessException("Type is needed ", HttpStatus.BAD_REQUEST_400);
    }
    // Appel a type.findByID() ? pour verifier les autres champs.
    if (json.get("buyer").asText().equals("")) {
      throw new BusinessException("Buyer is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("purchasePrice").asText().equals("")) {
      throw new BusinessException("Purchase Price is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("furnitureDateCollection").asText().equals("")) {
      throw new BusinessException("Furniture Date Collection is needed ",
          HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("sellingPrice").asText().equals("")) {
      throw new BusinessException("Selling Price is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("specialSalePrice").asText().equals("")) {
      throw new BusinessException("Special Sale Price is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("delivery").asText().equals("")) {
      throw new BusinessException("Delivery is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("state").asText().equals("")) {
      throw new BusinessException("State is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("depositDate").asText().equals("")) {
      throw new BusinessException("Deposit Date is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("dateOfSale").asText().equals("")) {
      throw new BusinessException("Date Of Sale is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("saleWithdrawalDate").asText().equals("")) {
      throw new BusinessException("Sale With drawal Date is needed ", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("seller").asText().equals("")) {
      throw new BusinessException("Seller is needed ", HttpStatus.BAD_REQUEST_400);
    }
  }

  private FurnitureDTO createFullFillFurniture(JsonNode json) {
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();

    furniture.setFurnitureTitle(json.get("title").asText());
    furniture.setType(json.get("type").asInt());
    furniture.setBuyer(json.get("buyer").asInt());
    furniture.setPurchasePrice(json.get("purchasePrice").asLong());

    Timestamp timestamp = Timestamp.valueOf(json.get("furnitureDateCollection").asText());
    furniture.setFurnitureDateCollection(timestamp);
    furniture.setSellingPrice(json.get("sellingPrice").asLong());
    furniture.setSpecialSalePrice(json.get("specialSalePrice").asLong());
    furniture.setDelivery(json.get("delivery").asInt());
    furniture.setState(json.get("state").asText());

    timestamp = Timestamp.valueOf(json.get("depositDate").asText());
    furniture.setDepositDate(timestamp);

    timestamp = Timestamp.valueOf(json.get("dateOfSale").asText());
    furniture.setDateOfSale(timestamp);

    timestamp = Timestamp.valueOf(json.get("saleWithdrawalDate").asText());
    furniture.setSaleWithdrawalDate(timestamp);
    furniture.setSeller(json.get("seller").asInt());

    return furniture;
  }

}
