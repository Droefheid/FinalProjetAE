package be.vinci.pae.services;

import be.vinci.pae.domaine.Address;
import be.vinci.pae.domaine.UserDTO;

public interface UserDAO {

  UserDTO findById(int id);

  UserDTO findByUserName(String username);

  UserDTO registerUser(UserDTO user);

  /**
   * insert an address into the DB.
   * 
   * @param address to be inserted.
   * @return the id of the address or -1 if address already exists.
   */
  int registerAddress(Address address);

  /**
   * Fetch an adress with the address_id.
   * 
   * @param address id.
   * @return the address that is linked to the parameter id or null if inexistant.
   */
  Address getAddressById(int address);

  /**
   * fetch an address with the corresponding information.
   * 
   * @param street name.
   * @param buildingNumber the number of the building.
   * @param commune the name of the commune.
   * @param country name of the country.
   * @return the address id or -1 if the address doesn't exists.
   */
  int getAddressByInfo(String street, String buildingNumber, String commune, String country);

}

