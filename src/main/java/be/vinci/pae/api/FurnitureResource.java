package be.vinci.pae.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.server.ContainerRequest;
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
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
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

  @Inject
  private UserResource userRessource;

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
  public Response updateFurniture(@Context ContainerRequest request, JsonNode json) {
    /*
     * UserDTO currentUser = (UserDTO) request.getProperty("user"); if (currentUser == null || !currentUser.isBoss()) { throw new
     * BusinessException("You dont have the permission.", HttpStatus.BAD_REQUEST_400); }
     */

    checkAllCredentialFurniture(json); // pourrais renvoyer le type si besoin en dessous.
    FurnitureDTO furniture = createFullFillFurniture(json);

    furniture = furnitureUCC.update(furniture);

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniture", furniture);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

  private void checkAllCredentialFurniture(JsonNode json) {
    // Required Field.
    if (json.get("furnitureId").asText().equals("") || json.get("furnitureId").asInt() < 1) {
      throw new BusinessException("Id is needed or incorrect.", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("title").asText().equals("")) {
      throw new BusinessException("Title is needed.", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("state").asText().equals("")) {
      throw new BusinessException("State is needed.", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("purchasePrice").asText().equals("") || json.get("purchasePrice").asInt() <= 0) {
      throw new BusinessException("Purchase Price is needed or inccorect.",
          HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("seller").asText().equals("")) {
      throw new BusinessException("Seller is needed.", HttpStatus.BAD_REQUEST_400);
    }
    int sellerId = json.get("seller").asInt();
    if (sellerId < 1 || userRessource.getUserById(sellerId) == null) {
      throw new BusinessException("Seller does not exist.", HttpStatus.BAD_REQUEST_400);
    }
    if (json.get("pickUpDate").asText().equals("")) {
      throw new BusinessException("Pick-up date is needed.", HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que furnitureDateCollection est bien un timestamp.
    if (json.get("type").asText().equals("") || json.get("type").asInt() < 1) {
      throw new BusinessException("Type is needed ", HttpStatus.BAD_REQUEST_400);
    }
    // TODO
    // int typeId = json.get("type").asInt();
    // if (typeId < 1 || getTypeById(typeId) == null) {
    // throw new BusinessException("Type does not exist ", HttpStatus.BAD_REQUEST_400);
    // }


    // Check when the furniture is in restoration.
    String state = json.get("state").asText();
    if (state.equals("ER") && !json.get("depositDate").asText().equals("")) {
      throw new BusinessException("You cant have a deposit date if the state is in restoration.",
          HttpStatus.BAD_REQUEST_400);
    }


    // Check when the furniture is in the shop.
    if (json.get("depositDate").asText().equals("") && !state.equals("ER")) {
      throw new BusinessException("A deposit date is needed if is not anymore in restoration.",
          HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que depositDate est bien un timestamp.


    // Check when the furniture is put up for sale.
    if ((json.get("sellingPrice").asText().equals("")
        || json.get("sellingPrice").asText().equals("0")) && !state.equals("ER")
        && !state.equals("M")) {
      throw new BusinessException(
          "Selling Price is needed if is not anymore in restoration or in shop.",
          HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("sellingPrice").asText().equals("")
        && !json.get("sellingPrice").asText().equals("0")
        && (state.equals("ER") || state.equals("M"))) {
      throw new BusinessException(
          "You cant have a selling price if the state is in restoration or in shop.",
          HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("sellingPrice").asText().equals("") && json.get("sellingPrice").asInt() < 0) {
      throw new BusinessException("You cant have a negative selling price.",
          HttpStatus.BAD_REQUEST_400);
    }


    // Check when the furniture is buy.
    if (json.get("buyer").asText().equals("") && (state.equals("V") || state.equals("EL")
        || state.equals("L") || state.equals("AE") || state.equals("E") || state.equals("R"))) {
      throw new BusinessException("Buyer is needed ", HttpStatus.BAD_REQUEST_400);
    }
    int buyerId = json.get("buyer").asInt();
    if (buyerId != 0 && (buyerId < 1 || userRessource.getUserById(buyerId) == null)) {
      throw new BusinessException("Buyer does not exist ", HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("buyer").asText().equals("") && !state.equals("V") && !state.equals("EL")
        && !state.equals("L") && !state.equals("AE") && !state.equals("E") && !state.equals("R")) {
      throw new BusinessException("You cant have a buyer if the state is not (sold, on delivery, "
          + "delivered, to go, taken away, reserved).", HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("buyer").asText().equals("") && json.get("dateOfSale").asText().equals("")) {
      throw new BusinessException("A date of sale is needed if a buyer is specify.",
          HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que dateOfSale est bien un timestamp.
    // TODO Verifier que si il y a un buyer, il y a soit delivery/saleWithdrawalDate.

    // Case if delivery.
    if ((state.equals("EL") || state.equals("EL")) && json.get("delivery").asText().equals("")) {
      throw new BusinessException("Delivery is needed if the state is on delivery or delivered.",
          HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que delivery est bien un timestamp.

    // Case if takeaway.
    if ((state.equals("AE") || state.equals("E"))
        && json.get("furnitureDateCollection").asText().equals("")) {
      throw new BusinessException(
          "Furniture date collection is needed if the state is to go or take away.",
          HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que furnitureDateCollection est bien un timestamp.

    // Case if antique dealer.
    if (!json.get("specialSalePrice").asText().equals("")
        && json.get("specialSalePrice").asInt() < 0) {
      throw new BusinessException("You cant have a negative special sale price.",
          HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("specialSalePrice").asText().equals("")
        && !json.get("specialSalePrice").asText().equals("0") && !state.equals("V")
        && !state.equals("EL") && !state.equals("L") && !state.equals("AE") && !state.equals("E")
        && !state.equals("R")) {
      throw new BusinessException(
          "You cant have a special sale price if the state is not (sold, on delivery,"
              + " delivered, to go, taken away, reserved)",
          HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("specialSalePrice").asText().equals("")
        && !json.get("specialSalePrice").asText().equals("0")
        && json.get("buyer").asText().equals("")) {
      throw new BusinessException("Buyer is needed if a special sale price is specify.",
          HttpStatus.BAD_REQUEST_400);
    }



    // Check if withdraw.
    if (json.get("saleWithdrawalDate").asText().equals("") && state.equals("RE")) {
      throw new BusinessException("Sale Withdrawal Date is needed if the state is withdraw.",
          HttpStatus.BAD_REQUEST_400);
    }
    if (!json.get("saleWithdrawalDate").asText().equals("") && !state.equals("RE")) {
      throw new BusinessException(
          "The state need to be withdraw if a sale withdrawal date is specify.",
          HttpStatus.BAD_REQUEST_400);
    }
    // TODO Verifier que saleWithdrawalDate est bien un timestamp.
  }

  private FurnitureDTO createFullFillFurniture(JsonNode json) {
    FurnitureDTO furniture = domaineFactory.getFurnitureDTO();

    furniture.setFurnitureId(json.get("furnitureId").asInt());
    furniture.setFurnitureTitle(json.get("title").asText());
    furniture.setType(json.get("type").asInt());
    furniture.setBuyer(json.get("buyer").asInt());
    furniture.setPurchasePrice(json.get("purchasePrice").asLong());

    Timestamp timestamp;
    if (!json.get("furnitureDateCollection").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("furnitureDateCollection").asText());
      furniture.setFurnitureDateCollection(timestamp);
    }
    furniture.setSellingPrice(json.get("sellingPrice").asLong());
    furniture.setSpecialSalePrice(json.get("specialSalePrice").asLong());
    furniture.setDelivery(json.get("delivery").asInt());
    furniture.setState(json.get("state").asText());

    if (!json.get("depositDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("depositDate").asText());
      furniture.setDepositDate(timestamp);
    }

    if (!json.get("dateOfSale").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("dateOfSale").asText());
      furniture.setDateOfSale(timestamp);
    }

    if (!json.get("saleWithdrawalDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("saleWithdrawalDate").asText());
      furniture.setSaleWithdrawalDate(timestamp);
    }
    furniture.setSeller(json.get("seller").asInt());

    if (!json.get("pickUpDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("pickUpDate").asText());
      furniture.setPickUpDate(timestamp);
    }

    return furniture;
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
    FurnitureDTO furniture = this.furnitureUCC.findById(id);

    ObjectNode node = jsonMapper.createObjectNode().putPOJO("furniture", furniture);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }
}
