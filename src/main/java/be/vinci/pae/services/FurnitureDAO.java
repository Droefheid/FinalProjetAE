package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.FurnitureDTO;

public interface FurnitureDAO {

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  FurnitureDTO add(FurnitureDTO furniture);

}
