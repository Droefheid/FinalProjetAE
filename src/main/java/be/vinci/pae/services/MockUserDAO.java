package be.vinci.pae.services;

import be.vinci.pae.domaine.UserDTO;
import jakarta.inject.Inject;

public class MockUserDAO implements UserDAO {

  @Inject
  private UserDTO userDTO;
  
  @Override
  public UserDTO findByUserName(String username) {
    userDTO.setUserName(username);
    userDTO.setPassword("$2a$10$B0LtQqz9ERNwEHlOOjrsk.g7XZBPIJ4aCjQVBPa4QyNOKYkZ4V3hq"); //hi
    return userDTO;
  }

  @Override
  public UserDTO findById(int id) {
    userDTO.setID(id);
    return userDTO;
  }

}
