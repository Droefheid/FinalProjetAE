package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.furniture.FurnitureDTO;

public interface FurnitureDAO {

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  FurnitureDTO add(FurnitureDTO furniture);

  FurnitureDTO update(FurnitureDTO furniture);

  List<FurnitureDTO> getMyFurniture(int userID);

  FurnitureDTO findByFurnitureInfo(FurnitureDTO furnitureDTO);

  List<FurnitureDTO> getBoughtFurniture(int id);


}
