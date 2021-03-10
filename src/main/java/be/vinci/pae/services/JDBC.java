package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import be.vinci.pae.utils.Config;

public class JDBC {

  private ThreadLocal<Connection> dbConnections;
  private Connection connection;

  /**
   * Creates a connection to the database.
   */
  public JDBC() {
    dbConnections = new ThreadLocal<Connection>();
    connection = creationConnection(Config.getProperty("db.url"), Config.getProperty("db.username"),
        Config.getProperty("db.password"));
    String driver = Config.getProperty("db.driver");
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException exception) {
      System.out.println(exception.getMessage());
      throw new UnsupportedOperationException(exception.getMessage());
    }
  }

  private Connection creationConnection(String connectionString, String login, String password) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(connectionString, login, password);
      dbConnections.set(connection);
    } catch (SQLException exception) {
      System.out.println(exception.getMessage());
      throw new UnsupportedOperationException(exception.getMessage());
    }
    return connection;
  }

  public Connection getConnection() {
    return /* TODO Clone */ this.connection;
  }
}
