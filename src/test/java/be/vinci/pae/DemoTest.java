package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.ApplicationBinder;

public class DemoTest {

	private UserUCC userUCC;
	private UserFactory user;

	@BeforeEach
	void initAll() {
		ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
		this.userUCC = locator.getService(UserUCC.class);
		this.user = locator.getService(UserFactory.class);
	}

	@Test
	public void demoTest() {
	  assertNotNull(this.userUCC);
	}
	
	/*
	 * Test la methode login de UserUcc
	 * En utilisant MockUserDAO
	 */
	@Test
	public void MockTestLogin() {
	  UserDTO userDTO = user.getUserDTO();
	  userDTO.setUserName("root");
	  //password = 123
	  userDTO.setPassword("$2a$10$9wCIFfvCj7CxhU2rA3DYOeZK6ZpugxZ4gDHCUxxrX9cUE/UK5pHSa");
	  assertEquals(userDTO,userUCC.login("root","123"));
	}
	
	   /*
     * Test la methode login de UserUcc
     * En utilisant UserDAOImpl
     */
    @Test
    public void TestLogin() {
      UserDTO userDTO = user.getUserDTO();
      userDTO.setUserName("Jo123");
      //password = azerty
      userDTO.setPassword("$2a$10$LnMTnzCT7c1HL2VtLAJzfurviQy70TTDlUg0wIHYGr/NV0LhW.QUq");
      assertEquals(userDTO,userUCC.login("Jo123","azerty"));
    }


	
}
