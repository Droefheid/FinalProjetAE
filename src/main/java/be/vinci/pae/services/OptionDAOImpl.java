package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.option.OptionDTO;
import jakarta.inject.Inject;

public class OptionDAOImpl implements OptionDAO {

  @Inject
  private DalBackendServices dalBackendServices;

  @Inject
  private DomaineFactory domaineFactory;

  @Override
  public int introduceOption(OptionDTO option) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.options VALUES(DEFAULT,?,?,true,?,?)");
    try {
      ps.setTimestamp(1, option.getOptionTerm());
      ps.setTimestamp(2, option.getBeginningOptionDate());
      ps.setInt(3, option.getCustomer());
      ps.setInt(4, option.getFurniture());
      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return findOptionIdByInfo(option);
  }

  /**
   * returns the id of the option or -1 if non-existent.
   */
  public int findOptionIdByInfo(OptionDTO option) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT option_id FROM projet.options WHERE option_term  = ?"
            + "AND beginning_option_date =? AND customer = ? AND furniture = ? ");
    int id = 0;
    try {
      ps.setTimestamp(1, option.getOptionTerm());
      ps.setTimestamp(2, option.getBeginningOptionDate());
      ps.setInt(3, option.getCustomer());
      ps.setInt(4, option.getFurniture());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          id = rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    if (id <= 0) {
      return -1;
    }
    return id;
  }

  @Override
  public void changeFurnitureState(String state, int furnitureID) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "UPDATE projet.furnitures SET state_furniture=? WHERE furniture_id = ? ");
    try {
      ps.setString(1, state);
      ps.setInt(2, furnitureID);
      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
  }

  @Override
  public List<OptionDTO> listOfOptionsFromSameCustomerAndFurniture(OptionDTO option) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT option_id,option_term,beginning_option_date FROM projet.options "
            + "WHERE customer=? AND furniture=?");
    List<OptionDTO> list = new ArrayList<OptionDTO>();
    try {
      ps.setInt(1, option.getCustomer());
      ps.setInt(2, option.getFurniture());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          OptionDTO optionDTO = domaineFactory.getOptionDTO();
          optionDTO.setId(rs.getInt(1));
          optionDTO.setOptionTerm(rs.getTimestamp(2));
          optionDTO.setOptionTerm(rs.getTimestamp(3));
          list.add(optionDTO);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }

    return list;
  }

  @Override
  public OptionDTO findOptionByID(int optionID) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT option_id,option_term," + "beginning_option_date,customer,furniture "
            + "FROM projet.options WHERE option_id=?");
    OptionDTO optionDTO = domaineFactory.getOptionDTO();
    try {
      ps.setInt(1, optionID);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          fullFillOption(rs, optionDTO);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return optionDTO;
  }

  @Override
  public void stopOption(OptionDTO option) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("UPDATE projet.options SET option_term =?, is_currently_reserved=? "
            + "WHERE option_id = ? ");
    try {
      ps.setTimestamp(1, option.getOptionTerm());
      ps.setBoolean(2, false);
      ps.setInt(3, option.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
  }

  @Override
  public OptionDTO findOptionByFurnitureIdANDCustomerId(int furnitureID, int customerID) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT option_id,option_term," + "beginning_option_date,customer,furniture "
            + "FROM projet.options WHERE furniture=? AND "
            + "is_currently_reserved=? AND customer=?");
    OptionDTO optionDTO = domaineFactory.getOptionDTO();
    try {
      ps.setInt(1, furnitureID);
      ps.setBoolean(2, true);
      ps.setInt(3, customerID);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          fullFillOption(rs, optionDTO);
        }
      }
    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException(e.getMessage(), e);
    }
    return optionDTO;
  }

  private OptionDTO fullFillOption(ResultSet rs, OptionDTO optionDTO) {
    try {
      optionDTO.setId(rs.getInt(1));
      optionDTO.setOptionTerm(rs.getTimestamp(2));
      optionDTO.setBeginningOptionDate(rs.getTimestamp(3));
      optionDTO.setCustomer(rs.getInt(4));
      optionDTO.setFurniture(rs.getInt(5));

    } catch (SQLException e) {
      ((DalServices) dalBackendServices).rollbackTransaction();
      throw new FatalException("error fullFillFurnitures", e);
    }
    return optionDTO;
  }
}
