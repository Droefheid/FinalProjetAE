package be.vinci.pae.domaine.photo;

import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.PhotoFurnitureDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class PhotoFurnitureUCCImpl implements PhotoFurnitureUCC {

  @Inject
  private DalServices dalservices;

  @Inject
  private PhotoFurnitureDAO photoFurnitureDAO;

  @Override
  public PhotoFurnitureDTO findById(int id) {
    dalservices.startTransaction();
    PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.findById(id);
    if (photoFurnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo doesn't exist", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return photoFurnitureDTO;
  }

  @Override
  public PhotoFurnitureDTO updateFavorite(PhotoFurnitureDTO photoFurniture) {
    dalservices.startTransaction();
    PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.updateFavorite(photoFurniture);
    if (photoFurnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo_furniture doesn't exist", Status.BAD_REQUEST); // TODO bon msg?
    }
    dalservices.commitTransaction();
    return photoFurnitureDTO;
  }

  @Override
  public PhotoFurnitureDTO updateVisibility(PhotoFurnitureDTO photoFurniture) {
    dalservices.startTransaction();
    PhotoFurnitureDTO photoFurnitureDTO = photoFurnitureDAO.updateVisibility(photoFurniture);
    if (photoFurnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Photo_furniture doesn't exist", Status.BAD_REQUEST); // TODO bon msg?
    }
    dalservices.commitTransaction();
    return photoFurnitureDTO;
  }

  @Override
  public void removeFavouriteFormFurniture(int id) {
    dalservices.startTransaction();
    photoFurnitureDAO.removeFavouriteFormFurniture(id);
    dalservices.commitTransaction();
  }

}
