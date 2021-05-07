package be.vinci.pae.domaine.photo;

public class PhotoVisitImpl implements PhotoVisitDTO {

  private int visitId;
  private int photoId;

  @Override
  public int getVisitId() {
    return this.visitId;
  }

  @Override
  public void setVisitId(int visitId) {
    this.visitId = visitId;
  }

  @Override
  public int getPhotoId() {
    return this.photoId;
  }

  @Override
  public void setPhotoId(int photoId) {
    this.photoId = photoId;
  }

}
