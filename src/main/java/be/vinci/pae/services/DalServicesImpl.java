package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.utils.Config;


public class DalServicesImpl implements DalBackendServices, DalServices {

  private ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

  private BasicDataSource bds;

  /**
   * Sets an Url, username and password.
   * 
   */
  public DalServicesImpl() {
    bds = new BasicDataSource();
    bds.setUrl(Config.getProperty("db.url"));
    bds.setDriverClassName(Config.getProperty("db.driver"));
    bds.setUsername(Config.getProperty("db.username"));
    bds.setPassword(Config.getProperty("db.password"));
    // Inutile avec Elephant DB.
    bds.setMaxActive(5);
  }

  /**
   * get a statement form a String query.
   * 
   * @param query from which to get a Statement.
   */
  public PreparedStatement getPreparedStatement(String query) {
    if (threadConnection.get() == null) {
      throw new FatalException("Error can't find connection: transaction hasn't been started.");
    }
    PreparedStatement ps = null;
    try {
      ps = threadConnection.get().prepareStatement(query);
      return ps;
    } catch (SQLException e) {
      throw new FatalException("Error can't create prepareStatement.", e);
    }
  }

  @Override
  public void startTransaction() {
    if (threadConnection.get() != null) {
      throw new FatalException("Already a transaction active.");
    }
    try {
      bds.setDefaultAutoCommit(false);
      Connection c = bds.getConnection();
      threadConnection.set(c);
    } catch (SQLException e) {
      throw new FatalException("Transaction error", e);
    }

  }

  @Override
  public void commitTransaction() {
    Connection c = threadConnection.get();
    if (c == null) {
      throw new FatalException("No connection: transaction hasn't been started.");
    }
    try {
      c.commit();
      bds.setDefaultAutoCommit(true);
      threadConnection.remove();
      c.close();
    } catch (SQLException e) {
      throw new FatalException("commit error", e);
    }
  }

  @Override
  public void rollbackTransaction() {
    Connection c = threadConnection.get();
    if (c == null) {
      throw new FatalException("No connection: transaction hasn't been started.");
    }
    try {
      c.rollback();
      bds.setDefaultAutoCommit(true);
      threadConnection.remove();
      c.close();
    } catch (SQLException e) {
      throw new FatalException("rollback error", e);
    }
  }

}
