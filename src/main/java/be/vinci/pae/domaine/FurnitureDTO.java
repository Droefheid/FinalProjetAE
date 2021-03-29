package be.vinci.pae.domaine;

import java.sql.Timestamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {


  int getIdFurniture();

  void setIdFurniture(int idFurniture);

  int getType();

  void setType(int type);

  int getBuyer();

  void setBuyer(int buyer);

  String getFurnitureTitle();

  void setFurnitureTitle(String furnitureTitle);

  double getPurchasePrice();

  void setPurchasePrice(double purchasePrice);

  Timestamp getPickUpDate();

  void setPickUpDate(Timestamp pickUpDate);

  double getSellingPrice();

  void setSellingPrice(double sellingPrice);

  double getSpecialSalePrice();

  void setSpecialSalePrice(double specialSalePrice);

  int getDelivery();

  void setDelivery(int delivery);

  String getState();

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
