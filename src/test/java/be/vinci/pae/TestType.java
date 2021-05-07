package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.type.TypeUCC;
import be.vinci.pae.services.TypeDAO;

public class TestType {

  private DomaineFactory domaineFactory;
  private TypeDTO typeDTO;
  private TypeDAO typeDAO;
  private TypeUCC typeUCC;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.typeUCC = locator.getService(TypeUCC.class);
    this.typeDAO = locator.getService(TypeDAO.class);

    typeDTO = domaineFactory.getTypeDTO();
  }

  /**
   * Success test :
   */
  @Test
  public void testFindByIdV1() {
    Mockito.when(typeDAO.findById(typeDTO.getTypeId())).thenReturn(typeDTO);
    assertEquals(typeDTO, typeUCC.findById(typeDTO.getTypeId()));
  }

  /**
   * Fail test :
   */
  @Test
  public void testFindByIdV2() {
    Mockito.when(typeDAO.findById(typeDTO.getTypeId())).thenReturn(null);
    assertThrows(BusinessException.class, () -> typeUCC.findById(typeDTO.getTypeId()));
  }



}
