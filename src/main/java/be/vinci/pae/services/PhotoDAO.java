package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoDTO;

public interface PhotoDAO {

  PhotoDTO findByName(String name);

  PhotoDTO findById(int id);

  PhotoDTO getFavouritePhotoForFurniture(int furnitureId);

  PhotoDTO getOneVisiblePhotoForFurniture(int furnitureId);

  List<PhotoDTO> getAll();

  List<PhotoDTO> getAllVisiblePhotosFor(int furnitureId, int client);

  List<PhotoDTO> getAllForFurniture(int furnitureId);

  List<PhotoDTO> getAllForVisit(int visitId);

  PhotoDTO add(PhotoDTO photo);

  PhotoDTO update(PhotoDTO furniture);

  PhotoDTO delete(int id);

}
