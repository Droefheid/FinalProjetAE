package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {


  int getFurnitureId();

  void setFurnitureId(int idFurniture);

  int getType();

  void setType(int type);

  int getBuyer();

  void setBuyer(int buyer);

  String getFurnitureTitle();

  void setFurnitureTitle(String furnitureTitle);

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
   * set the state of the furniture.
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
