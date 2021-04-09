package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domaine.photo.PhotoDTO;

public interface PhotoDAO {

  PhotoDTO findById(int id);

  List<PhotoDTO> getAll();

  List<PhotoDTO> getAllForFurniture(int id);

  PhotoDTO add(PhotoDTO photo);

  PhotoDTO update(PhotoDTO furniture);

  PhotoDTO delete(int id);

}
