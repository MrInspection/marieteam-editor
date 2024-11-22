package fr.spectronlabs.marieteameditor.utils;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;

import java.util.List;

public class Test {
  public static void main(String[] args) {

    List<Boat> boats;

    DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
    BoatService service = new BoatService(new BoatQuery(database.getConnection()));
    boats = service.fetchAllBoats();

    for(Boat b : boats) {
      System.out.println(b.toString());
    }
  }
}
