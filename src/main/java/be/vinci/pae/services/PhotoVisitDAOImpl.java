package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.photo.PhotoVisitDTO;
import jakarta.inject.Inject;

public class PhotoVisitDAOImpl implements PhotoVisitDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;



  @Override
  public PhotoVisitDTO findByPhotoId(int idPhoto) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT photo," + " visit" + " FROM projet.photos_visits " + " WHERE photo = ?");

    PhotoVisitDTO photo = null;
    try {
      ps.setInt(1, idPhoto);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          photo = fullFillPhotoVisit(rs);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error findById", e);
    }
    return photo;
  }

  @Override
  public List<PhotoVisitDTO> getAllForVisit(int visitId) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement("SELECT photo," + " visit"
        + " FROM projet.photos_visits WHERE visit = ?" + " ORDER BY photo");

    List<PhotoVisitDTO> list = new ArrayList<>();

    try {
      ps.setInt(1, visitId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PhotoVisitDTO photoFurniture = fullFillPhotoVisit(rs);
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
  public PhotoVisitDTO add(PhotoVisitDTO photoVisit) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.photos_visits" + " VALUES(?,?)");

    try {
      ps = setAllPsAttributNotNull(ps, photoVisit);

      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error add photo_visit", e);
    }
    return findByPhotoId(photoVisit.getPhotoId());
  }



  // ******************** Private's Methods ********************

  private PhotoVisitDTO fullFillPhotoVisit(ResultSet rs) {
    PhotoVisitDTO photoVisit = domaineFactory.getPhotoVisitDTO();

    try {
      photoVisit.setPhotoId(rs.getInt(1));
      photoVisit.setVisitId(rs.getInt(2));

    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("Error fullFillPhoto", e);
    }

    return photoVisit;
  }

  private PreparedStatement setAllPsAttributNotNull(PreparedStatement ps, PhotoVisitDTO photoVisit)
      throws SQLException {
    ps.setInt(1, photoVisit.getVisitId());
    ps.setInt(2, photoVisit.getPhotoId());

    return ps;
  }

}
