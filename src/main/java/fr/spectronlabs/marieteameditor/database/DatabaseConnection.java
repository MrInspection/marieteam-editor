package fr.spectronlabs.marieteameditor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gère la connexion à la base de données PostgreSQL.
 * Fournit les méthodes pour établir et fermer la connexion.
 */
public class DatabaseConnection {

  private final Connection connection;

  /**
   * Établit une connexion à la base de données.
   *
   * @param databaseUrl URL de la base de données
   * @param user        Nom d'utilisateur
   * @param password    Mot de passe
   * @throws RuntimeException si la connexion échoue
   */
  public DatabaseConnection(String databaseUrl, String user, String password) {
    try {
      this.connection = DriverManager.getConnection(databaseUrl, user, password);
      if (connection != null) {
        System.out.println("\n☑️ MarieTeam: connection established to the database \n");
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
