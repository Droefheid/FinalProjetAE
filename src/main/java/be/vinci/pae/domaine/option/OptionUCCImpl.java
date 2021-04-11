package be.vinci.pae.domaine.option;

import java.time.LocalDateTime;
import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.domaine.furniture.FurnitureDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.FurnitureDAO;
import be.vinci.pae.services.OptionDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

public class OptionUCCImpl implements OptionUCC {


  @Inject
  private OptionDAO optionDao;

  @Inject
  private FurnitureDAO furnitureDao;

  @Inject
  private DalServices dalservices;

  @Override
  public void introduceOption(OptionDTO option) {
    dalservices.startTransaction();
    FurnitureDTO furniture = furnitureDao.findById(option.getFurniture());
    if (furniture == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("This furniture doesn't exist!", Status.BAD_REQUEST);
    }
    if (furniture.getState().equals("O")) {
      dalservices.rollbackTransaction();
      throw new BusinessException("This furniture is already reserved", Status.BAD_REQUEST);
    }
    if (furniture.getState().equals("V")) {
      dalservices.rollbackTransaction();
      throw new BusinessException("This furniture has already been sold", Status.BAD_REQUEST);
    }

    List<OptionDTO> list = optionDao.listOfOptionsFromSameCustomerAndFurniture(option);

    int d = 0;
    for (int i = 0; i < list.size(); i++) {
      OptionDTO o = list.get(i);
      LocalDateTime beginning = o.getBeginningOptionDate().toLocalDateTime();
      LocalDateTime term = o.getOptionTerm().toLocalDateTime();
      d += term.getDayOfYear() - beginning.getDayOfYear();
    }

    if (d >= 5) {
      throw new BusinessException(
          "You have already reserved this" + " furniture for more than 5 days");
    }

    optionDao.introduceOption(option);
    optionDao.changeFurnitureState("O", option.getFurniture());
    dalservices.commitTransaction();
  }

  @Override
  public OptionDTO getOption(int optionID) {
    dalservices.startTransaction();
    OptionDTO option = optionDao.findOptionByID(optionID);
    if (option == null) {
      dalservices.rollbackTransaction();
      throw new BusinessException("Option doesn't exist");
    }
    dalservices.commitTransaction();
    return option;
  }

  @Override
  public void deleteOption(int optionID) {
    dalservices.startTransaction();
    optionDao.deleteOption(optionID);
    dalservices.commitTransaction();
  }

}
