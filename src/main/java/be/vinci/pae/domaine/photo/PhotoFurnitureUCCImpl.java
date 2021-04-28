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

}
