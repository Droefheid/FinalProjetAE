package be.vinci.pae.domaine;

import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDao;

  @Override
  public UserDTO login(String username, String password) {

    // Try to login
    User user = (User) this.userDao.findByUserName(username);
    if (user == null || !user.checkPassword(password)) {
      throw new BusinessException("Username or password incorrect", HttpStatus.BAD_REQUEST_400);
    }
    return (UserDTO) user;
  }

  @Override
  public UserDTO register(UserDTO userDTO, Address address) {
    int adresseId = userDao.getAddressByInfo(address.getStreet(), address.getBuildingNumber(),
        address.getCommune(), address.getCountry());
    if (adresseId == -1) {
      adresseId = userDao.registerAddress(address);
    }
    User user = (User) userDTO;
    user.setAdressID(adresseId);
    String password = user.hashPassword(userDTO.getPassword());
    user.setPassword(password);
    user = (User) userDao.registerUser(user);
    if (user == null) {
      throw new BusinessException("User already exists", HttpStatus.BAD_REQUEST_400);
    }
    return (UserDTO) user;
  }

  @Override
  public UserDTO getUser(int id) {
    User user = (User) this.userDao.findById(id);
    if (user == null) {
      throw new BusinessException("User doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    return (UserDTO) user;
  }

}
