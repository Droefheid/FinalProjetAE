package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoVisitDTO;

public interface PhotoVisitDAO {

  PhotoVisitDTO findByPhotoId(int photoId);

  List<PhotoVisitDTO> getAllForVisit(int visitId);

  PhotoVisitDTO add(PhotoVisitDTO photoVisit);

}
