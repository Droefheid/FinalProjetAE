package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.utils.Config;


public class DalServicesImpl implements DalBackendServices, DalServices {

  private ThreadLocal<Connection> threadConnection;

  private BasicDataSource bds = new BasicDataSource();

  /**
   * Sets an Url, username and password.
   * 
   */
  public DalServicesImpl() {
    bds.setUrl(Config.getProperty("db.url"));
    bds.setDriverClassName(Config.getProperty("db.driver"));
    try {
      Connection connection =
          bds.getConnection(Config.getProperty("db.username"), Config.getProperty("db.password"));
      threadConnection.set(connection);
    } catch (SQLException e) {
      throw new FatalException("Error Db connection", e);
    }
    bds.setMaxActive(5);
  }

  /**
   * get a statement form a String query.
   * 
   * @param query from which to get a Statement.
   */
  public PreparedStatement getPreparedStatement(String query) {
    PreparedStatement ps = null;
    try {
      ps = threadConnection.get().prepareStatement(query);
      return ps;
    } catch (SQLException e) {
      throw new FatalException("Error can't find connection", e);
    }
  }

  @Override
  public void startTransaction() {
    try {
      Connection c = bds.getConnection();
    } catch (SQLException e) {
      throw new FatalException("Transaction error", e);
    }

  }

  @Override
  public void commitTransaction() {


  }

  @Override
  public void rollbackTransaction() {

  }

}
