package be.vinci.pae.domaine;

import java.util.List;

public interface FurnitureUCC {

  FurnitureDTO add(FurnitureDTO furniture);

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  FurnitureDTO update(FurnitureDTO furniture);

  Object[] getAllInfosForUpdate(int id);

}
