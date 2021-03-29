package be.vinci.pae.domaine;

import java.sql.Timestamp;
import java.util.Arrays;

public class FurnitureImpl implements FurnitureDTO {

  private int idFurniture;
  private int type;
  private int buyer;
  private String furnitureTitle;
  private double purchasePrice;
  private Timestamp pickUpDate;
  private double sellingPrice;
  private double specialSalePrice;
  private int delivery;
  private static final String[] STATES = {"R", "V", "A", "O", "RE", "VE"};
  private String state;
  private Timestamp depositDate;
  private Timestamp dateOfSale;
  private Timestamp saleWithdrawalDate;
  private int seller;


  public int getIdFurniture() {
    return idFurniture;
  }

  public void setIdFurniture(int idFurniture) {
    this.idFurniture = idFurniture;
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

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public Timestamp getPickUpDate() {
    return pickUpDate;
  }

  public void setPickUpDate(Timestamp pickUpDate) {
    this.pickUpDate = pickUpDate;
  }

  public double getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(double sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public double getSpecialSalePrice() {
    return specialSalePrice;
  }

  public void setSpecialSalePrice(double specialSalePrice) {
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
