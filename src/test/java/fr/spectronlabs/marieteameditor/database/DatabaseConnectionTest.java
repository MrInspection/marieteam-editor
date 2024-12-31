package fr.spectronlabs.marieteameditor.database;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

  private DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
  private Connection connection = database.getConnection();

  @Test
  @DisplayName("1. Test de l'établissement de la connexion")
  void testGetConnection() {
    if (connection != null) {
      assertEquals(connection, database.getConnection());
    } else {
      fail("Connection to database not obtained. Please check the database connection.");
    }
  }

  @Test
  @DisplayName("2. Test de récupération des données")
  void testGetDatabaseData() {
    BoatService service = new BoatService(new BoatQuery(connection));
    /* assertNotNull(service.fetchAllBoats()); */
    List<Boat> boats = service.fetchAllBoats();
    assertNotNull(boats);
    for (Boat boat : boats) {
      System.out.println(boat.toString());
    }
  }

  @Test
  @DisplayName("3. Test de connexion avec identifiants invalides")
  void testInvalidDatabaseConnection() {
    DatabaseConnection connection = new DatabaseConnection(
        "invalid_url", "invalid_user", "invalid_password");
    assertThrows(RuntimeException.class, () -> connection.getConnection());
  }
}