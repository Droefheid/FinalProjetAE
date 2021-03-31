package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.AddressDTO;
import be.vinci.pae.domaine.DomaineFactory;
import be.vinci.pae.domaine.UserDTO;
import jakarta.inject.Inject;

public class UserDAOImpl implements UserDAO {

  @Inject
  private DomaineFactory domaineFactory;

  @Inject
  private DalBackendServices dalBackendServices;

  @Override
  public UserDTO findByUserName(String username) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,"
            + " is_antique_dealer, is_confirmed, registration_date, password"
            + " FROM projet.users WHERE username = ?");
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
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
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
    /*
     * if (!user.isConfirmed()) { return null; }
     */
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
    if (findByEmail(user.getEmail()) != null) {
      return null;
    }
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
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

  private UserDTO findByEmail(String email) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,"
            + " is_antique_dealer, is_confirmed, registration_date, password"
            + " FROM projet.users WHERE email = ?");
    UserDTO user = domaineFactory.getUserDTO();
    try {
      ps.setString(1, email);
      user = fullFillUserFromResulSet(user, ps);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
    if (user.getEmail() == null) {
      return null;
    }
    return user;
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
      throw new FatalException(e.getMessage(), e);
    }
    if (adresse <= 0) {
      return -1;
    }
    return adresse;
  }

  @Override
  public List<UserDTO> getAll() {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT user_id , last_name , first_name,username ,password , address , email , is_boss , is_antique_dealer , is_confirmed , "
            + " registration_date FROM projet.users");


    List<UserDTO> list = new ArrayList<UserDTO>();

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        UserDTO user = domaineFactory.getUserDTO();
        fullFillListUsers(rs, user);
        list.add(user);
      }
    } catch (SQLException e) {
      throw new FatalException("error fullFillUsers", e);
    }

    return list;
  }



  @Override
  public void updateConfirmed(boolean confirmed, boolean antiqueDealer, int userId) {
    PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "UPDATE projet.users SET is_confirmed = ? , is_antique_dealer = ? WHERE user_id = ?");


    try {
      ps.setBoolean(1, confirmed);
      ps.setBoolean(2, antiqueDealer);
      ps.setInt(3, userId);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage(), e);
    }
  }

  private UserDTO fullFillListUsers(ResultSet rs, UserDTO user) {
    try {
      user.setID(rs.getInt(1));
      user.setLastName(rs.getString(2));
      user.setFirstName(rs.getString(3));
      user.setUserName(rs.getString(4));
      user.setPassword(rs.getString(5));
      user.setAdressID(rs.getInt(6));
      user.setEmail(rs.getString(7));
      user.setBoss(rs.getBoolean(8));
      user.setAntiqueDealer(rs.getBoolean(9));
      user.setConfirmed(rs.getBoolean(10));
      user.setRegistrationDate(rs.getTimestamp(11));

    } catch (SQLException e) {
      throw new FatalException("error fullFillUsers", e);
    }
    return user;
  }


}
