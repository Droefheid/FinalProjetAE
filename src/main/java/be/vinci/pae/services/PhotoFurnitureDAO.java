package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;

public interface PhotoFurnitureDAO {

  PhotoFurnitureDTO findById(int id);

  List<PhotoFurnitureDTO> getAllForFurniture(int id);

  PhotoFurnitureDTO add(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO update(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO delete(int id);

}
