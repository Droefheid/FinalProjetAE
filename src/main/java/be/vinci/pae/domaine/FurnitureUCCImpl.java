package be.vinci.pae.domaine;

import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.FurnitureDAO;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  FurnitureDAO furnitureDAO;

  @Inject
  private DalServices dalservices;

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    dalservices.startTransaction();
    FurnitureDTO furnitureDTO = furnitureDAO.add(furniture);
    if (furnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("furniture doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return furnitureDTO;
  }

  @Override
  public FurnitureDTO findById(int id) {
    dalservices.startTransaction();
    FurnitureDTO furnitureDTO = furnitureDAO.findById(id);
    if (furnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("furniture doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return furnitureDTO;
  }

  @Override
  public List<FurnitureDTO> getAll() {
    dalservices.startTransaction();
    List<FurnitureDTO> list = furnitureDAO.getAll();
    dalservices.commitTransaction();
    return list;
  }

  @Override
  public FurnitureDTO update(FurnitureDTO furniture) {
    dalservices.startTransaction();
    FurnitureDTO furnitureDTO = furnitureDAO.update(furniture);
    if (furnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Furniture doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    dalservices.commitTransaction();
    return furnitureDTO;
  }

}
