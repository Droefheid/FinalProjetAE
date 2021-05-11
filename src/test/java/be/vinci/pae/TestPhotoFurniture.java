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
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureUCC;
import be.vinci.pae.services.PhotoFurnitureDAO;

public class TestPhotoFurniture {

  private DomaineFactory domaineFactory;
  private PhotoFurnitureDTO photoFurnitureDTO;
  private PhotoFurnitureDAO photoFurnitureDAO;
  private PhotoFurnitureUCC photoFurnitureUCC;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.photoFurnitureDAO = locator.getService(PhotoFurnitureDAO.class);
    this.photoFurnitureUCC = locator.getService(PhotoFurnitureUCC.class);

    photoFurnitureDTO = domaineFactory.getPhotoFurnitureDTO();
  }

  /**
   * Success test : The photoFurniture is found and well returned.
   */
  @Test
  public void testFindByIdV1() {
    int id = photoFurnitureDTO.getPhotoId();
    Mockito.when(photoFurnitureDAO.findById(id)).thenReturn(photoFurnitureDTO);
    assertEquals(photoFurnitureDTO, photoFurnitureUCC.findById(id));
  }

  /**
   * Fail test : Photo doesn't exist. BusinessException thrown.
   */
  @Test
  public void testFindByIdV2() {
    int id = photoFurnitureDTO.getPhotoId();
    Mockito.when(photoFurnitureDAO.findById(id)).thenReturn(null);
    assertThrows(BusinessException.class, () -> photoFurnitureUCC.findById(id));
  }

  /**
   * Success test : the favorite photoFurniture is well updated.
   */
  @Test
  public void testUpdateFavoriteV1() {
    Mockito.when(photoFurnitureDAO.updateFavorite(photoFurnitureDTO)).thenReturn(photoFurnitureDTO);
    assertEquals(photoFurnitureDTO, photoFurnitureUCC.updateFavorite(photoFurnitureDTO));
  }

  /**
   * Fail test : Photo_furniture doesn't exist. BusinessException thrown.
   */
  @Test
  public void testUpdateFavoriteV2() {
    Mockito.when(photoFurnitureDAO.updateFavorite(photoFurnitureDTO)).thenReturn(null);
    assertThrows(BusinessException.class,
        () -> photoFurnitureUCC.updateFavorite(photoFurnitureDTO));
  }

  /**
   * Success test : the visibility of the photoFurniture is well updated.
   */
  @Test
  public void testUpdateVisibilityV1() {
    Mockito.when(photoFurnitureDAO.updateVisibility(photoFurnitureDTO))
        .thenReturn(photoFurnitureDTO);
    assertEquals(photoFurnitureDTO, photoFurnitureUCC.updateVisibility(photoFurnitureDTO));
  }

  /**
   * Fail test : Photo_furniture doesn't exist. BusinessException thrown.
   */
  @Test
  public void testUpdateVisibilityV2() {
    Mockito.when(photoFurnitureDAO.updateVisibility(photoFurnitureDTO)).thenReturn(null);
    assertThrows(BusinessException.class,
        () -> photoFurnitureUCC.updateVisibility(photoFurnitureDTO));
  }



}
