package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.TypeDAO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.utils.Config;

class testFurniture {

  private FurnitureUCC furnitureUCC;
  private FurnitureDTO furnitureDTO;
  private FurnitureDAO furnitureDAO;
  private DomaineFactory domaineFactory;
  private TypeDAO typeDAO;
  private UserDAO userDAO;

  @BeforeEach
  void initAll() {
    Config.load("test.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.furnitureUCC = locator.getService(FurnitureUCC.class);
    this.furnitureDAO = locator.getService(FurnitureDAO.class);
    this.typeDAO = locator.getService(TypeDAO.class);
    this.userDAO = locator.getService(UserDAO.class);

    furnitureDTO = domaineFactory.getFurnitureDTO();
  }

  @Test
  public void demoTest() {
    assertNotNull(this.furnitureUCC);
  }



}
