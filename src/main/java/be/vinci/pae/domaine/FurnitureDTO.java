package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {

  static enum STATES {
    ON_RESTORATION("ER"), ON_SHOP("M"), ON_SALE("EV"), UNDER_OPTION("O"), SOLD("V"), DELIVERY(
        "EL"), DELIVERED("L"), TO_TAKE_AWAY("AE"), TAKE_AWAY("E"), RESERVED("R"), WITHDRAW("RE");

    private String value;

    public String getValue() {
      return value;
    }

    private STATES(String value) {
      this.value = value;
    }
  };

  int getFurnitureId();

  void setFurnitureId(int idFurniture);

  int getType();

  void setType(int type);

  int getBuyer();

  void setBuyer(int buyer);

  String getFurnitureTitle();

  void setFurnitureTitle(String furnitureTitle);

  Timestamp getPickUpDate();

  void setPickUpDate(Timestamp pickUpDate);

  float getPurchasePrice();

  void setPurchasePrice(float purchasePrice);

  Timestamp getFurnitureDateCollection();

  void setFurnitureDateCollection(Timestamp furnitureDateCollection);

  float getSellingPrice();

  void setSellingPrice(float sellingPrice);

  float getSpecialSalePrice();

  void setSpecialSalePrice(float specialSalePrice);

  int getDelivery();

  void setDelivery(int delivery);

  String getState();

  /**
   * set the state of the furniture if it is correct.
   * 
   * @param state the new state of the furniture.
   */
  void setState(String state);

  Timestamp getDepositDate();

  void setDepositDate(Timestamp depositDate);

  Timestamp getDateOfSale();

  void setDateOfSale(Timestamp dateOfSale);

  Timestamp getSaleWithdrawalDate();

  void setSaleWithdrawalDate(Timestamp saleWithdrawalDate);

  int getSeller();

  void setSeller(int seller);

}
