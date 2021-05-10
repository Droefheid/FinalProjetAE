package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.BusinessException;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.visit.VisitDTO;
import jakarta.inject.Inject;

public class VisitDAOImpl implements VisitDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;


  @Override
  public VisitDTO findByDate(Timestamp requestDate) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, " + "request_date, time_slot, "
            + "date_and_hours_visit, " + "explanatory_note, label_furniture, is_confirmed,"
            + " users, address" + " FROM projet.visits WHERE request_date = ?");
    VisitDTO visit = domaineFactory.getVisitDTO();
    try {
      ps.setTimestamp(1, requestDate);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          visit = fullFillVisitFromResulSet(visit, rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    if (visit.getRequestDate() == null) {
      return null;
    }
    return visit;
  }

  @Override
  public VisitDTO findById(int id) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, " + "request_date, time_slot,"
            + " date_and_hours_visit, explanatory_note, label_furniture, is_confirmed,"
            + " users, address" + " FROM projet.visits WHERE visit_id = ?");
    VisitDTO visit = domaineFactory.getVisitDTO();
    try {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          visit = fullFillVisitFromResulSet(visit, rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return visit;
  }

  private VisitDTO fullFillVisitFromResulSet(VisitDTO visit, ResultSet rs) throws SQLException {
    visit.setId(rs.getInt(1));
    visit.setRequestDate(rs.getTimestamp(2));
    visit.setTimeSlot(rs.getString(3));
    visit.setDateAndHoursVisit(rs.getTimestamp(4));
    visit.setExplanatoryNote(rs.getString(5));
    visit.setLabelFurniture(rs.getString(6));
    visit.setIsConfirmed(rs.getBoolean(7));
    visit.setUserId(rs.getInt(8));
    visit.setAddressId(rs.getInt(9));
    return visit;
  }


  @Override
  public VisitDTO introduceVisit(VisitDTO visit) {

    if (findByDate(visit.getRequestDate()) != null) {
      throw new BusinessException("There is already a visite planned for that date!");
    }

    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO " + "projet.visits VALUES(DEFAULT,?,?,NULL,?,?,DEFAULT,?,?)");
    // label furniture a mettre par defaut , mais pas a null
    try {
      ps.setTimestamp(1, visit.getRequestDate());
      ps.setString(2, visit.getTimeSlot());
      ps.setString(3, visit.getExplanatoryNote());
      ps.setString(4, visit.getLabelFurniture());
      ps.setInt(5, visit.getUserId());
      ps.setInt(6, visit.getAddressId());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return findByDate(visit.getRequestDate());
  }


  @Override
  public List<VisitDTO> getAll() {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, request_date, time_slot, date_and_hours_visit,"
            + " explanatory_note, label_furniture, is_confirmed," + " users, address"
            + " FROM projet.visits");


    List<VisitDTO> list = new ArrayList<VisitDTO>();

    try {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        VisitDTO visit = domaineFactory.getVisitDTO();
        fullFillVisitFromResulSet(visit, rs);
        list.add(visit);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillVisits", e);
    }

    return list;
  }

  @Override
  public List<VisitDTO> getAllMyVisits(int userId) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, request_date, time_slot, date_and_hours_visit,"
            + " explanatory_note, label_furniture, is_confirmed," + " users, address"
            + " FROM projet.visits WHERE users = ?");


    List<VisitDTO> list = new ArrayList<VisitDTO>();

    try {
      ps.setInt(1, userId);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        VisitDTO visit = domaineFactory.getVisitDTO();
        visit = fullFillVisitFromResulSet(visit, rs);
        list.add(visit);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillMyVisits", e);
    }

    return list;
  }

  @Override
  public void updateConfirmed(VisitDTO visit) {
    PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("UPDATE projet.visits SET is_confirmed = ? , "
            + "explanatory_note=? , " + "date_and_hours_visit=? WHERE visit_id = ?");

    try {
      ps.setBoolean(1, visit.getIsConfirmed());
      ps.setString(2, visit.getExplanatoryNote());
      ps.setTimestamp(3, visit.getDateAndHoursVisit());
      ps.setInt(4, visit.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }

  }

  @Override
  public List<VisitDTO> getAllNotConfirmed() {
    // explanatory note and dateAndHoursVisit should be empty
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, request_date, time_slot, date_and_hours_visit,"
            + " explanatory_note, label_furniture, is_confirmed," + " users, address"
            + " FROM projet.visits WHERE is_confirmed = ?"
            + " AND (date_and_hours_visit IS NULL AND explanatory_note IS NULL)");


    List<VisitDTO> list = new ArrayList<VisitDTO>();

    try {
      ps.setBoolean(1, false);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        VisitDTO visit = domaineFactory.getVisitDTO();
        fullFillVisitFromResulSet(visit, rs);
        list.add(visit);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillUsers", e);
    }

    return list;
  }

  @Override
  public List<VisitDTO> getAllConfirmed() {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT visit_id, request_date, time_slot, date_and_hours_visit,"
            + " explanatory_note, label_furniture, is_confirmed," + " users, address"
            + " FROM projet.visits WHERE is_confirmed = ?");


    List<VisitDTO> list = new ArrayList<VisitDTO>();


    try {
      ps.setBoolean(1, true);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        VisitDTO visit = domaineFactory.getVisitDTO();
        fullFillVisitFromResulSet(visit, rs);
        list.add(visit);
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillUsers", e);
    }

    return list;
  }

  @Override
  public void delete(int visitId) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("DELETE FROM projet.visits WHERE visit_id = ?");
    try {
      ps.setInt(1, visitId);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
  }

}

