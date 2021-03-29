package be.vinci.pae.domaine;

public interface DomaineFactory {
  UserDTO getUserDTO();

  Address getAdress();

  FurnitureDTO getFurnitureDTO();
}
