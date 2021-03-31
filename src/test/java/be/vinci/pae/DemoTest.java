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
import be.vinci.pae.domaine.AddressDTO;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.User;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.MockApplicationBinder;

public class DemoTest {

  private UserUCC userUCC;
  private DomaineFactory domaineFactory;
  private UserDTO userDTO;
  private UserDAO userDAO;
  private AddressDTO addressDTO;
  private User user;

  @BeforeEach
  void initAll() {
    Config.load("test.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.userUCC = locator.getService(UserUCC.class);
    this.userDAO = locator.getService(UserDAO.class);

    userDTO = domaineFactory.getUserDTO();
    addressDTO = domaineFactory.getAdress();
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
  public void testLogin() {
    userDTO.setUserName("Jo123");
    user = (User) userDTO;
    user.setPassword(user.hashPassword("123"));
    Mockito.when(userDAO.findByUserName("root")).thenReturn(null);
    Mockito.when(userDAO.findByUserName("Jo123")).thenReturn(userDTO);

    assertAll(() -> assertThrows(BusinessException.class, () -> userUCC.login("root", "123")),
        () -> assertEquals(userDTO, userUCC.login("Jo123", "123")),
        () -> assertThrows(BusinessException.class, () -> userUCC.login("Jo123", "1234")));
  }

  /**
   * Test register method.
   * 
   * so first time using method returns OK. Second time returns null.
   * 
   * because user or addressDTO should already be registered.
   */
  @Test
  public void testRegister() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(1, -1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(userDAO.registerUser(userDTO)).thenReturn(userDTO, (UserDTO) null);

    assertAll(() -> assertNotNull(userUCC.register(userDTO, addressDTO)),
        () -> assertThrows(BusinessException.class, () -> userUCC.register(userDTO, addressDTO)),
        () -> assertThrows(BusinessException.class, () -> userUCC.register(userDTO, addressDTO)));
  }

}
