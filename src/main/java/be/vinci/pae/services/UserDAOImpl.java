package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.vinci.pae.domaine.UserDTO;
import be.vinci.pae.domaine.UserImpl;
import jakarta.inject.Inject;

public class UserDAOImpl implements UserDAO {

  // Connection JDBC

  // TODO Question : Inject UserFactory ?? mais => attribut mit a null au lieu du null

  @Inject
  private DalServices dalServices;

  @Override
  public UserDTO findByUserName(String username) {
    // TODO Auto-generated method stub
    PreparedStatement ps = this.dalServices.getPreparedStatement(
        "SELECT user_id, username, first_name, last_name, address, email, is_boss,"
            + "is_antique_dealer, is_confirmed, registration_date, password "
            + "FROM projet.users WHERE username = ?");
    UserDTO user = null;
    try {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          user = new UserImpl();
          user.setID(rs.getInt(1));
          user.setUserName(rs.getString(2));
          user.setFirstName(rs.getString(3));
          user.setLastName(rs.getString(4));
          user.setAdressID(rs.getInt(5));
          // TODO faire les autres setters
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

}
