package be.vinci.pae.services;

import be.vinci.pae.domaine.UserDTO;

public interface UserDAO {

  UserDTO findById(int id);

  UserDTO findByUserName(String username);
}
