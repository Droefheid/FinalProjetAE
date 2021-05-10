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
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import be.vinci.pae.domaine.photo.PhotoUCC;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoFurnitureDAO;

public class TestPhoto {

  private DomaineFactory domaineFactory;
  private PhotoDAO photoDAO;
  private PhotoDTO photoDTO;
  private PhotoUCC photoUCC;
  private PhotoFurnitureDAO photoFurnitureDAO;
  private PhotoFurnitureDTO photoFurnitureDTO;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.photoDAO = locator.getService(PhotoDAO.class);
    this.photoUCC = locator.getService(PhotoUCC.class);
    this.photoFurnitureDAO = locator.getService(PhotoFurnitureDAO.class);


    photoDTO = domaineFactory.getPhotoDTO();
    photoFurnitureDTO = domaineFactory.getPhotoFurnitureDTO();

  }

  /**
   * Success test :
   */
  @Test
  public void testFindByIdV1() {
    int id = 1;
    Mockito.when(photoDAO.findById(id)).thenReturn(photoDTO);
    assertEquals(photoDTO, photoUCC.findById(id));
  }

  /**
   * Fail test : Photo doesn't exist.
   */
  @Test
  public void testFindByIdV2() {
    int id = -1;
    Mockito.when(photoDAO.findById(id)).thenReturn(null);
    assertThrows(BusinessException.class, () -> photoUCC.findById(id));
  }


  /**
   * Success test : check if it exist a favorite photo and return the photo.
   */
  @Test
  public void testGetFavouritePhotoForFurnitureV1() {
    int id = photoDTO.getId();
    Mockito.when(photoDAO.getFavouritePhotoForFurniture(id)).thenReturn(photoDTO);
    assertEquals(photoDTO, photoUCC.getFavouritePhotoForFurniture(id));
  }


  /**
   * Success test : check if it exist a favorite photo. If it doesn't exist it return one visible photo.
   */
  @Test
  public void testGetFavouritePhotoForFurnitureV2() {
    int id = photoDTO.getId();
    Mockito.when(photoDAO.getFavouritePhotoForFurniture(id)).thenReturn(null);
    Mockito.when(photoDAO.getOneVisiblePhotoForFurniture(id)).thenReturn(photoDTO);
    assertEquals(photoDTO, photoUCC.getFavouritePhotoForFurniture(id));
  }

  /**
   * Success test :
   */
  @Test
  public void testGetAllVisiblePhotoForV1() {
    int idPhoto = photoDTO.getId();
    List<PhotoDTO> listPhoto = new ArrayList<PhotoDTO>();
    Mockito.when(photoDAO.getAllVisiblePhotosFor(idPhoto, 1)).thenReturn(listPhoto);
    assertEquals(listPhoto, photoUCC.getAllVisiblePhotosFor(idPhoto, 1));
  }

  /**
   * Success test : Photo is well added and linked to photoFurniture.
   */
  @Test
  public void testAddV1() {
    Mockito.when(photoDAO.add(photoDTO)).thenReturn(photoDTO);
    photoFurnitureDTO.setFurnitureId(photoDTO.getId());
    Mockito.when(photoFurnitureDAO.add(photoFurnitureDTO)).thenReturn(photoFurnitureDTO);
    assertEquals(photoDTO, photoUCC.add(photoDTO, photoFurnitureDTO));
  }

  /**
   * Fail test : Photo doesn't add.
   */
  @Test
  public void testAddV2() {
    Mockito.when(photoDAO.add(photoDTO)).thenReturn(null);
    assertThrows(BusinessException.class, () -> photoUCC.add(photoDTO, photoFurnitureDTO));
  }


  /**
   * Fail test : Photo_Furniture doesn't add.
   */
  @Test
  public void testAddV3() {
    Mockito.when(photoDAO.add(photoDTO)).thenReturn(photoDTO);
    photoFurnitureDTO.setFurnitureId(photoDTO.getId());
    Mockito.when(photoFurnitureDAO.add(photoFurnitureDTO)).thenReturn(null);
    assertThrows(BusinessException.class, () -> photoUCC.add(photoDTO, photoFurnitureDTO));
  }

  /**
   * Success Test : All photos are well added and linked to photoFurniture.
   */
  @Test
  public void testAddMultiplePhotosForFurnitureV1() {
    List<PhotoDTO> photos = new ArrayList<PhotoDTO>();
    List<PhotoFurnitureDTO> photoFurnitures = new ArrayList<PhotoFurnitureDTO>();
    List<PhotoDTO> photoToReturn = new ArrayList<PhotoDTO>();

    int i = 0;
    while (i < photos.size()) {
      Mockito.when(photoDAO.add(photoDTO)).thenReturn(photoDTO);
      photoFurnitureDTO.setFurnitureId(photoDTO.getId());
      Mockito.when(photoFurnitureDAO.add(photoFurnitureDTO)).thenReturn(photoFurnitureDTO);
      assertEquals(photoToReturn, photoUCC.addMultiplePhotosForFurniture(photos, photoFurnitures));
      i++;
    }
  }

  /**
   * Fail Test : Photo_Furniture doesn't add.
   */
  @Test
  public void testAddMultiplePhotosForFurnitureV2() {
    List<PhotoDTO> photos = new ArrayList<PhotoDTO>();
    List<PhotoFurnitureDTO> photoFurnitures = new ArrayList<PhotoFurnitureDTO>();


    int i = 0;
    while (i < photos.size()) {
      Mockito.when(photoDAO.add(photoDTO)).thenReturn(photoDTO);
      photoFurnitureDTO.setFurnitureId(photoDTO.getId());
      Mockito.when(photoFurnitureDAO.add(photoFurnitureDTO)).thenReturn(null);
      assertThrows(BusinessException.class,
          () -> photoUCC.addMultiplePhotosForFurniture(photos, photoFurnitures));
      i++;
    }
  }



}
