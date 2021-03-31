package be.vinci.pae.domaine;

public interface DomaineFactory {
  UserDTO getUserDTO();

  FurnitureDTO getFurnitureDTO();

  AddressDTO getAdressDTO();

  OptionDTO getOptionDTO();
}
