package be.vinci.pae;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureUCC;
import be.vinci.pae.domaine.photo.PhotoUCC;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoFurnitureDAO;

public class TestPhoto {

  private DomaineFactory domaineFactory;
  private PhotoDAO photoDAO;
  private PhotoUCC photoUCC;
  private PhotoDTO photoDTO;

  private PhotoFurnitureDTO photoFurnitureDTO;
  private PhotoFurnitureDAO photoFurnitureDAO;
  private PhotoFurnitureUCC photoFurnitureUCC;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new MockApplicationBinder());
    this.domaineFactory = locator.getService(DomaineFactory.class);
    this.photoUCC = locator.getService(PhotoUCC.class);
    this.photoDAO = locator.getService(PhotoDAO.class);
    this.photoFurnitureUCC = locator.getService(PhotoFurnitureUCC.class);
    this.photoFurnitureDAO = locator.getService(PhotoFurnitureDAO.class);

    photoDTO = domaineFactory.getPhotoDTO();
    photoFurnitureDTO = domaineFactory.getPhotoFurnitureDTO();

  }

}
