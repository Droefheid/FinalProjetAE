package be.vinci.pae.services;

import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.UserDTO;

public interface UserDAO {

  UserDTO findById(int id);

  UserDTO findByUserName(String username);

  UserDTO registerUser(String username, String email, String password, String lastName,
      String firstName, Adress adress);

  Adress registerAdress(Adress adress);

  int getAdressById(Adress adress);

}

