package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;

public interface PhotoFurnitureDAO {

  PhotoFurnitureDTO findById(int idPhoto);

  PhotoFurnitureDTO getFavoritePhoto(int furniture);

  List<PhotoFurnitureDTO> getAllForFurniture(int photoId);

  PhotoFurnitureDTO add(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO updateFavorite(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO updateVisibility(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO delete(int photoId);

  void removeFavouriteFormFurniture(int furnitureId);

}
