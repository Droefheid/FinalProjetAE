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
import be.vinci.pae.domaine.type.TypeDTO;
import be.vinci.pae.domaine.type.TypeUCC;
import be.vinci.pae.services.TypeDAO;

public class TestType {

  private DomaineFactory domaineFactory;
  private TypeDAO typeDAO;
  private TypeUCC typeUCC;
  private TypeDTO typeDTO;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.typeUCC = locator.getService(TypeUCC.class);
    this.typeDAO = locator.getService(TypeDAO.class);

    typeDTO = domaineFactory.getTypeDTO();
  }

  /**
   * Success test : Type is found based on the id and return the type found.
   */
  @Test
  public void testFindByIdV1() {
    typeDTO.setTypeId(1);
    int id = typeDTO.getTypeId();
    Mockito.when(typeDAO.findById(id)).thenReturn(typeDTO);
    assertEquals(typeDTO, typeUCC.findById(id));
  }

  /**
   * Fail test : Type doesn't exist.
   */
  @Test
  public void testFindByIdV2() {
    typeDTO.setTypeId(-1);
    int id = typeDTO.getTypeId();
    Mockito.when(typeDAO.findById(id)).thenReturn(null);
    assertThrows(BusinessException.class, () -> typeUCC.findById(id));
  }

  @Test
  public void testGetAllV1() {
    List<TypeDTO> list = new ArrayList<TypeDTO>();
    Mockito.when(typeDAO.getAll()).thenReturn(list);
    assertEquals(list, typeUCC.getAll());
  }



}
