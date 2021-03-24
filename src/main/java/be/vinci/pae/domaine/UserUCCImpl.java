package be.vinci.pae.domaine;

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
      return null;
    }
    return (UserDTO) user;
  }

  @Override
  public UserDTO register(UserDTO userDTO, Adress adress) {
    int adresseId = userDao.getAdressByInfo(adress.getStreet(), adress.getBuildingNumber(),
        adress.getCommune(), adress.getCountry());
    if (adresseId == -1) {
      adresseId = userDao.registerAdress(adress);
    }
    User user = (User) userDTO;
    user.setAdressID(adresseId);
    String password = user.hashPassword(userDTO.getPassword());
    user.setPassword(password);
    user = (User) userDao.registerUser(user);
    if (user == null) {
      return null;
    }
    return (UserDTO) user;
  }

}
