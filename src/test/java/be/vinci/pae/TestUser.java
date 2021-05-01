package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
   * Checks to see if userUCC is null or not.
   */
  @Test
  public void demoTest() {
    assertNotNull(this.userUCC);
  }

  /**
   * Tests login method of UserUcc using UserDAOImp. Correct username=Jo123, password=123 .
   * 
   */

  @Test
  public void testLoginV1() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("root")).thenReturn(null);
    assertThrows(BusinessException.class, () -> userUCC.login("root", "123"));
  }

  @Test
  public void testLoginV2() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("Jo123")).thenReturn(userDTO);
    assertEquals(userDTO, userUCC.login("Jo123", "123"));
  }

  @Test
  public void testLoginV3() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("Jo123")).thenReturn(null);
    assertThrows(BusinessException.class, () -> userUCC.login("Jo123", "1234"));
  }

  /**
   * Test register method.
   */
  @Test
  public void testRegister() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(1, -1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(userDAO.registerUser(userDTO)).thenReturn(userDTO, (UserDTO) null);

    assertAll(() -> assertEquals(userDTO, userUCC.register(userDTO, addressDTO)),
        () -> assertThrows(BusinessException.class, () -> userUCC.register(userDTO, addressDTO)),
        () -> assertThrows(BusinessException.class, () -> userUCC.register(userDTO, addressDTO)));
  }

}