package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.vinci.pae.domaine.Adress;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserFactory;
import jakarta.inject.Inject;

public class UserDAOImpl implements UserDAO {

  @Inject
  private UserFactory userFactory;

  @Inject
  private DalServices dalServices;

  @Override
  public UserDTO findByUserName(String username) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,\r\n"
            + "            is_antique_dealer, is_confirmed, registration_date, password \r\n"
            + "            FROM projet.users WHERE username = ?");
    UserDTO user = userFactory.getUserDTO();
    try {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          user.setID(rs.getInt(1));
          user.setUserName(rs.getString(2));
          user.setFirstName(rs.getString(3));
          user.setLastName(rs.getString(4));
          user.setAdressID(rs.getInt(5));
          user.setEmail(rs.getString(6));
          user.setBoss(rs.getBoolean(7));
          user.setAntiqueDealer(rs.getBoolean(8));
          user.setConfirmed(rs.getBoolean(9));
          user.setRegistrationDate(rs.getString(10));
          user.setPassword(rs.getString(11));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  @Override
  public UserDTO findById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserDTO registerUser(String username, String email, String password, String lastName,
      String firstName, Adress adress) {
    PreparedStatement ps =
        this.dalServices.getPreparedStatement("INSERT INTO projet.users VALUES(DEFAULT,?,?,?,?,?)");
    try {
      ps.setString(1, lastName);
      ps.setString(2, firstName);
      ps.setString(3, username);
      ps.setString(4, password);
      ps.setInt(5, adress.getID());
      ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return findByUserName(username);
  }

  @Override
  public Adress registerAdress(Adress adress) {
    PreparedStatement ps = this.dalServices
        .getPreparedStatement("INSERT INTO projet.addresses VALUES(DEFAULT,?,?,?,?,?,?)");
    try {
      ps.setString(1, adress.getStreet());
      ps.setString(2, adress.getBuildingNumber());
      ps.setString(3, adress.getPostCode());
      ps.setString(4, adress.getCommune());
      ps.setString(5, adress.getCountry());
      ps.setString(6, adress.getUnitNumber());
      ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    getAdressById(adress);
    return adress;
  }

  @Override
  public int getAdressById(Adress adress) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT address_id FROM projet.addresses WHERE street=? AND building_number=?"
            + " AND postcode=? AND commune=? AND unit_number=? AND country=?");
    try {
      ps.setString(1, adress.getStreet());
      ps.setString(2, adress.getBuildingNumber());
      ps.setString(3, adress.getPostCode());
      ps.setString(4, adress.getCommune());
      ps.setString(5, adress.getCountry());
      ps.setString(6, adress.getUnitNumber());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          adress.setID(rs.getInt(1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
    return adress.getID();
  }

}
