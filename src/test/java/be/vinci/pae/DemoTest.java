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
	  Config.load("prod2.properties");
		ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
		this.userUCC = locator.getService(UserUCC.class);
	}

	@Test
	public void demoTest() {
	  assertNotNull(this.userUCC);
	}	
	
    /**
     *Tests login method of UserUcc using UserDAOImpl
     * parameters used : username=Jo123, password=azerty
     * information is correct
     */
    @Test
    public void TestLoginCorrect() {
      assertNotNull(userUCC.login("Jo123","azerty"));
    }

    /**
     *Tests login method of UserUcc using UserDAOImpl
     * parameters used : username=Jo123, password=15
     * information is incorrect
     */
    @Test
    public void TestLoginIncorrect() {
      assertNull(userUCC.login("Jo123","15"));
    }


	
}
