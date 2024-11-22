package fr.spectronlabs.marieteameditor.database;

import fr.spectronlabs.marieteameditor.models.Boat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoatQuery {

  private final Connection connection;

  public BoatQuery(Connection connection) {
    this.connection = connection;
  }

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
