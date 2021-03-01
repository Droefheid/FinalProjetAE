package be.vinci.pae.domaine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = AdressImpl.class)
public interface Adress {
  int getID();

  /**
   * change the id of the user if is not already use in the DB
   * 
   * @param id : not already use in the DB
   */
  void setID(int id);

  String getStreet();

  /**
   * 
   * @param street : as to be Not Null
   */
  void setStreet(String street);

  int getBuildingNumber();

  void setBuildingNumber(int buildingNumber);

  int getUnitNumber();

  void setUnitNumber(int unitNumber);

  int getPostCode();

  void setPostCode(int postCode);

  String getCommune();

  /**
   * 
   * @param commune : as to be Not Null
   */
  void setCommune(String commune);

  String getCountry();

  /**
   * 
   * @param country : as to be Not Null
   */
  void setCountry(String country);
}
