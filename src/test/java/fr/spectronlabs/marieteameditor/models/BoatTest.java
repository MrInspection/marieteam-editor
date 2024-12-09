package fr.spectronlabs.marieteameditor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.services.BoatService;

class BoatTest {
  private final DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
  private final Connection connection = database.getConnection();
  private final BoatService service = new BoatService(new BoatQuery(connection));

  private final List<Boat> boats = service.fetchAllBoats();

  @Test
  void TestGetASpecificBoat() {
    assertNotNull(boats);
    Boat boat = boats.getFirst();
    System.out.println(boats.toString());

    boat.setName("Vogue Merry");
    System.out.println(boats.toString());

    /* boat.setSpeed(0);
    System.out.println(boats.toString()); // Ça fonctionne !

    boat.setSpeed(-1);
    System.out.println(boats.toString());

    boat.setLength(0);
    System.out.println(boats.toString()); // Ça fonctionne également !

    boat.setWidth(0);
    System.out.println(boats.toString()); */

    boat.addEquipment("Anchor");
    System.out.println(boat.getEquipments());
  }

  @Test
  void testGetFormattedEquipments() {
    Boat boat = new Boat("1", "Test", 10, 10, 10);
    boat.addEquipment("First Aid Kit");
    boat.addEquipment("GPS Chartpottlers");
    boat.addEquipment("Fire Blanket");
    System.out.println(boat.getFormattedEquipments());

    String result = boat.getFormattedEquipments();

    assertEquals("First Aid Kit, GPS Chartpottlers, Fire Blanket", result);
  }

  @Test
  void testRemoveEquipment() {
    Boat boat = new Boat("1", "Test", 10, 10, 10);
    boat.addEquipment("First Aid Kit");
    boat.addEquipment("GPS Chartpottlers");
    boat.addEquipment("Fire Blanket");
    System.out.println(boat.getFormattedEquipments());

    assertTrue(boat.removeEquipment("First Aid Kit"));
    System.out.println(boat.getFormattedEquipments());
    assertFalse(boat.removeEquipment("First Aid Kit"));
    System.out.println(boat.getFormattedEquipments());
  }

  @Test
  void testModifyBoatData() {
    Boat boat = new Boat("1", "Test", 10, 10, 10);
    boat.addEquipment("First Aid Kit");
    boat.addEquipment("GPS Chartpottlers");
    boat.addEquipment("Fire Blanket");
    System.out.println(boat.getFormattedEquipments());

    boat.setName("Vogue Merry");
    System.out.println(boat.getName());

    boat.setSpeed(0);
    System.out.println(boat.getSpeed());

    boat.setSpeed(-1);
    System.out.println(boat.getSpeed());

    boat.setLength(0);
    System.out.println(boat.getLength());

    boat.setWidth(0);
    System.out.println(boat.getWidth());
  }
}