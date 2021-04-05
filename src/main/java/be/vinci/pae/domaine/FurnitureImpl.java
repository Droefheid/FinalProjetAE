package be.vinci.pae.domaine;

import java.sql.Timestamp;
import be.vinci.pae.api.utils.BusinessException;
import jakarta.ws.rs.core.Response.Status;

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
    for (STATES s : FurnitureDTO.STATES.values()) {
      if (s.getValue().equals(state)) {
        this.state = state;
        return;
      }
    }
    throw new BusinessException("State does not exist ", Status.BAD_REQUEST);
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

  @Override
  public String toString() {
    return "FurnitureImpl [furnitureId=" + furnitureId + ", type=" + type + ", buyer=" + buyer
        + ", furnitureTitle=" + furnitureTitle + ", purchasePrice=" + purchasePrice
        + ", furnitureDateCollection=" + furnitureDateCollection + ", sellingPrice=" + sellingPrice
        + "\n, specialSalePrice=" + specialSalePrice + ", delivery=" + delivery + ", state=" + state
        + ", depositDate=" + depositDate + ", dateOfSale=" + dateOfSale + ", saleWithdrawalDate="
        + saleWithdrawalDate + ", seller=" + seller + "\n, pickUpDate=" + pickUpDate + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + furnitureId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    FurnitureImpl other = (FurnitureImpl) obj;
    if (furnitureId != other.furnitureId) {
      return false;
    }
    return true;
  }

}
