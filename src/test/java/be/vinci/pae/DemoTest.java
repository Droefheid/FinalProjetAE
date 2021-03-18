package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;

public class DemoTest {

  private UserUCC userUCC;
  private DomaineFactory user;
  private UserDTO userDTO;
  private UserDAO userDAO;

  @BeforeEach
  void initAll() {
    Config.load("test.properties");
    userDAO = Mockito.mock(UserDAO.class);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.user = locator.getService(DomaineFactory.class);
    this.userUCC = locator.getService(UserUCC.class);

    UserDTO userDTO = user.getUserDTO();
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
    Mockito.when(userDAO.findByUserName("root")).thenReturn(null);
    Mockito.when(userDAO.findByUserName("Jo123")).thenReturn(userDTO);
    userDTO.setUserName("Jo123");
    userDTO.setPassword("123");
    UserDTO correct =
        userUCC.login("Jo123", "$2a$10$LnMTnzCT7c1HL2VtLAJzfurviQy70TTDlUg0wIHYGr/NV0LhW.QUq");
    assertAll(
        () -> assertNull(
            userUCC.login("root", "$2a$10$LnMTnzCT7c1HL2VtLAJzfurviQy70TTDlUg0wIHYGr/NV0LhW.QUq")),
        () -> assertNull(userUCC.login("Jo123", "1234")),
        () -> assertEquals(userDTO.getUserName(), correct.getUserName()),
        () -> assertEquals(userDTO.getPassword(), correct.getPassword()));
  }

  @Test
  public void testRegister() {
    UserDTO correct = userUCC.register(null, null);
    assertAll();
  }

}
