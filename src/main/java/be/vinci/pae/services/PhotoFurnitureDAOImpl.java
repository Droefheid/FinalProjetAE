package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.photo.PhotoFurnitureDTO;
import jakarta.inject.Inject;

public class PhotoFurnitureDAOImpl implements PhotoFurnitureDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;


  @Override
  public PhotoFurnitureDTO findById(int id) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT photo_id," + " is_visible, is_favourite_photo, furniture"
            + " FROM projet.photos_furniture " + " WHERE photo_id = ?");

    PhotoFurnitureDTO photo = null;
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          photo = fullFillPhotoFurniture(rs);
        }
      }
    } catch (SQLException e) {
      throw new FatalException("Error findById", e);
    }
    return photo;
  }

  @Override
  public List<PhotoFurnitureDTO> getAllForFurniture(int id) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT photo_id," + " is_visible, is_favourite_photo, furniture"
            + " FROM projet.photos_furniture WHERE furniture = ?" + " ORDER BY photo_id");

    List<PhotoFurnitureDTO> list = new ArrayList<PhotoFurnitureDTO>();

    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoFurnitureDTO photoFurniture = fullFillPhotoFurniture(rs);
          list.add(photoFurniture);
        }
      } catch (SQLException e) {
        throw new FatalException("Error getAllForFurniture", e);
      }
    } catch (SQLException e) {
      throw new FatalException("Error setInt in getAllForFurniture", e);
    }
    return list;
  }

  @Override
  public PhotoFurnitureDTO add(PhotoFurnitureDTO photoFurniture) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.photos_furniture" + " VALUES(?,?,?,?)");

    try {
      ps = setAllPsAttributNotNull(ps, photoFurniture);

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException("Error add photo_furniture", e);
    }
    return findById(photoFurniture.getPhotoId());
  }

  @Override
  public PhotoFurnitureDTO update(PhotoFurnitureDTO photoFurniture) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PhotoFurnitureDTO delete(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  private PhotoFurnitureDTO fullFillPhotoFurniture(ResultSet rs) {
    PhotoFurnitureDTO photoFurniture = domaineFactory.getPhotoFurnitureDTO();

    try {
      photoFurniture.setPhotoId(rs.getInt(1));
      photoFurniture.setVisible(rs.getBoolean(2));
      photoFurniture.setFavourite(rs.getBoolean(3));
      photoFurniture.setFurnitureId(rs.getInt(4));

    } catch (SQLException e) {
      throw new FatalException("Error fullFillPhoto", e);
    }

    return photoFurniture;
  }

  private PreparedStatement setAllPsAttributNotNull(PreparedStatement ps,
      PhotoFurnitureDTO photoFurniture) throws SQLException {
    ps.setBoolean(1, photoFurniture.isVisible());
    ps.setBoolean(2, photoFurniture.isFavourite());
    ps.setInt(3, photoFurniture.getPhotoId());
    ps.setInt(4, photoFurniture.getFurnitureId());

    return ps;
  }

}
