package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.option.OptionDTO;

public interface OptionDAO {

  void introduceOption(OptionDTO option);

  int findOptionByInfo(OptionDTO option);

  void changeFurnitureState(String state, int furnitureID);

  List<OptionDTO> listOfOptionsFromSameCustomerAndFurniture(OptionDTO option);
}

