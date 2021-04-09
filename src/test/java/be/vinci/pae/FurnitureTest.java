package be.vinci.pae;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.utils.Config;

public class FurnitureTest {

  private FurnitureUCC furnitureUCC;
  private FurnitureDTO furnitureDTO;
  private FurnitureDAO furnitureDAO;
  private DomaineFactory domaineFactory;

  @BeforeAll
  void initAll() {
    Config.load("test.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
  }



}
