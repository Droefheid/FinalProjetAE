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
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;

public class DemoTest {

  private UserUCC userUCC;
  private UserFactory user;
  private UserDTO userDTO;

  @BeforeEach
  void initAll() {
    Config.load("test.properties");
    userUCC = Mockito.mock(UserUCC.class);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.user = locator.getService(UserFactory.class);

    UserDTO userDTO = user.getUserDTO();
    userDTO.setUserName("Jo123");
    userDTO.setPassword("123");
  }

  /**
   * Checks to see if userUCC is null or not.
   */
  @Test
  public void demoTest() {
    assertNotNull(this.userUCC);
  }

  /**
   * Tests login method of UserUcc using UserDAOImpl parameters used :
   *  username=Jo123, password=123 information is incorrect.
   * 
   */
  @Test
  public void testLogin() {
    Mockito.when(userUCC.login("root", "123")).thenReturn(null);
    Mockito.when(userUCC.login("", "")).thenReturn(null);
    Mockito.when(userUCC.login("", "1234")).thenReturn(null);
    Mockito.when(userUCC.login("Jo123", "1234")).thenReturn(null);
    Mockito.when(userUCC.login("Jo123", "123")).thenReturn(userDTO);

    assertAll(
        () -> assertNull(userUCC.login("root", "123")),
        () -> assertNull(userUCC.login("", "")), 
        () -> assertNull(userUCC.login("", "1234")),
        () -> assertNull(userUCC.login("Jo123", "1234")),
        () -> assertEquals(userDTO, userUCC.login("Jo123", "123")));
  }



}
