package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.OptionDTO;
import jakarta.inject.Inject;

public class OptionDAOImpl implements OptionDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;

  @Override
  public void introduceOption(OptionDTO option) {
    PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO projet.options VALUES(DEFAULT,?,?,?,?)");
    try {
      ps.setTimestamp(1, option.getOptionTerm());
      ps.setTimestamp(2, option.getBeginningOptionDate());
      ps.setInt(3, option.getCustomer());
      ps.setInt(4, option.getFurniture());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException(e.getMessage(), e);
    }
  }

  public int findOptionByInfo(OptionDTO option) {
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
      throw new FatalException(e.getMessage(), e);
    }
    if (id <= 0)
      return -1;
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
      throw new FatalException(e.getMessage(), e);
    }
  }

}
