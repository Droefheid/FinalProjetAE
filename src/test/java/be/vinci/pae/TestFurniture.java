package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
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
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.OptionDAO;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoFurnitureDAO;
import be.vinci.pae.services.TypeDAO;
import be.vinci.pae.services.UserDAO;

class TestFurniture {

  private DomaineFactory domaineFactory;
  private FurnitureUCC furnitureUCC;
  private FurnitureDTO furnitureDTO;
  private FurnitureDAO furnitureDAO;
  private UserDAO userDAO;
  private UserDTO userDTO;
  private PhotoDAO photoDAO;
  private PhotoFurnitureDAO photoFurnitureDAO;
  private OptionDAO optionDAO;
  private OptionDTO optionDTO;
  private TypeDAO typeDAO;



  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.furnitureUCC = locator.getService(FurnitureUCC.class);
    this.furnitureDAO = locator.getService(FurnitureDAO.class);
    this.typeDAO = locator.getService(TypeDAO.class);
    this.userDAO = locator.getService(UserDAO.class);
    this.photoDAO = locator.getService(PhotoDAO.class);
    this.photoFurnitureDAO = locator.getService(PhotoFurnitureDAO.class);
    this.optionDAO = locator.getService(OptionDAO.class);

    furnitureDTO = domaineFactory.getFurnitureDTO();
    userDTO = domaineFactory.getUserDTO();
    optionDTO = domaineFactory.getOptionDTO();
  }

  /**
   * Failed test : the furniture doesn't exist. Furniture Id equals -1.
   */
  @Test
  public void testFindByIdV1() {
    furnitureDTO.setFurnitureId(-1);
    Mockito.when(furnitureDAO.findById(furnitureDTO.getFurnitureId())).thenReturn(null);
    assertThrows(BusinessException.class,
        () -> furnitureUCC.findById(furnitureDTO.getFurnitureId()));
  }

  /**
   * Success test : the furniture exist. Furniture Id equals 1.
   */
  @Test
  public void testFindByIdV2() {
    furnitureDTO.setFurnitureId(1);
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
   * Failed test : furniture isn't added.
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
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
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

  /**
   * Success test : the method return a Object[6] with all infos for update.
   */
  @Test
  public void testGetAllInfosForUpdateV1() {
    List<TypeDTO> listType = new ArrayList<TypeDTO>();
    List<UserDTO> listUser = new ArrayList<UserDTO>();
    List<PhotoDTO> listPhoto = new ArrayList<PhotoDTO>();
    List<PhotoFurnitureDTO> listPhotoFurniture = new ArrayList<PhotoFurnitureDTO>();

    int id = furnitureDTO.getFurnitureId();

    Mockito.when(furnitureDAO.findById(id)).thenReturn(furnitureDTO);
    Mockito.when(typeDAO.getAll()).thenReturn(listType);
    Mockito.when(userDAO.getAll()).thenReturn(listUser);
    Mockito.when(photoDAO.getAllForFurniture(id)).thenReturn(listPhoto);
    Mockito.when(photoFurnitureDAO.getAllForFurniture(id)).thenReturn(listPhotoFurniture);
    Mockito.when(optionDAO.findOptionByFurniture(id)).thenReturn(optionDTO);

    Object[] allLists = new Object[6];
    allLists[0] = furnitureDTO;
    allLists[1] = listType;
    allLists[2] = listUser;
    allLists[3] = listPhoto;
    allLists[4] = listPhotoFurniture;
    allLists[5] = optionDTO;

    Object[] test = furnitureUCC.getAllInfosForUpdate(id);

    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]),
        () -> assertEquals(allLists[2], test[2]), () -> assertEquals(allLists[3], test[3]),
        () -> assertEquals(allLists[4], test[4]), () -> assertEquals(allLists[5], test[5]));
  }

  /**
   * Success test : it returns an object list with two lists into. A listType and a listUser.
   */
  @Test
  public void testGetAllInfosForAddV1() {
    List<TypeDTO> listType = new ArrayList<TypeDTO>();
    List<UserDTO> listUser = new ArrayList<UserDTO>();

    Mockito.when(typeDAO.getAll()).thenReturn(listType);
    Mockito.when(userDAO.getAll()).thenReturn(listUser);

    Object[] allLists = new Object[2];
    allLists[0] = listType;
    allLists[1] = listUser;

    Object[] test = furnitureUCC.getAllInfosForAdd();

    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]));
  }


  /**
   * Success test : This method return 2 list of furnitures.
   */
  @Test
  public void testGetClientFurnituresV1() {
    List<FurnitureDTO> listMyFurniture = new ArrayList<FurnitureDTO>();
    List<FurnitureDTO> listBoughtFurniture = new ArrayList<FurnitureDTO>();

    int id = furnitureDTO.getFurnitureId();
    Mockito.when(furnitureDAO.getMyFurniture(id)).thenReturn(listMyFurniture);
    Mockito.when(furnitureDAO.getBoughtFurniture(id)).thenReturn(listBoughtFurniture);

    Object[] allLists = new Object[2];
    allLists[0] = listMyFurniture;
    allLists[1] = listBoughtFurniture;

    Object[] test = furnitureUCC.getClientFurnitures(id);

    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]));
  }

  /**
   * Success test : return a list based on search, minimum and maximum price.
   * 
   */
  @Test
  public void testSearchFurnitureV1() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    String search = "armoire";
    int max = 100;
    int min = 50;
    Mockito.when(furnitureDAO.searchFurnitureWithoutType(search, min, max)).thenReturn(list);
    assertEquals(list, furnitureUCC.searchFurniture(search, -1, min, max));
  }

  /**
   * Success test : return a list based on price,search and type.
   */
  @Test
  public void testSearchFurnitureV2() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    String search = "armoire";
    int max = 100;
    int min = 50;
    Mockito.when(furnitureDAO.searchFurniture(search, 1, min, max)).thenReturn(list);
    assertEquals(list, furnitureUCC.searchFurniture(search, 1, min, max));
  }


}
