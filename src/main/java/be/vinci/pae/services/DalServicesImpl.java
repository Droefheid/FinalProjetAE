package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.inject.Inject;


public class DalServicesImpl implements DalServices {
  // private Map<String, String> propertiesQueries = new HashMap<String, String>();
  private Connection connection;

  @Inject
  JDBC jdbc;

  /**
   * Sets an Url, username and password.
   * 
   */
  public DalServicesImpl() {
    this.connection = jdbc.getConnection();
  }

  /**
   * get a statement form a String query.
   * 
   * @param query from which to get a Statement
   */
  public PreparedStatement getPreparedStatement(String query) {
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(query);
      return ps;
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    return null;
  }

}
