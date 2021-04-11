package be.vinci.pae.domaine.option;

public interface OptionUCC {

  void introduceOption(OptionDTO option);

  OptionDTO getOption(int optionID);

  void stopOption(OptionDTO option);
}
