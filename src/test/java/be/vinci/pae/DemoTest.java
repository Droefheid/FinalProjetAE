package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.ApplicationBinder;

public class DemoTest {

	private UserUCC userUCC;
	private UserDTO userDTO;

	@BeforeEach
	void initAll() {
		ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
		this.userUCC = locator.getService(UserUCC.class);
	}

	@Test
	public void demoTest() {
	  assertNotNull(this.userUCC);
	}
	
	@Test
	public void testLogin() {
	  userDTO.setUserName("hi");
	  userDTO.setPassword("hi");
	  assertEquals(userDTO,userUCC.login("hi","$2a$10$B0LtQqz9ERNwEHlOOjrsk.g7XZBPIJ4aCjQVBPa4QyNOKYkZ4V3hq"));
	}

	
}
