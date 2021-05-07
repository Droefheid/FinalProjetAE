package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.photo.PhotoDTO;
import jakarta.inject.Inject;

public class PhotoDAOImpl implements PhotoDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;



  @Override
  public PhotoDTO findByName(String name) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT photo_id," + " pictures, name" + " FROM projet.photos WHERE name = ?");
    PhotoDTO photo = domaineFactory.getPhotoDTO();
    try {
      ps.setString(1, name);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          photo = fullFillPhoto(rs);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error findById", e);
    }
    if (photo.getName() == null) {
      return null;
    }
    return photo;
  }

  @Override
  public PhotoDTO findById(int id) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT photo_id," + " pictures, name" + " FROM projet.photos WHERE photo_id = ?");
    PhotoDTO photo = null;
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          photo = fullFillPhoto(rs);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error findById", e);
    }
    return photo;
  }

  @Override
  public List<PhotoDTO> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet!");
  }

  @Override
  public List<PhotoDTO> getAllForFurniture(int id) {
    PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT p.photo_id," + " p.pictures, p.name"
            + " FROM projet.photos p, projet.photos_furniture pf WHERE p.photo_id = pf.photo_id"
            + " AND pf.furniture = ?" + " ORDER BY p.photo_id");

    List<PhotoDTO> list = new ArrayList<PhotoDTO>();

    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDTO photoFurniture = fullFillPhoto(rs);
          list.add(photoFurniture);
        }
      } catch (SQLException e) {
        ((DalServices) dalBackendServices).rollbackTransaction();
        throw new FatalException("Error getAllForFurniture", e);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error setInt in getAllForFurniture", e);
    }
    return list;
  }


  @Override
  public List<PhotoDTO> getAllForVisit(int id) {
    PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT p.photo_id," + " p.pictures, p.name"
            + " FROM projet.photos p, projet.photos_visits pv WHERE p.photo_id = pv.photo"
            + " AND pv.visit = ?" + " ORDER BY p.photo_id");

    List<PhotoDTO> list = new ArrayList<PhotoDTO>();

    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoDTO photoFurniture = fullFillPhoto(rs);
          list.add(photoFurniture);
        }
      } catch (SQLException e) {
        ((DalServices) dalBackendServices).rollbackTransaction();
        throw new FatalException("Error getAllForVisit", e);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error setInt in getAllForVisit", e);
    }
    return list;
  }

  @Override
  public PhotoDTO add(PhotoDTO photo) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.photos" + " VALUES(DEFAULT,?,?)");

    try {
      setAllPsAttributNotNull(ps, photo);

      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error add photo", e);
    }
    return findByName(photo.getName());
  }

  @Override
  public PhotoDTO update(PhotoDTO furniture) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not implemented yet!");
  }

  @Override
  public PhotoDTO delete(int id) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("DELETE FROM projet.photos" + " WHERE photo_id = ?");

    try {
      ps.setInt(1, id);

      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error delete photo", e);
    }
    return findById(id);
  }

  private PhotoDTO fullFillPhoto(ResultSet rs) {
    PhotoDTO photo = domaineFactory.getPhotoDTO();

    try {
      photo.setId(rs.getInt(1));
      photo.setPicture(rs.getString(2));
      photo.setName(rs.getString(3));

    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error fullFillPhoto", e);
    }

    return photo;
  }

  private PreparedStatement setAllPsAttributNotNull(PreparedStatement ps, PhotoDTO photo)
      throws SQLException {
    ps.setString(1, photo.getPicture());
    ps.setString(2, photo.getName());

    return ps;
  }

}
