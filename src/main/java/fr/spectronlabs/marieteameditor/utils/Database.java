package fr.spectronlabs.marieteameditor.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
  public static void main(String[] args) {

    ArrayList<Boat> boats = new ArrayList<>();

    String DATABASE_URL = "jdbc:postgresql://localhost:5432/marieteam?currentSchema=public";
    String DATABASE_USER = "postgres";
    String DATABASE_PASSWORD = "admin";
    String SQL_QUERY = "SELECT * FROM public.\"Boat\"";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
      if (conn != null) {
        System.out.println("Connected to PostgreSQL database");
      }

      PreparedStatement statement = conn.prepareStatement(SQL_QUERY);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        double length = resultSet.getDouble("length");
        double width = resultSet.getDouble("width");
        double speed = resultSet.getDouble("speed");
        String imageUrl = resultSet.getString("imageUrl");
        String[] equipment = resultSet.getString("equipment").split(",");

        Boat b = new Boat(id, name, length, width, speed, imageUrl, equipment);
        boats.add(b);
      }

      for (Boat b : boats) {
        System.out.println("-------------------------");
        System.out.println("DEBUG! Boat Id : " + b.getId());
        System.out.println("DEBUG! Boat Name : " + b.getName());
        System.out.println("DEBUG! Boat ImageUrl : " + b.getImageUrl());
        System.out.println("DEBUG! Boat Speed : " + b.getSpeed());
        System.out.println("DEBUG! Boat Length : " + b.getLength());
        System.out.println("DEBUG! Boat Width : " + b.getWidth());
        System.out.println("DEBUG! Boat Equipments : " + Arrays.stream(b.getEquipments()).count() + " equipments");
        System.out.println("-------------------------");
      }

    } catch (Exception e) {
      if (e instanceof SQLException) {
        System.out.println(e.getMessage());
        System.out.println(((SQLException) e).getSQLState());
      }
      System.err.println(e.getMessage());
    }
  }
}
