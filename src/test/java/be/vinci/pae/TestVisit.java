package be.vinci.pae;

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
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.domaine.visit.VisitDTO;
import be.vinci.pae.domaine.visit.VisitUCC;
import be.vinci.pae.services.VisitDAO;

public class TestVisit {


  private DomaineFactory domaineFactory;

  private VisitUCC visitUCC;
  private VisitDTO visitDTO;
  private VisitDAO visitDAO;

  private UserDTO userDTO;

  private AddressDTO addressDTO;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.visitUCC = locator.getService(VisitUCC.class);
    this.visitDAO = locator.getService(VisitDAO.class);

    userDTO = domaineFactory.getUserDTO();
    addressDTO = domaineFactory.getAdressDTO();
    visitDTO = domaineFactory.getVisitDTO();
  }

  /**
   * Success Test :
   */
  @Test
  public void testIntroduceVisitV1() {
    Mockito.when(visitDAO.introduceVisit(visitDTO)).thenReturn(visitDTO);
    assertEquals(visitDTO, visitUCC.introduceVisit(visitDTO, addressDTO, userDTO));
  }

  /**
   * Fail Test :
   */
  @Test
  public void testIntroduceVisitV2() {
    Mockito.when(visitDAO.introduceVisit(visitDTO)).thenReturn(null);
    assertThrows(BusinessException.class,
        () -> visitUCC.introduceVisit(visitDTO, addressDTO, userDTO));
  }


  /**
   * Success Test :
   */
  @Test
  public void testGetVisitV1() {
    Mockito.when(visitDAO.findById(visitDTO.getId())).thenReturn(visitDTO);
    assertEquals(visitDTO, visitUCC.getVisit(visitDTO.getId()));
  }

  /**
   * Fail Test : visit doesn't exist.
   */
  @Test
  public void testGetVisitV2() {
    visitDTO.setId(-1);
    Mockito.when(visitDAO.findById(visitDTO.getId())).thenReturn(null);
    assertThrows(BusinessException.class, () -> visitUCC.getVisit(visitDTO.getId()));
  }

  /**
   * Success test :
   */
  @Test
  public void testGetAllVisitV1() {
    List<VisitDTO> list = new ArrayList<VisitDTO>();

    Mockito.when(visitDAO.getAll()).thenReturn(list);
    assertEquals(list, visitUCC.getAll());
  }

  /**
   * Success test :
   */
  @Test
  public void testGetAllVisitNotConfirmedV1() {
    List<VisitDTO> list = new ArrayList<VisitDTO>();

    Mockito.when(visitDAO.getAllNotConfirmed()).thenReturn(list);
    assertEquals(list, visitUCC.getAllNotConfirmed());
  }



}
