package be.vinci.pae.domaine.option;

public interface OptionUCC {

  void introduceOption(OptionDTO option);

  OptionDTO getOption(int optionID);

  OptionDTO findOption(int furnitureID, int customerID);

  void stopOption(OptionDTO option);

  void changeOptionState(OptionDTO option);
}
