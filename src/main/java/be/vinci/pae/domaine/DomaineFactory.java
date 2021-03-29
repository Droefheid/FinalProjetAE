package be.vinci.pae.domaine;

public interface DomaineFactory {
  UserDTO getUserDTO();

  Adress getAdress();
  
  FurnitureDTO getFurnitureDTO();
}
