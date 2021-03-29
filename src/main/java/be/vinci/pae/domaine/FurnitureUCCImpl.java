package be.vinci.pae.domaine;

import be.vinci.pae.services.FurnitureDAO;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private FurnitureDAO furnitureDao;

  @Override
  public FurnitureDTO add(FurnitureDTO furniture) {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public FurnitureDTO getById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FurnitureDTO getAll() {
    FurnitureDTO furnitureList = this.furnitureDao.getAll();
    if (furnitureList == null) {
      return null;
    }
    return furnitureList;
  }

}
