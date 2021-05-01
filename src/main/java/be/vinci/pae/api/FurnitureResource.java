package be.vinci.pae.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeBoss;
import be.vinci.pae.api.utils.PresentationException;
import be.vinci.pae.api.utils.ResponseMaker;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.type.TypeUCC;
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
@Path("/furnitures")
public class FurnitureResource {

  @Inject
  private TypeUCC typeUCC;

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
  public Response allFurnitures() {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    listFurnitures = furnitureUCC.getAll();

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("list", listFurnitures);
  }


  /**
   * get a clients furniture.
   * 
   * @return list of all the clients furnitures.
   */
  @GET
  @Authorize
  @Path("myFurnitures")
  public Response myFurnitures(@Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null || !currentUser.isBoss()) {
      throw new PresentationException("You dont have the permission.", Status.BAD_REQUEST);
    }
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    listFurnitures = furnitureUCC.getMyFurniture(currentUser.getID());

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("list", listFurnitures);
  }

  /**
<<<<<<< HEAD
   * Add a furniture (title, purchase_price, state, seller, type, pick_up_date).
   * 
   * @return return Reponse.ok().build();
=======
   * Add a furniture. with attribute title, purchase_price, state, seller, type, pick_up_date).
   * 
   * @param json object containing all necessary information to add the furniture.
   * @return the furniture added.
>>>>>>> 5d9b166c6279a5ad9589fd0ba7f378eae37123d2
   */
  @POST
  @AuthorizeBoss
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addFurniture(JsonNode json) {

    // Check credentials

    if (json.get("title").asText().equals("")) {
      throw new PresentationException("Title cannot be empty", Status.BAD_REQUEST);
    }
    if (json.get("purchasePrice").asText().equals("") || json.get("purchasePrice").asInt() <= 0) {
      throw new PresentationException("Purchase Price is needed or incorrect.", Status.BAD_REQUEST);
    }
    if (json.get("state").asText().equals("")) {
      throw new PresentationException("State is needed.", Status.BAD_REQUEST);
    }
    if (json.get("seller").asText().equals("")) {
      throw new PresentationException("Seller is needed.", Status.BAD_REQUEST);
    }
    int sellerId = json.get("seller").asInt();
    if (sellerId < 1 || userRessource.getUserById(sellerId) == null) {
      throw new PresentationException("Seller does not exist.", Status.BAD_REQUEST);
    }
    if (json.get("type").asText().equals("") || json.get("type").asInt() <= 0) {
      throw new PresentationException("Type is incorrect or needed.", Status.BAD_REQUEST);
    }
    if (json.get("pickUpDate").asText().equals("")) {
      throw new PresentationException("Pick-up date is needed.", Status.BAD_REQUEST);
    }

    FurnitureDTO furnitureDTO = domaineFactory.getFurnitureDTO();

    furnitureDTO.setFurnitureTitle(json.get("title").asText());
    furnitureDTO.setPurchasePrice(json.get("purchasePrice").asInt());
    furnitureDTO.setState(json.get("state").asText());
    furnitureDTO.setType(json.get("type").asInt());
    furnitureDTO.setSeller(json.get("seller").asInt());

    // Transformation de la pick-up date en timestamp.
    String term = json.get("pickUpDate").asText();
    LocalDateTime optionTerm = LocalDateTime.parse(term);
    furnitureDTO.setPickUpDate(Timestamp.valueOf(optionTerm));

    // Si le meuble rentre en magasin, il doit avoir une date de dépot.
    if (furnitureDTO.getState().equals(FurnitureDTO.STATES.IN_SHOP.getValue())) {
      LocalDateTime dateNow = LocalDateTime.now();
      furnitureDTO.setDepositDate(Timestamp.valueOf(dateNow));
    }
    furnitureDTO = furnitureUCC.add(furnitureDTO);

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("furniture", furnitureDTO);
  }



  /**
   * update a furniture.
   * 
   * @param request contains headers.
   * @param json object containing all necessary information about the furniture.
   * @return the furniture updated.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeBoss
  public Response updateFurniture(@Context ContainerRequest request, JsonNode json) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    if (currentUser == null || !currentUser.isBoss()) {
      throw new PresentationException("You dont have the permission.", Status.BAD_REQUEST);
    }
    // System.out.println(json);
    // System.out.println(json.get("files").get(0));
    // System.out.println(json.get("formData").get("photo0"));
    // System.out.println(json.get("filesBase64").get(0));

    // TODO Verifier son etat.
    checkAllCredentialFurniture(json); // pourrais renvoyer le type si besoin en dessous.
    FurnitureDTO furniture = createFullFillFurniture(json);
    checkIfRespectStateDiagram(furniture);

    furniture = furnitureUCC.update(furniture);

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("furniture", furniture);
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
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }
    FurnitureDTO furniture = this.furnitureUCC.findById(id);

    return ResponseMaker.createResponseWithObjectNodeWith1PutPOJO("furniture", furniture);
  }

  /**
   * get the furniture by is id and all types and users.
   * 
   * @param id id of the furniture.
   * @return list of all types, users and the furniture where the id is the same.
   */
  @GET
  @Path("/infosUpdate/{id}")
  public Response allInfosForUpdateFurniture(@PathParam("id") int id) {
    if (id < 1) {
      throw new PresentationException("Id cannot be under 1", Status.BAD_REQUEST);
    }

    Object[] listOfAll = furnitureUCC.getAllInfosForUpdate(id);

    // Transform all URL into Base64 Image.
    for (PhotoDTO photo : (List<PhotoDTO>) listOfAll[3]) {
      String encodstring = PhotoResource.encodeFileToBase64Binary(photo.getPicture());
      photo.setPicture(encodstring);
    }

    int i = 0;
    return ResponseMaker.createResponseWithObjectNodeWith6PutPOJO("furniture", listOfAll[i++],
        "types", listOfAll[i++], "users", listOfAll[i++], "photos", listOfAll[i++],
        "photosFurnitures", listOfAll[i++], "option", listOfAll[i++]);
  }


  /**
   * get all types and users from DB.
   * 
   * 
   * @return list of all types, users to display them in add form.
   */
  @GET
  @Path("/infosAdd")
  public Response allInfosForAddFurniture() {
    Object[] listOfAll = furnitureUCC.getAllInfosForAdd();
    int i = 0;
    return ResponseMaker.createResponseWithObjectNodeWith2PutPOJO("types", listOfAll[i++], "users",
        listOfAll[i++]);
  }



  // ******************** Private's Methods ********************

  private void checkAllCredentialFurniture(JsonNode json) {
    // Required Field.
    if (json.get("furnitureId").asText().equals("") || json.get("furnitureId").asInt() < 1) {
      throw new PresentationException("Id is needed or incorrect.", Status.BAD_REQUEST);
    }
    if (json.get("title").asText().equals("")) {
      throw new PresentationException("Title is needed.", Status.BAD_REQUEST);
    }
    if (json.get("state").asText().equals("")) {
      throw new PresentationException("State is needed.", Status.BAD_REQUEST);
    }
    if (json.get("purchasePrice").asText().equals("") || json.get("purchasePrice").asInt() <= 0) {
      throw new PresentationException("Purchase Price is needed or incorrect.", Status.BAD_REQUEST);
    }
    if (json.get("seller").asText().equals("")) {
      throw new PresentationException("Seller is needed.", Status.BAD_REQUEST);
    }
    int sellerId = json.get("seller").asInt();
    if (sellerId < 1 || userRessource.getUserById(sellerId) == null) {
      throw new PresentationException("Seller does not exist.", Status.BAD_REQUEST);
    }
    if (json.get("pickUpDate").asText().equals("")) {
      throw new PresentationException("Pick-up date is needed.", Status.BAD_REQUEST);
    }
    checkTimestampPattern("Pick-up date", json.get("pickUpDate").asText());
    if (json.get("type").asText().equals("") || json.get("type").asInt() < 1) {
      throw new PresentationException("Type is needed ", Status.BAD_REQUEST);
    }
    this.typeUCC.findById(json.get("type").asInt());


    // Check when the furniture is in restoration.
    String state = json.get("state").asText();
    if (state.equals("ER") && !json.get("depositDate").asText().equals("")) {
      throw new PresentationException(
          "You cant have a deposit date if the state is in restoration.", Status.BAD_REQUEST);
    }


    // Check when the furniture is in the shop.
    if (json.get("depositDate").asText().equals("") && !state.equals("ER")) {
      throw new PresentationException("A deposit date is needed if is not anymore in restoration.",
          Status.BAD_REQUEST);
    }
    if (json.get("depositDate").asText() != null && !json.get("depositDate").asText().equals("")) {
      checkTimestampPattern("Deposite date", json.get("depositDate").asText());
    }


    // Check when the furniture is put up for sale.
    if ((json.get("sellingPrice").asText().equals("")
        || json.get("sellingPrice").asText().equals("0")) && !state.equals("ER")
        && !state.equals("M")) {
      throw new PresentationException(
          "Selling Price is needed if is not anymore in restoration or in shop.",
          Status.BAD_REQUEST);
    }
    if (!json.get("sellingPrice").asText().equals("")
        && !json.get("sellingPrice").asText().equals("0")
        && (state.equals("ER") || state.equals("M"))) {
      throw new PresentationException(
          "You cant have a selling price if the state is in restoration or in shop.",
          Status.BAD_REQUEST);
    }
    if (!json.get("sellingPrice").asText().equals("") && json.get("sellingPrice").asInt() < 0) {
      throw new PresentationException("You cant have a negative selling price.",
          Status.BAD_REQUEST);
    }


    // Check when the furniture is buy.
    if ((json.get("buyer").asText().equals("") || json.get("buyer").asText().equals("0"))
        && (state.equals("V") || state.equals("EL") || state.equals("L") || state.equals("AE")
            || state.equals("E") || state.equals("R"))) {
      throw new PresentationException("Buyer is needed ", Status.BAD_REQUEST);
    }
    int buyerId = json.get("buyer").asInt();
    if (buyerId != 0 && (buyerId < 1 || userRessource.getUserById(buyerId) == null)) {
      throw new PresentationException("Buyer does not exist ", Status.BAD_REQUEST);
    }
    if (!json.get("buyer").asText().equals("") && !json.get("buyer").asText().equals("0")
        && !state.equals("V") && !state.equals("EL") && !state.equals("L") && !state.equals("AE")
        && !state.equals("E") && !state.equals("R")) {
      throw new PresentationException(
          "You cant have a buyer if the state is not (sold, on delivery, "
              + "delivered, to go, taken away, reserved).",
          Status.BAD_REQUEST);
    }
    if (!json.get("buyer").asText().equals("") && !json.get("buyer").asText().equals("0")
        && json.get("dateOfSale").asText().equals("")) {
      throw new PresentationException("A date of sale is needed if a buyer is specify.",
          Status.BAD_REQUEST);
    }
    if (json.get("dateOfSale").asText() != null && !json.get("dateOfSale").asText().equals("")) {
      checkTimestampPattern("Date of sale", json.get("dateOfSale").asText());
    }
    if ((json.get("buyer").asText().equals("") || json.get("buyer").asText().equals("0"))
        && json.get("dateOfSale").asText() != null && !json.get("dateOfSale").asText().equals("")) {
      throw new PresentationException("You can't have a date of sale if a buyer is not specify.",
          Status.BAD_REQUEST);
    }
    // TODO Verifier que si il y a un buyer, il y a soit delivery/saleWithdrawalDate.

    // Case if delivery.
    if ((state.equals("EL") || state.equals("EL")) && (json.get("delivery").asText().equals("")
        || json.get("delivery").asText().equals("0"))) {
      throw new PresentationException(
          "Delivery is needed if the state is on delivery or delivered.", Status.BAD_REQUEST);
    }

    // Case if takeaway.
    if ((state.equals("AE") || state.equals("E"))
        && json.get("furnitureDateCollection").asText().equals("")) {
      throw new PresentationException(
          "Furniture date collection is needed if the state is to go or take away.",
          Status.BAD_REQUEST);
    }
    if (json.get("furnitureDateCollection").asText() != null
        && !json.get("furnitureDateCollection").asText().equals("")) {
      checkTimestampPattern("Furniture date collection",
          json.get("furnitureDateCollection").asText());
    }

    // Case if antique dealer.
    if (!json.get("specialSalePrice").asText().equals("")
        && json.get("specialSalePrice").asInt() < 0) {
      throw new PresentationException("You cant have a negative special sale price.",
          Status.BAD_REQUEST);
    }
    if (!json.get("specialSalePrice").asText().equals("")
        && !json.get("specialSalePrice").asText().equals("0") && !state.equals("V")
        && !state.equals("EL") && !state.equals("L") && !state.equals("AE") && !state.equals("E")
        && !state.equals("R")) {
      throw new PresentationException(
          "You cant have a special sale price if the state is not (sold, on delivery,"
              + " delivered, to go, taken away, reserved)",
          Status.BAD_REQUEST);
    }
    if (!json.get("specialSalePrice").asText().equals("")
        && !json.get("specialSalePrice").asText().equals("0")
        && (json.get("buyer").asText().equals("") || json.get("buyer").asText().equals("0"))) {
      throw new PresentationException("Buyer is needed if a special sale price is specify.",
          Status.BAD_REQUEST);
    }



    // Check if withdraw.
    if (json.get("saleWithdrawalDate").asText().equals("") && state.equals("RE")) {
      throw new PresentationException("Sale Withdrawal Date is needed if the state is withdraw.",
          Status.BAD_REQUEST);
    }
    if (!json.get("saleWithdrawalDate").asText().equals("") && !state.equals("RE")) {
      throw new PresentationException(
          "The state need to be withdraw if a sale withdrawal date is specify.",
          Status.BAD_REQUEST);
    }
    if (json.get("saleWithdrawalDate").asText() != null
        && !json.get("saleWithdrawalDate").asText().equals("")) {
      checkTimestampPattern("Sale withdrawal date", json.get("saleWithdrawalDate").asText());
    }

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
      timestamp =
          Timestamp.valueOf(json.get("furnitureDateCollection").asText().replaceFirst("T", " "));
      furniture.setFurnitureDateCollection(timestamp);
    }
    furniture.setSellingPrice(json.get("sellingPrice").asLong());
    furniture.setSpecialSalePrice(json.get("specialSalePrice").asLong());
    furniture.setDelivery(json.get("delivery").asInt());
    furniture.setState(json.get("state").asText());

    if (!json.get("depositDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("depositDate").asText().replaceFirst("T", " "));
      furniture.setDepositDate(timestamp);
    }

    if (!json.get("dateOfSale").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("dateOfSale").asText().replaceFirst("T", " "));
      furniture.setDateOfSale(timestamp);
    }

    if (!json.get("saleWithdrawalDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("saleWithdrawalDate").asText().replaceFirst("T", " "));
      furniture.setSaleWithdrawalDate(timestamp);
    }
    furniture.setSeller(json.get("seller").asInt());

    if (!json.get("pickUpDate").asText().equals("")) {
      timestamp = Timestamp.valueOf(json.get("pickUpDate").asText().replaceFirst("T", " "));
      furniture.setPickUpDate(timestamp);
    }

    return furniture;
  }

  private void checkTimestampPattern(String name, String toVerify) {
    toVerify = toVerify.replaceFirst("T", " ");
    String timestampPattern = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
    Pattern pattern = Pattern.compile(timestampPattern, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(toVerify);
    if (!matcher.find()) {
      throw new PresentationException(name + " is not matching a Timestamp pattern.",
          Status.BAD_REQUEST);
    }
  }

  private void checkIfRespectStateDiagram(FurnitureDTO furniture) {
    FurnitureDTO oldFurniture = this.furnitureUCC.findById(furniture.getFurnitureId());

    if (oldFurniture.getState().equals(furniture.getState())) {
      return;
    }

    String errorMsg = "You can't by pass the state diagram";

    // From on restoration.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.ON_RESTORATION.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.IN_SHOP.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }

    // From in shop.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.IN_SHOP.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.ON_SALE.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }

    // Check about antique dealer early buy.
    if ((oldFurniture.getState().equals(FurnitureDTO.STATES.ON_RESTORATION.getValue())
        || oldFurniture.getState().equals(FurnitureDTO.STATES.IN_SHOP.getValue()))
        && furniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())
        && furniture.getSpecialSalePrice() == 0) {
      throw new PresentationException(
          "You can only sold the furniture in restauration or in shop to a antique dealer.",
          Status.BAD_REQUEST);
    }

    // From on sale.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.ON_SALE.getValue())
        /* && !furniture.getState().equals(FurnitureDTO.STATES.UNDER_OPTION.getValue()) */
        && !furniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.WITHDRAW.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }

    // From under option.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.UNDER_OPTION.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.ON_SALE.getValue())
        && !furniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }

    // From sold.
    // If we are here (With the state sold) without be stop by the first if(), it's not normal.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.SOLD.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }

    // From withdraw.
    // If we are here (With the state withdraw) without be stop by the first if(), it's not normal.
    if (oldFurniture.getState().equals(FurnitureDTO.STATES.WITHDRAW.getValue())) {
      throw new PresentationException(errorMsg, Status.BAD_REQUEST);
    }
  }

}
