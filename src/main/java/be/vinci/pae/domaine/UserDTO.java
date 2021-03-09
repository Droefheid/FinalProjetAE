package be.vinci.pae.domaine;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  int getID();

  /**
   * change the id of the user if is not already use in the DB
   * 
   * @param id : not already use in the DB
   */
  void setID(int id);

  boolean isBoss();

  void setBoss(boolean isBoss);

  boolean isAntiqueDealer();

  void setAntiqueDealer(boolean isAntiqueDealer);

  String getLastName();

  /**
   * 
   * @param lastName : as to be Not Null
   */
  void setLastName(String lastName);

  String getFirstName();

  /**
   * 
   * @param firstName : as to be Not Null
   */
  void setFirstName(String firstName);

  String getUserName();

  /**
   * change the pseudo of the user if is not already use in the DB
   * 
   * @param pseudo : not already use in the DB and as to be Not Null
   */
  void setUserName(String username);

  int getAdressID();

  void setAdressID(int adressID);

  String getEmail();

  /**
   * change the email of the user if is not already use in the DB
   * 
   * @param email : not already use in the DB and as to be Not Null
   */
  void setEmail(String email);

  boolean isConfirmed();

  void setConfirmed(boolean isConfirmed);

  // TODO change String for... idk a date...
  String getRegistrationDate();

  // TODO change String for... idk a date...
  void setRegistrationDate(String registrationDate);

  String getPassword();

  /**
   * 
   * @param hashPassword : as to be Not Null
   */
  void setPassword(String hashPassword);
}
