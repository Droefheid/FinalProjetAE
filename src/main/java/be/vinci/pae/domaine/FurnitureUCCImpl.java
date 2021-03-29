package be.vinci.pae.domaine;

import java.util.List;
import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.FurnitureDAO;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  FurnitureDAO furnitureDAO;

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    FurnitureDTO furnitureDTO = furnitureDAO.add(furniture);
    if (furnitureDTO == null) {
      throw new BusinessException("furniture doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    return furnitureDTO;
  }

  @Override
  public FurnitureDTO findById(int id) {
    FurnitureDTO furnitureDTO = furnitureDAO.findById(id);
    if (furnitureDTO == null) {
      throw new BusinessException("furniture doesn't exist", HttpStatus.BAD_REQUEST_400);
    }
    return furnitureDTO;
  }

  @Override
  public List<FurnitureDTO> getAll() {
    List<FurnitureDTO> list = furnitureDAO.getAll();
    return list;
  }

}
