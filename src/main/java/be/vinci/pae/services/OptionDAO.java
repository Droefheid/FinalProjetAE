package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.option.OptionDTO;

public interface OptionDAO {

  void introduceOption(OptionDTO option);

  int findOptionIdByInfo(OptionDTO option);

  OptionDTO findOptionByID(int optionID);

  void deleteOption(int optionID);

  void changeFurnitureState(String state, int furnitureID);

  List<OptionDTO> listOfOptionsFromSameCustomerAndFurniture(OptionDTO option);
}

