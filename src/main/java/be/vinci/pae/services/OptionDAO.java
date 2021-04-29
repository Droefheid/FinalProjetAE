package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.option.OptionDTO;

public interface OptionDAO {

  int introduceOption(OptionDTO option);

  int findOptionIdByInfo(OptionDTO option);

  OptionDTO findOptionByID(int optionID);

  OptionDTO findOptionByFurnitureIdANDCustomerId(int furnitureID, int customerID);

  void stopOption(OptionDTO option);

  void changeFurnitureState(String state, int furnitureID);

  List<OptionDTO> listOfOptionsFromSameCustomerAndFurniture(OptionDTO option);
}

