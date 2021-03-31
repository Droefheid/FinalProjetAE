package be.vinci.pae.domaine;

import org.glassfish.grizzly.http.util.HttpStatus;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.OptionDAO;
import jakarta.inject.Inject;

public class OptionUCCImpl implements OptionUCC {


  @Inject
  private OptionDAO optionDao;

  @Inject
  private DalServices dalservices;

  @Override
  public void introduceOption(OptionDTO option) {
    dalservices.startTransaction();
    int optionID = optionDao.findOptionByInfo(option);
    if (optionID <= 0) {
      dalservices.rollbackTransaction();
      throw new BusinessException("This furniture is already reserved", HttpStatus.BAD_REQUEST_400);
    }
    optionDao.introduceOption(option);
    optionDao.changeFurnitureState("O", option.getFurniture());
    dalservices.commitTransaction();
  }

  @Override
  public OptionDTO stopOption() {
    return null;
  }

}
