package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import be.vinci.pae.utils.Config;


public class DalServicesImpl implements DalServices {
  // private Map<String, String> propertiesQueries = new HashMap<String, String>();
  private Connection connection;


  /**
   * Sets an Url, username and password.
   * 
   */
  public DalServicesImpl() {
    try {
      connection = DriverManager.getConnection(Config.getProperty("db.url"),
          Config.getProperty("db.username"), Config.getProperty("db.password"));
      String driver = Config.getProperty("db.driver");
      Class.forName(driver);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * get a statement form a String query.
   * 
   * @param query from which to get a Statement.
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
