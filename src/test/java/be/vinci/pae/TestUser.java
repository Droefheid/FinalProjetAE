package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.user.User;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.user.UserUCC;
import be.vinci.pae.services.UserDAO;

public class TestUser {

  private UserUCC userUCC;
  private DomaineFactory domaineFactory;
  private UserDTO userDTO;
  private UserDAO userDAO;
  private AddressDTO addressDTO;
  private User user;

  @BeforeEach
  void initAll() {
    // BeforeEach doit devenir BeforeAll.
    // Config.load("test.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.userUCC = locator.getService(UserUCC.class);
    this.userDAO = locator.getService(UserDAO.class);

    userDTO = domaineFactory.getUserDTO();
    addressDTO = domaineFactory.getAdressDTO();
  }

  /**
   * Fail Test : login method of UserUcc using UserDAOImp. Correct username=Jo123, password=123 .
   */

  @Test
  public void testLoginV1() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("root")).thenReturn(null);
    assertThrows(BusinessException.class, () -> userUCC.login("root", "123"));
  }

  /**
   * Success Test : login method of UserUcc. Correct username=Jo123, password=123 .
   */
  @Test
  public void testLoginV2() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName(userDTO.getUserName())).thenReturn(userDTO);
    assertEquals(userDTO, userUCC.login("Jo123", "123"));
  }

  /**
   * Fail Test : login method of UserUcc. Correct username=Jo123, password=123 .
   */
  @Test
  public void testLoginV3() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("Jo123")).thenReturn(null);
    assertThrows(BusinessException.class, () -> userUCC.login("Jo123", "1234"));
  }

  /**
   * Success test : register method of UserUcc. All informations are well filled.
   */
  @Test
  public void testRegisterV1() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(1);
    Mockito.when(userDAO.registerUser(userDTO)).thenReturn(userDTO);

    assertEquals(userDTO, userUCC.register(userDTO, addressDTO));
  }

  /**
   * Fail test : register method of UserUcc. BusinessException has been throw.
   */
  @Test
  public void testRegisterV2() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(-1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(userDAO.registerUser(userDTO)).thenReturn((UserDTO) null);

    assertThrows(BusinessException.class, () -> userUCC.register(userDTO, addressDTO));

  }

  /**
   * Success test : register method of UserUcc.
   */
  @Test
  public void testRegisterV3() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(-1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(userDAO.registerUser(userDTO)).thenReturn(userDTO);

    assertEquals(userDTO, userUCC.register(userDTO, addressDTO));

  }

  /**
   * Success test : getUser return an existing User.
   */
  @Test
  public void testGetUserV1() {
    int id = userDTO.getID();
    Mockito.when(userDAO.findById(id)).thenReturn(userDTO);
    assertEquals(userDTO, userUCC.getUser(id));
  }

  /**
   * Fail test : getUser return null because the user is not found.
   */
  @Test
  public void testGetUserV2() {
    int id = -1;
    Mockito.when(userDAO.findById(id)).thenReturn(null);
    assertThrows(BusinessException.class, () -> userUCC.getUser(id));
  }

  /**
   * Success test : return a list with all users.
   */
  @Test
  public void testGetAllV1() {
    List<UserDTO> list = new ArrayList<UserDTO>();
    Mockito.when(userDAO.getAll()).thenReturn(list);
    assertEquals(list, userUCC.getAll());
  }

  /**
   * Success test : get the address with his id.
   */
  @Test
  public void testGetAddressByIdV1() {
    int id = addressDTO.getID();
    Mockito.when(userDAO.getAddressById(id)).thenReturn(addressDTO);
    assertEquals(addressDTO, userUCC.getAddressById(id));

  }



}
