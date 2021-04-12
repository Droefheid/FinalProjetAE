package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.furniture.FurnitureUCC;
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.user.UserDTO;
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
  private TypeDTO typeDTO;
  private UserDAO userDAO;
  private UserDTO userDTO;

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
    typeDTO = domaineFactory.getTypeDTO();
    userDTO = domaineFactory.getUserDTO();
  }

  /**
   * Failed test : the furniture id is not correct.
   */
  @Test
  public void testFindByIdV1() {
    Mockito.when(furnitureDAO.findById(furnitureDTO.getFurnitureId())).thenReturn(null);
    assertThrows(BusinessException.class,
        () -> furnitureUCC.findById(furnitureDTO.getFurnitureId()));
  }

  /**
   * Success test : the furniture id is correct.
   */
  @Test
  public void testFindByIdV2() {
    Mockito.when(furnitureDAO.findById(furnitureDTO.getFurnitureId())).thenReturn(furnitureDTO);
    assertEquals(furnitureDTO, furnitureUCC.findById(furnitureDTO.getFurnitureId()));
  }

  /**
   * Success test : the furniture is correctly added.
   */
  @Test
  public void testAddFurnitureV1() {
    Mockito.when(furnitureDAO.add(furnitureDTO)).thenReturn(furnitureDTO);
    assertEquals(furnitureDTO, furnitureUCC.add(furnitureDTO));
  }

  /**
   * Failed test : the furniture is not added.
   */
  @Test
  public void testAddFurnitureV2() {
    Mockito.when(furnitureDAO.add(furnitureDTO)).thenReturn(null);
    assertThrows(BusinessException.class, () -> furnitureUCC.add(furnitureDTO));
  }

  /**
   * Success test : the furniture is correctly updated.
   */
  @Test
  public void testUpdateFurnitureV1() {
    Mockito.when(furnitureDAO.update(furnitureDTO)).thenReturn(furnitureDTO);
    assertEquals(furnitureDTO, furnitureUCC.update(furnitureDTO));
  }

  /**
   * Failed test : the furniture is not correctly updated.
   */
  @Test
  public void testUpdateFurnitureV2() {
    Mockito.when(furnitureDAO.update(furnitureDTO)).thenReturn(null);
    assertThrows(BusinessException.class, () -> furnitureUCC.update(furnitureDTO));
  }

  /**
   * Success test : the furniture list is well returned.
   */
  @Test
  public void testGetAllFurnitureV1() {
    List<FurnitureDTO> list = null;
    Mockito.when(furnitureDAO.getAll()).thenReturn(list);
    assertEquals(list, furnitureUCC.getAll());
  }
}
