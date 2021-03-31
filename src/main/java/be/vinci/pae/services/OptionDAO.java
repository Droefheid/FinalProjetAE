package be.vinci.pae.services;

import be.vinci.pae.domaine.OptionDTO;

public interface OptionDAO {

  void introduceOption(OptionDTO option);

  int findOptionByInfo(OptionDTO option);

  void changeFurnitureState(String state, int furnitureID);
}

