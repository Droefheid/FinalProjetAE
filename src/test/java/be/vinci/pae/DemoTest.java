package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.*;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.vinci.pae.domaine.UserUCC;
import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;

public class DemoTest {

	private UserUCC userUCC;

	@BeforeEach
	void initAll() {
	  Config.load("prod.properties");
		ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
		this.userUCC = locator.getService(UserUCC.class);
	}

	@Test
	public void demoTest() {
	  assertNotNull(this.userUCC);
	}	
	
	 /**
     * Test la methode login de UserUcc
     * En utilisant UserDAOImpl
     */
    @Test
    public void TestLogin() {
      assertNotNull(userUCC.login("Jo123","azerty"));
    }


	
}
