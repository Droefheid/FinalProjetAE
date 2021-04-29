package be.vinci.pae.domaine.photo;

public interface PhotoFurnitureUCC {

  PhotoFurnitureDTO findById(int id);

  PhotoFurnitureDTO updateFavorite(PhotoFurnitureDTO photoFurniture);

  PhotoFurnitureDTO updateVisibility(PhotoFurnitureDTO photoFurniture);

  void removeFavouriteFormFurniture(int id);

}
