package be.vinci.pae.domaine;

import java.sql.Timestamp;
import java.util.Arrays;
import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;

public class FurnitureImpl implements FurnitureDTO {

  private int furnitureId;
  private int type;
  private int buyer;
  private String furnitureTitle;
  private float purchasePrice;
  private Timestamp furnitureDateCollection;
  private float sellingPrice;
  private float specialSalePrice;
  private int delivery;
  private static final String[] STATES =
      {"ER", "M", "EV", "O", "V", "EL", "L", "AE", "E", "R", "RE"};
  private String state;
  private Timestamp depositDate;
  private Timestamp dateOfSale;
  private Timestamp saleWithdrawalDate;
  private int seller;
  private Timestamp pickUpDate;


  public int getFurnitureId() {
    return furnitureId;
  }

  public void setFurnitureId(int furnitureId) {
    this.furnitureId = furnitureId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getBuyer() {
    return buyer;
  }

  public void setBuyer(int buyer) {
    this.buyer = buyer;
  }

  public String getFurnitureTitle() {
    return furnitureTitle;
  }

  public void setFurnitureTitle(String furnitureTitle) {
    this.furnitureTitle = furnitureTitle;
  }

  public Timestamp getPickUpDate() {
    return pickUpDate;
  }

  public void setPickUpDate(Timestamp pickUpDate) {
    this.pickUpDate = pickUpDate;
  }

  public float getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(float purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public Timestamp getFurnitureDateCollection() {
    return furnitureDateCollection;
  }

  public void setFurnitureDateCollection(Timestamp furnitureDateCollection) {
    this.furnitureDateCollection = furnitureDateCollection;
  }

  public float getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(float sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public float getSpecialSalePrice() {
    return specialSalePrice;
  }

  public void setSpecialSalePrice(float specialSalePrice) {
    this.specialSalePrice = specialSalePrice;
  }

  public int getDelivery() {
    return delivery;
  }

  public void setDelivery(int delivery) {
    this.delivery = delivery;
  }

  public String getState() {
    return state;
  }

  /**
   * set the state of the furniture.
   * 
   * @param state the new state of the furniture.
   */
  public void setState(String state) {
    if (Arrays.asList(STATES).contains(state)) {
      this.state = state;
    } else {
      throw new BusinessException("State does not exist ", HttpStatus.BAD_REQUEST_400);
    }
  }

  public Timestamp getDepositDate() {
    return depositDate;
  }

  public void setDepositDate(Timestamp depositDate) {
    this.depositDate = depositDate;
  }

  public Timestamp getDateOfSale() {
    return dateOfSale;
  }

  public void setDateOfSale(Timestamp dateOfSale) {
    this.dateOfSale = dateOfSale;
  }

  public Timestamp getSaleWithdrawalDate() {
    return saleWithdrawalDate;
  }

  public void setSaleWithdrawalDate(Timestamp saleWithdrawalDate) {
    this.saleWithdrawalDate = saleWithdrawalDate;
  }

  public int getSeller() {
    return seller;
  }

  public void setSeller(int seller) {
    this.seller = seller;
  }

}
