package be.vinci.pae.domaine.furniture;

import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.TypeDAO;
import be.vinci.pae.services.UserDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private FurnitureDAO furnitureDAO;

  @Inject
  private TypeDAO typeDAO;

  @Inject
  private UserDAO userDAO;

  @Inject
  private DalServices dalservices;

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    dalservices.startTransaction();
    FurnitureDTO furnitureDTO = furnitureDAO.add(furniture);
    if (furnitureDTO == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("furniture doesn't exist", Status.BAD_REQUEST);
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
      throw new BusinessException("furniture doesn't exist", Status.BAD_REQUEST);
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
      throw new BusinessException("Furniture doesn't exist", Status.BAD_REQUEST);
    }
    dalservices.commitTransaction();
    return furnitureDTO;
  }

  @Override
  public Object[] getAllInfosForUpdate(int id) {
    dalservices.startTransaction();
    Object[] allLists = new Object[3];
    int i = 0;
    allLists[i++] = furnitureDAO.findById(id);
    allLists[i++] = typeDAO.getAll();
    allLists[i++] = userDAO.getAll();
    dalservices.commitTransaction();
    return allLists;
  }

}
