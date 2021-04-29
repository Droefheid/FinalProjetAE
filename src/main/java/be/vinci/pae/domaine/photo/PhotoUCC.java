package be.vinci.pae.domaine.photo;

import java.util.List;

public interface PhotoUCC {

  PhotoDTO findById(int id);

  PhotoDTO add(PhotoDTO photo, PhotoFurnitureDTO photoFurniture);

  List<PhotoDTO> addMultiple(List<PhotoDTO> photo, List<PhotoFurnitureDTO> photoFurniture);

  void delete(int id);

}
