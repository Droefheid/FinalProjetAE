package be.vinci.pae.domaine;

import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO userDao;

  @Inject
  private DalServices dalservices;

  @Override
  public UserDTO login(String username, String password) {
    dalservices.startTransaction();
    User user = (User) this.userDao.findByUserName(username);
    if (user == null || !user.checkPassword(password)) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Username or password incorrect", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return (UserDTO) user;
  }

  @Override
  public UserDTO register(UserDTO userDTO, AddressDTO addressDTO) {
    dalservices.startTransaction();
    int adresseId = userDao.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry());
    if (adresseId == -1) {
      adresseId = userDao.registerAddress(addressDTO);
    }
    User user = (User) userDTO;
    user.setAdressID(adresseId);
    String password = user.hashPassword(userDTO.getPassword());
    user.setPassword(password);
    user = (User) userDao.registerUser(user);
    if (user == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("User already exists", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return (UserDTO) user;
  }

  @Override
  public UserDTO getUser(int id) {
    dalservices.startTransaction();
    User user = (User) this.userDao.findById(id);
    if (user == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("User doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return (UserDTO) user;
  }

  @Override
  public List<UserDTO> getAll() {
    dalservices.startTransaction();
    List<UserDTO> list = userDao.getAll();
    dalservices.commitTransaction();
    return list;
  }


  @Override
  public void updateConfirmed(boolean confirmed, boolean antique_dealer, int user_id) {
    dalservices.startTransaction();
    this.userDao.updateConfirmed(confirmed, antique_dealer, user_id);
    dalservices.commitTransaction();
  }



}
