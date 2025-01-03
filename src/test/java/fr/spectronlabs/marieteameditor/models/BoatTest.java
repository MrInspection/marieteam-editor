package fr.spectronlabs.marieteameditor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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
  @Order(1)
  @DisplayName("1. Test de récupération d'un bateau spécifique")
  void TestGetASpecificBoat() {
    assertNotNull(boats);
    Boat boat = boats.getFirst();
    System.out.println(boats.toString());

    boat.setName("Vogue Merry");
    System.out.println(boats.toString());

    boat.addEquipment("Anchor");
    System.out.println(boat.getEquipments());
  }

  @Test
  @Order(2)
  @DisplayName("2. Test du formatage des équipements")
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
  @Order(3)
  @DisplayName("3. Test de la gestion des équipements")
  void testEquipmentManagement() {
    Boat boat = new Boat("1", "Test", 10, 5, 20);

    // Test ajout équipement
    boat.addEquipment("GPS");
    assertEquals(1, boat.getEquipments().size());

    // Test ajout équipement null ou vide
    boat.addEquipment(null);
    boat.addEquipment("");
    assertEquals(1, boat.getEquipments().size());

    // Test suppression équipement
    assertTrue(boat.removeEquipment("GPS"));
    assertFalse(boat.removeEquipment("NonExistant"));
  }

  @Test
  @Order(4)
  @DisplayName("4. Test des modifications des données avec valeurs invalides")
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

  @Test
  @Order(5)
  @DisplayName("5. Test des contraintes de validation")
  void testValidationConstraints() {
    // Test des contraintes de validation (longueur, largeur, vitesse négatives)
    assertThrows(IllegalArgumentException.class, () -> new Boat("1", "Test", -1, 5, 10));
    assertThrows(IllegalArgumentException.class, () -> new Boat("1", "Test", 10, -1, 10));
    assertThrows(IllegalArgumentException.class, () -> new Boat("1", "Test", 10, 5, -1));
  }
}