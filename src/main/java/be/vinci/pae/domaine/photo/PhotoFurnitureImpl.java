package be.vinci.pae.domaine.photo;

public class PhotoFurnitureImpl implements PhotoFurnitureDTO {

  private int photoId;
  private int furnitureId;
  private boolean isVisible;
  private boolean isFavourite;



  public int getPhotoId() {
    return photoId;
  }

  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

  public int getFurnitureId() {
    return furnitureId;
  }

  public void setFurnitureId(int furnitureId) {
    this.furnitureId = furnitureId;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  public boolean isFavourite() {
    return isFavourite;
  }

  public void setFavourite(boolean isFavourite) {
    this.isFavourite = isFavourite;
  }

  @Override
  public String toString() {
    return "PhotoFurnitureImpl [photoId=" + photoId + ", furnitureId=" + furnitureId
        + ", isVisible=" + isVisible + ", isFavourite=" + isFavourite + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + photoId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PhotoFurnitureImpl other = (PhotoFurnitureImpl) obj;
    if (photoId != other.photoId) {
      return false;
    }
    return true;
  }

}
