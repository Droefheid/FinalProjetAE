package be.vinci.pae.services;

import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.UserDTO;

public interface UserDAO {

  UserDTO findById(int id);

  UserDTO findByUserName(String username);

  UserDTO registerUser(UserDTO user);

  Adress registerAdress(Adress adress);

  Adress getAdressById(int adress);

  int getAdressByInfo(String street, String building_number, String commune, String country);

}

