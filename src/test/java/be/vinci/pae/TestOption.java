package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.option.OptionUCC;
import be.vinci.pae.domaine.user.UserDTO;
import be.vinci.pae.services.OptionDAO;

public class TestOption {

  private DomaineFactory domaineFactory;
  private OptionUCC optionUCC;
  private OptionDTO optionDTO;
  private OptionDAO optionDAO;
  private UserDTO userDTO;
  private FurnitureDTO furnitureDTO;


  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.optionUCC = locator.getService(OptionUCC.class);
    this.optionDAO = locator.getService(OptionDAO.class);

    optionDTO = domaineFactory.getOptionDTO();
    userDTO = domaineFactory.getUserDTO();
    furnitureDTO = domaineFactory.getFurnitureDTO();
  }

  /**
   * Success test : get the option by id.
   */
  @Test
  public void testGetOptionV1() {
    Mockito.when(optionDAO.findOptionByID(optionDTO.getId())).thenReturn(optionDTO);
    assertEquals(optionDTO, optionUCC.getOption(optionDTO.getId()));
  }

  /**
   * Failed test : Option doesn't exist.
   */
  @Test
  public void testGetOptionV2() {
    Mockito.when(optionDAO.findOptionByID(optionDTO.getId())).thenReturn(null);
    assertThrows(BusinessException.class, () -> optionUCC.getOption(optionDTO.getId()));
  }

  /**
   * Success test : find the option with it's id.
   */
  @Test
  public void testFindOptionV1() {
    LocalDateTime date = LocalDateTime.now();
    optionDTO.setBeginningOptionDate(Timestamp.valueOf(date));
    int furnitureID = furnitureDTO.getFurnitureId();
    int userID = userDTO.getID();

    Mockito.when(optionDAO.findOptionByFurnitureIdANDCustomerId(furnitureID, userID))
        .thenReturn(optionDTO);
    assertEquals(optionDTO, optionUCC.findOption(furnitureID, userID));
  }

  /**
   * Failed test : beginning_option_date is null.
   */
  @Test
  public void testFindOptionV2() {
    optionDTO.setBeginningOptionDate(null);
    int furnitureID = furnitureDTO.getFurnitureId();
    int userID = userDTO.getID();
    Mockito.when(optionDAO.findOptionByFurnitureIdANDCustomerId(furnitureID, userID))
        .thenReturn(optionDTO);
    assertThrows(BusinessException.class, () -> optionUCC.findOption(furnitureID, userID));
  }



}
