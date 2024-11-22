package fr.spectronlabs.marieteameditor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private final String databaseUrl;
  private final String user;
  private final String password;

  private final Connection connection;

  public DatabaseConnection(String databaseUrl, String user, String password) {
    this.databaseUrl = databaseUrl;
    this.user = user;
    this.password = password;

    try {
      this.connection = DriverManager.getConnection(databaseUrl, user, password);
      if (connection != null) {
        System.out.println("\n SpectronAtoms: Connection to the database established successfully... \n");
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to connect to the database");
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException("Unable to close the database connection");
    }
  }
}
