package be.vinci.pae.domaine.furniture;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoDTO;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;

public interface FurnitureUCC {

  FurnitureDTO add(FurnitureDTO furniture);

  FurnitureDTO findById(int id);

  List<FurnitureDTO> getAll();

  FurnitureDTO update(FurnitureDTO furniture, List<PhotoDTO> photos,
      PhotoFurnitureDTO photoFurniture);

  List<FurnitureDTO> getMyFurniture(int userID);

  Object[] getAllInfosForUpdate(int id);

  Object[] getAllInfosForAdd();



}
