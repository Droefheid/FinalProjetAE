package be.vinci.pae.domaine.photo;

public interface PhotoFurnitureDTO {

  int getPhotoId();

  void setPhotoId(int photoId);

  int getFurnitureId();

  void setFurnitureId(int furnitureId);

  boolean isVisible();

  void setVisible(boolean isVisible);

  boolean isFavourite();

  void setFavourite(boolean isFavourite);

}
