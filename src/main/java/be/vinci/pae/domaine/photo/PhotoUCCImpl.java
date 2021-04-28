package be.vinci.pae.domaine.photo;

import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.PhotoDAO;
import be.vinci.pae.services.PhotoFurnitureDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class PhotoUCCImpl implements PhotoUCC {

  @Inject
  private DalServices dalservices;

  @Inject
  private PhotoDAO photoDAO;

  @Inject
  private PhotoFurnitureDAO photoFurnitureDAO;



  @Override
  public PhotoDTO findById(int id) {
    dalservices.startTransaction();
    PhotoDTO photoDTO = photoDAO.findById(id);
    if (photoDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo doesn't exist", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return photoDTO;
  }

  @Override
  public PhotoDTO add(PhotoDTO photo, PhotoFurnitureDTO photoFurniture) {
    dalservices.startTransaction();

    PhotoDTO photoDTO = photoDAO.add(photo);
    if (photoDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo doesn't add", Status.BAD_REQUEST);
    }

    // Link to photo.
    photoFurniture.setPhotoId(photoDTO.getId());
    PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.add(photoFurniture);
    if (photoFurnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo_Furniture doesn't add", Status.BAD_REQUEST);
    }

    dalservices.commitTransaction();
    return photoDTO;
  }

  @Override
  public List<PhotoDTO> addMultiple(List<PhotoDTO> photos,
      List<PhotoFurnitureDTO> photosFurniture) {
    dalservices.startTransaction();

    int i = 0;
    List<PhotoDTO> addedPhotos = new ArrayList<>();
    while (i < photos.size()) {
      PhotoDTO photo = photos.get(i);
      PhotoFurnitureDTO photoFurniture = photosFurniture.get(i);
      PhotoDTO photoDTO = photoDAO.add(photo);
      if (photoDTO == null) {
        dalservices.rollbackTransaction();
        throw new BusinessException("Photo doesn't add", Status.BAD_REQUEST);
      }

      // Link to photo.
      photoFurniture.setPhotoId(photoDTO.getId());
      PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.add(photoFurniture);
      if (photoFurnitureDTO == null) {
        dalservices.rollbackTransaction();
        throw new BusinessException("Photo_Furniture doesn't add", Status.BAD_REQUEST);
      }
      addedPhotos.add(photoDTO);

      i++;
    }

    dalservices.commitTransaction();
    return addedPhotos;
  }

  public void delete(int id) {
    dalservices.startTransaction();

    PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.delete(id);
    if (photoFurnitureDTO != null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo_Furniture doesn't delete", Status.BAD_REQUEST);
    }
    PhotoDTO photoDTO = photoDAO.delete(id);
    if (photoDTO != null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo doesn't delete", Status.BAD_REQUEST);
    }

    dalservices.commitTransaction();
  }

}
