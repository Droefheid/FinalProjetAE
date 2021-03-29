package be.vinci.pae.domaine;

public interface DomaineFactory {
  UserDTO getUserDTO();
  
  FurnitureDTO getFurnitureDTO();
  
  Address getAdress();
}