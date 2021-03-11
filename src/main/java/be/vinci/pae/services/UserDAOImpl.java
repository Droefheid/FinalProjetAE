package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
      return fullFillUserFromResulSet(user, ps);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public UserDTO findById(int id) {
	  PreparedStatement ps = this.dalServices.getPreparedStatement(
		        "SELECT user_id, username, first_name, last_name, address, email, is_boss,\r\n"
		            + "            is_antique_dealer, is_confirmed, registration_date, password \r\n"
		            + "            FROM projet.users WHERE user_id = ?");
	  UserDTO user = userFactory.getUserDTO();
	  try {
	      ps.setInt(1, id);
	      return fullFillUserFromResulSet(user, ps);
	  } catch (SQLException e) {
	      e.printStackTrace();
	      return null;
	  }
  }
  
  /**
   * Fully fill the user with the ResultSet from de db.
   * Or throws SQLException.
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
	          user.setRegistrationDate(rs.getString(10));
	          user.setPassword(rs.getString(11));
	        }
	      }
	  return user;
  }

}
