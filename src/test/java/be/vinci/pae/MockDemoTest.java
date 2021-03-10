package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.*;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.MockApplicationBinder;

public class MockDemoTest {

	private UserUCC userUCC;
	private UserFactory user;

	@BeforeEach
	void initAll() {
		ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
		this.userUCC = locator.getService(UserUCC.class);
		this.user = locator.getService(UserFactory.class);
	}

	@Test
	public void demoTest() {
	  assertNotNull(this.userUCC);
	}
	
	/**
	 *Tests login method of UserUcc using MockUserDAO
     * parameters used : username=root, password=123
	 * information is correct
	 */
	@Test
	public void MockTestLoginCorrect() {
	  UserDTO userDTO = user.getUserDTO();
	  userDTO.setUserName("root");
	  userDTO.setPassword("$2a$10$9wCIFfvCj7CxhU2rA3DYOeZK6ZpugxZ4gDHCUxxrX9cUE/UK5pHSa");
	  assertEquals(userDTO,userUCC.login("root","123"));
	}
	
	/**
     * Tests login method of UserUcc using MockUserDAO
     * parameters used : username=mike, password=123
     * information is incorrect
     */
    @Test
    public void MockTestLoginFalse() {
      assertNull(userUCC.login("mike","123"));
    }

}
