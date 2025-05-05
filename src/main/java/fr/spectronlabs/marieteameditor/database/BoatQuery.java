package fr.spectronlabs.marieteameditor.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.spectronlabs.marieteameditor.models.Boat;

/**
 * Gère les requêtes SQL relatives aux bateaux.
 * Cette classe fournit une interface entre l'application et la base de données
 * pour toutes les opérations concernant les bateaux.
 */
public class BoatQuery {
  private final Connection connection;

  public BoatQuery(Connection connection) {
    this.connection = connection;
  }

  /**
   * Récupère tous les bateaux stockés dans la base de données.
   * Cette méthode effectue une requête SQL pour obtenir les informations complètes
   * de chaque bateau, y compris leurs équipements.
   *
   * @return Une liste contenant tous les bateaux avec leurs informations complètes
   * @throws RuntimeException si la requête échoue ou si la connexion est perdue
   */
  public List<Boat> getAllBoats() {
    List<Boat> boats = new ArrayList<>();
    String query = "SELECT * FROM public.\"Boat\"";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      ResultSet res = statement.executeQuery();

      while (res.next()) {
        String id = res.getString("id");
        String name = res.getString("name");
        double length = res.getDouble("length");
        double width = res.getDouble("width");
        double speed = res.getDouble("speed");
        String imageUrl = res.getString("imageUrl");

        // Parse the PostgreSQL array to a Java List<String>
        Array equipmentArray = res.getArray("equipment");
        List<String> equipment = equipmentArray != null
            ? Arrays.asList((String[]) equipmentArray.getArray())
            : new ArrayList<>(); // Handle null equipment

        Boat b = new Boat(id, name, length, width, speed, imageUrl, equipment);
        boats.add(b);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Unable to fetch boats from the database connection", e);
    }
    return boats;
  }
}
