package be.vinci.pae.services;

import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import jakarta.inject.Inject;

public class MockUserDAO implements UserDAO {

  @Inject
  private UserFactory user;

  /**
   * password = 123
   */
  @Override
  public UserDTO findByUserName(String username) {
    if (!username.equals("root"))
      return null;
    UserDTO userDTO = user.getUserDTO();
    userDTO.setUserName(username);
    userDTO.setPassword("$2a$10$9wCIFfvCj7CxhU2rA3DYOeZK6ZpugxZ4gDHCUxxrX9cUE/UK5pHSa");
    return userDTO;
  }

  @Override
  public UserDTO findById(int id) {
    user.getUserDTO().setID(id);
    return user.getUserDTO();
  }

}
