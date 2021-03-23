package be.vinci.pae.services;

import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.UserDTO;

public interface UserDAO {

  UserDTO findById(int id);

  UserDTO findByUserName(String username);

  UserDTO registerUser(UserDTO user);

  /**
   * insert an address into the DB.
   * 
   * @param adress
   * @return the id of the address or -1 if address already exists.
   */
  int registerAdress(Adress adress);

  /**
   * Fetch an adress with the address_id.
   * 
   * @param adress id.
   * @return the address that is linked to the parameter id or null if inexistant.
   */
  Adress getAdressById(int adress);

  /**
   * fetch an address with the corresponding information.
   * 
   * @param street
   * @param building_number
   * @param commune
   * @param country
   * @return the address id or -1 if the address doesn't exists.
   */
  int getAdressByInfo(String street, String building_number, String commune, String country);

}

