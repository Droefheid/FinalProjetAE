package be.vinci.pae.domaine;

import be.vinci.pae.services.MockUserDAO;
import jakarta.inject.Inject;

public class MockUCCImpl implements UserUCC {

  @Inject
  private MockUserDAO userDao;
  
  @Override
  public UserDTO login(String username, String password) {
    // Try to login
    User user = (User) this.userDao.findByUserName(username);
    if (user == null || !user.checkPassword(password)) {
      return null;
    }
    return (UserDTO) user;
  }

  @Override
  public UserDTO register(UserDTO userDTO) {
    // TODO Auto-generated method stub
    return null;
  }

}
