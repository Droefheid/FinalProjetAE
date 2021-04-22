package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.address.AddressDTO;
import be.vinci.pae.domaine.visit.VisitDTO;
import jakarta.inject.Inject;

public class VisitDAOImpl implements VisitDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;


  @Override
  public VisitDTO findByDate(Timestamp request_date) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed,"
            + " users, address" + "FROM projet.visits WHERE request_date = ?");
    VisitDTO visit = domaineFactory.getVisitDTO();
    try {
      ps.setTimestamp(1, request_date);
      visit = fullFillVisitFromResulSet(visit, ps);
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
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT visit_id, request_date, time_slot, date_and_hours_visit, explanatory_note, label_furniture, is_confirmed,"
            + " users, address" + "FROM projet.visits WHERE visit_id = ?");
    VisitDTO visit = domaineFactory.getVisitDTO();
    try {
      ps.setInt(1, id);
      visit = fullFillVisitFromResulSet(visit, ps);
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return visit;
  }

  private VisitDTO fullFillVisitFromResulSet(VisitDTO visit, PreparedStatement ps)
      throws SQLException {
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        visit.setId(rs.getInt(1));
        visit.setRequestDate(rs.getTimestamp(2));
        visit.setTimeSLot(rs.getString(3));
        visit.setDateAndHoursVisit(rs.getTimestamp(4));
        visit.setExplanatoryNote(rs.getString(5));
        visit.setLabelFurniture(rs.getString(6));
        visit.setIsConfirmed(rs.getBoolean(7));
        visit.setUserId(rs.getInt(8));
        visit.setAddressId(rs.getInt(9));

      }
    }
    return visit;
  }


  @Override
  public VisitDTO introduceVisit(VisitDTO visit) {
    if (findByDate(visit.getRequestDate()) != null) {
      return null;
    }

    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO projet.visits VALUES(DEFAULT,?,DEFAULT,DEFAULT,?,DEFAULT,DEFAULT,?,?)");
    try {
      ps.setTimestamp(1, visit.getRequestDate());
      ps.setString(2, visit.getExplanatoryNote());
      ps.setInt(3, visit.getUserId());
      ps.setInt(4, visit.getAddressId());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return findByDate(visit.getRequestDate());
  }

  @Override
  public int registerAddress(AddressDTO addressDTO) {
    if (getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry()) > 0) {
      return -1;
    }
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.addresses VALUES(DEFAULT,?,?,?,?,?,?)");
    try {
      ps.setString(1, addressDTO.getStreet());
      ps.setString(2, addressDTO.getBuildingNumber());
      ps.setString(3, addressDTO.getPostCode());
      ps.setString(4, addressDTO.getCommune());
      ps.setString(5, addressDTO.getCountry());
      ps.setString(6, addressDTO.getUnitNumber());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    int i = getAddressByInfo(addressDTO.getStreet(), addressDTO.getBuildingNumber(),
        addressDTO.getCommune(), addressDTO.getCountry());
    return i;
  }

  @Override
  public int getAddressByInfo(String street, String buildingNumber, String commune,
      String country) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT address_id FROM projet.addresses WHERE street=? "
            + "AND building_number=? AND country=? AND commune=?");
    int adresse = 0;
    try {
      ps.setString(1, street);
      ps.setString(2, buildingNumber);
      ps.setString(3, country);
      ps.setString(4, commune);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          adresse = rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    if (adresse <= 0) {
      return -1;
    }
    return adresse;
  }

  @Override
  public List<VisitDTO> getAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateConfirmed(VisitDTO visit) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<VisitDTO> getAllConfirmed() {
    // TODO Auto-generated method stub
    return null;
  }

}
