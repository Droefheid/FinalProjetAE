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
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoVisitDTO;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitUCC;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoVisitDAO;
import be.vinci.pae.services.UserDAO;
import be.vinci.pae.services.VisitDAO;

public class TestVisit {

  private DomaineFactory domaineFactory;
  private VisitDTO visitDTO;
  private VisitDAO visitDAO;
  private VisitUCC visitUCC;
  private AddressDTO addressDTO;
  private UserDTO userDTO;
  private UserDAO userDAO;
  private PhotoDAO photoDAO;
  private PhotoVisitDAO photoVisitDAO;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.visitUCC = locator.getService(VisitUCC.class);
    this.visitDAO = locator.getService(VisitDAO.class);
    this.userDAO = locator.getService(UserDAO.class);
    this.photoDAO = locator.getService(PhotoDAO.class);
    this.photoVisitDAO = locator.getService(PhotoVisitDAO.class);

    visitDTO = domaineFactory.getVisitDTO();
    addressDTO = domaineFactory.getAdressDTO();
    userDTO = domaineFactory.getUserDTO();
  }

  /**
   * Success Test : visit is well introduce.
   */
  @Test
  public void testIntroduceVisitV1() {
    int userID = userDTO.getID();
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(1);
    Mockito.when(visitDAO.introduceVisit(visitDTO)).thenReturn(visitDTO);
    assertEquals(visitDTO, visitUCC.introduceVisit(visitDTO, addressDTO, userID));
  }

  /**
   * Success Test : visit is well introduce.
   */
  @Test
  public void testIntroduceVisitV2() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(-1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(visitDAO.introduceVisit(visitDTO)).thenReturn(visitDTO);
    int userID = userDTO.getID();
    assertEquals(visitDTO, visitUCC.introduceVisit(visitDTO, addressDTO, userID));
  }

  /**
   * Fail Test : Visit already exists.
   */
  @Test
  public void testIntroduceVisitV3() {
    Mockito.when(userDAO.getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry())).thenReturn(-1);
    Mockito.when(userDAO.registerAddress(addressDTO)).thenReturn(1);
    Mockito.when(visitDAO.introduceVisit(visitDTO)).thenReturn(null);
    int userID = userDTO.getID();
    assertThrows(BusinessException.class,
        () -> visitUCC.introduceVisit(visitDTO, addressDTO, userID));
  }

  /**
   * Successs test : Visit is found with it's id and is well returned.
   */
  @Test
  public void testGetVisitV1() {
    int id = 1;
    Mockito.when(visitDAO.findById(id)).thenReturn(visitDTO);
    assertEquals(visitDTO, visitUCC.getVisit(id));
  }

  /**
   * Fail test : Visit doesn't exist.
   */
  @Test
  public void testGetVisitV2() {
    int id = -1;
    Mockito.when(visitDAO.findById(id)).thenReturn(null);
    assertThrows(BusinessException.class, () -> visitUCC.getVisit(id));
  }

  /**
   * Success Test : return a list with all the visits.
   */
  @Test
  public void testGetAllV1() {
    List<VisitDTO> list = new ArrayList<VisitDTO>();
    Mockito.when(visitDAO.getAll()).thenReturn(list);
    assertEquals(list, visitUCC.getAll());
  }

  /**
   * Success test : return a list of Object with a listPhoto, a listPhotoVisit and a visit.
   */
  @Test
  public void testGetAllInfosOfVisitV1() {
    int id = visitDTO.getId();

    List<PhotoDTO> listPhoto = new ArrayList<PhotoDTO>();
    List<PhotoVisitDTO> listPhotoVisit = new ArrayList<PhotoVisitDTO>();

    Mockito.when(visitDAO.findById(id)).thenReturn(visitDTO);
    Mockito.when(photoDAO.getAllForVisit(id)).thenReturn(listPhoto);
    Mockito.when(photoVisitDAO.getAllForVisit(id)).thenReturn(listPhotoVisit);


    Object[] allLists = new Object[3];
    allLists[0] = visitDTO;
    allLists[1] = listPhoto;
    allLists[2] = listPhotoVisit;

    Object[] test = visitUCC.getAllInfosOfVisit(id);

    assertAll(() -> assertEquals(allLists[0], test[0]), () -> assertEquals(allLists[1], test[1]),
        () -> assertEquals(allLists[2], test[2]));
  }

  /**
   * Fail test : Visit doesn't exist.
   */
  @Test
  public void testGetAllInfosOfVisitV2() {
    int id = visitDTO.getId();

    List<PhotoDTO> listPhoto = new ArrayList<PhotoDTO>();
    List<PhotoVisitDTO> listPhotoVisit = new ArrayList<PhotoVisitDTO>();

    Mockito.when(visitDAO.findById(id)).thenReturn(null);
    Mockito.when(photoDAO.getAllForVisit(id)).thenReturn(listPhoto);
    Mockito.when(photoVisitDAO.getAllForVisit(id)).thenReturn(listPhotoVisit);


    Object[] allLists = new Object[3];
    allLists[0] = visitDTO;
    allLists[1] = listPhoto;
    allLists[2] = listPhotoVisit;

    // Object[] test = visitUCC.getAllInfosOfVisit(id);
    assertThrows(BusinessException.class, () -> visitUCC.getAllInfosOfVisit(id));
  }

}
