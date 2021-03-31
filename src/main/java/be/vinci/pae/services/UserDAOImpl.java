package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.Address;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.UserDTO;
import jakarta.inject.Inject;

public class UserDAOImpl implements UserDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalServices dalServices;

  @Override
  public UserDTO findByUserName(String username) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,\r\n"
            + "            is_antique_dealer, is_confirmed, registration_date, password \r\n"
            + "            FROM projet.users WHERE username = ?");
    UserDTO user = domaineFactory.getUserDTO();
    try {
      ps.setString(1, username);
      user = fullFillUserFromResulSet(user, ps);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    if (user.getUserName() == null) {
      return null;
    }
    return user;
  }

  @Override
  public UserDTO findById(int id) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,"
            + " is_antique_dealer, is_confirmed, registration_date, password "
            + "FROM projet.users WHERE user_id = ?");
    UserDTO user = domaineFactory.getUserDTO();
    try {
      ps.setInt(1, id);
      user = fullFillUserFromResulSet(user, ps);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    if (!user.isConfirmed()) {
      return null;
    }
    return user;
  }

  /**
   * Fully fill the user with the ResultSet from de db. Or throws SQLException.
   * 
   * @param user empty, to be filled.
   * @param ps the PreparedStatement already Set.
   * @return the user filled.
   * @throws SQLException if problems.
   */
  private UserDTO fullFillUserFromResulSet(UserDTO user, PreparedStatement ps) throws SQLException {
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
        user.setRegistrationDate(rs.getTimestamp(10));
        user.setPassword(rs.getString(11));
      }
    }
    return user;
  }

  @Override
  public UserDTO registerUser(UserDTO user) {
    if (findByUserName(user.getUserName()) != null) {
      return null;
    }
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "INSERT INTO projet.users VALUES(DEFAULT,?,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT,?)");
    try {
      ps.setString(1, user.getLastName());
      ps.setString(2, user.getFirstName());
      ps.setString(3, user.getUserName());
      ps.setString(4, user.getPassword());
      ps.setInt(5, user.getAdressID());
      ps.setString(6, user.getEmail());
      ps.setTimestamp(7, user.getRegistrationDate());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    return findByUserName(user.getUserName());
  }

  @Override
  public int registerAddress(Address address) {
    if (getAddressByInfo(address.getStreet(), address.getBuildingNumber(), address.getCommune(),
        address.getCountry()) > 0) {
      return -1;
    }
    PreparedStatement ps = this.dalServices
        .getPreparedStatement("INSERT INTO projet.addresses VALUES(DEFAULT,?,?,?,?,?,?)");
    try {
      ps.setString(1, address.getStreet());
      ps.setString(2, address.getBuildingNumber());
      ps.setString(3, address.getPostCode());
      ps.setString(4, address.getCommune());
      ps.setString(5, address.getCountry());
      ps.setString(6, address.getUnitNumber());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    int i = getAddressByInfo(address.getStreet(), address.getBuildingNumber(), address.getCommune(),
        address.getCountry());
    return i;
  }

  @Override
  public Address getAddressById(int addressId) {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT address_id,street," + "building_number,postcode,commune,country,unit_number "
            + "FROM projet.addresses WHERE address_id=?");
    Address adresse = domaineFactory.getAdress();
    try {
      ps.setInt(1, addressId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          if (rs.getInt(1) <= 0) {
            return null;
          }
          adresse.setID(rs.getInt(1));
          adresse.setStreet(rs.getString(2));
          adresse.setBuildingNumber(rs.getString(3));
          adresse.setPostCode(rs.getString(4));
          adresse.setCommune(rs.getString(5));
          adresse.setCountry(rs.getString(6));
          adresse.setUnitNumber(rs.getString(7));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    return adresse;
  }

  @Override
  public int getAddressByInfo(String street, String buildingNumber, String commune,
      String country) {
    PreparedStatement ps = this.dalServices
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
      throw new FatalException(e.getMessage(), e);
    }
    if (adresse <= 0) {
      return -1;
    }
    return adresse;
  }

  @Override
  public List<UserDTO> getAll() {
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT * FROM projet.users");

    UserDTO user = domaineFactory.getUserDTO();
    List<UserDTO> list = new ArrayList<UserDTO>();

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        fullFillUsers(rs, user);
        list.add(user);
      }
    } catch (SQLException e) {
      throw new FatalException("error fullFillUsers", e);
    }

    return list;
  }

/*
@Override
  public UserDTO updateBoss(UserDTO user, boolean is_boss) {
    PreparedStatement ps = this.dalServices.getPreparedStatement("UPDATE projet.users SET is_boss = ? WHERE username = ?;");
    try {
      ps.setBoolean(1, is_boss);
      ps.setString(2, user.getUserName());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FatalException("ERROR update user.", e);
    }
    return user;
  }
  
*/
  private void fullFillUsers(ResultSet rs, UserDTO user) {
    try {
      user.setAdressID(rs.getInt("address"));
      user.setAntiqueDealer(rs.getBoolean("is_antique_dealer"));
      user.setBoss(rs.getBoolean("is_boss"));
      user.setConfirmed(rs.getBoolean("is_confirmed"));
      user.setEmail(rs.getString("email"));
      user.setFirstName(rs.getString("first_name"));
      user.setID(rs.getInt("user_id"));
      user.setPassword(rs.getString("password"));
      user.setLastName(rs.getString("last_name"));
      user.setRegistrationDate(rs.getTimestamp("registration_date"));
      user.setUserName(rs.getString("username"));
    } catch (SQLException e) {
      throw new FatalException("error fullFillUsers", e);
    }
  }


}
