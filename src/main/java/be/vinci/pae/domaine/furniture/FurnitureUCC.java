package be.vinci.pae.domaine.furniture;

import java.util.List;

public interface FurnitureUCC {

  FurnitureDTO add(FurnitureDTO furniture);

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  List<FurnitureDTO> getMyFurniture(int userID);

  FurnitureDTO update(FurnitureDTO furniture);

  Object[] getAllInfosForUpdate(int id);

  Object[] getAllInfosForAdd();



}
