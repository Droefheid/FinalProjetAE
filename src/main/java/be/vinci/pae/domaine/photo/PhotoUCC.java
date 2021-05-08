package be.vinci.pae.domaine.photo;

import java.util.List;

public interface PhotoUCC {

  PhotoDTO findById(int id);

  PhotoDTO getFavouritePhotoForFurniture(int furnitureId);

  List<PhotoDTO> getAllVisiblePhotosFor(int furnitureId, int client);

  PhotoDTO add(PhotoDTO photo, PhotoFurnitureDTO photoFurniture);

  List<PhotoDTO> addMultiplePhotosForFurniture(List<PhotoDTO> photo,
      List<PhotoFurnitureDTO> photoFurniture);

  List<PhotoDTO> addMultiplePhotosForVisit(List<PhotoDTO> photo, List<PhotoVisitDTO> photoVisit);

  void delete(int id);

}
