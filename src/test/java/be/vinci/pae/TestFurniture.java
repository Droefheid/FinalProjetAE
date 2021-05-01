package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
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

class TestFurniture {

  private FurnitureUCC furnitureUCC;
  private FurnitureDTO furnitureDTO;
  private FurnitureDAO furnitureDAO;
  private DomaineFactory domaineFactory;
  private TypeDAO typeDAO;
  private UserDAO userDAO;
  private UserDTO userDTO;


  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.furnitureUCC = locator.getService(FurnitureUCC.class);
    this.furnitureDAO = locator.getService(FurnitureDAO.class);
    this.typeDAO = locator.getService(TypeDAO.class);
    this.userDAO = locator.getService(UserDAO.class);

    furnitureDTO = domaineFactory.getFurnitureDTO();
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
   * Failed test : if the furniture is not added, return null.
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

  /**
   * Success test : the furniture list from the userID is well returned.
   */
  @Test
  public void testGetMyFurnitureV1() {
    List<FurnitureDTO> list = null;
    int userID = userDTO.getID();
    Mockito.when(furnitureDAO.getMyFurniture(userID)).thenReturn(list);
    assertEquals(list, furnitureUCC.getMyFurniture(userID));
  }

  @Test
  public void testGetAllInfosForUpdateV1() {
    List<TypeDTO> listType = null;
    List<UserDTO> listUser = null;

    Mockito.when(furnitureDAO.findById(furnitureDTO.getFurnitureId())).thenReturn(furnitureDTO);
    Mockito.when(typeDAO.getAll()).thenReturn(listType);
    Mockito.when(userDAO.getAll()).thenReturn(listUser);

    Object[] allLists = new Object[3];
    allLists[0] = furnitureDTO;
    allLists[1] = listType;
    allLists[2] = listUser;

    Object[] test = furnitureUCC.getAllInfosForUpdate(furnitureDTO.getFurnitureId());



    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]),
        () -> assertEquals(allLists[2], test[2]));
  }


  @Test
  public void testGetAllInfosForAddV1() {
    Object[] allLists = new Object[2];
    List<TypeDTO> listType = null;
    List<UserDTO> listUser = null;

    Mockito.when(typeDAO.getAll()).thenReturn(listType);
    Mockito.when(userDAO.getAll()).thenReturn(listUser);

    allLists[0] = listType;
    allLists[1] = listUser;

    Object[] test = furnitureUCC.getAllInfosForAdd();

    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]));
  }
}
