package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;
import fr.spectronlabs.marieteameditor.utils.PdfBuilder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test d'intégration simulant le flux complet de l'application
 * De la connexion à la base de données jusqu'à l'export PDF
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationFlowTest {

  private static DatabaseConnection database;
  private static Connection connection;
  private static BoatService boatService;
  private static Boat selectedBoat;
  private static final String TEST_PDF_PATH = "./test_integration_export.pdf";

  @BeforeAll
  static void setUp() {
    try {
      // 1. Initialisation de la connexion à la base de données
      database = new DatabaseConnection(
          Constants.DATABASE_URL,
          Constants.DATABASE_USER,
          Constants.DATABASE_PASSWORD
      );
      connection = database.getConnection();
      boatService = new BoatService(new BoatQuery(connection));

      System.out.println("✅ Test setup completed successfully");
    } catch (Exception e) {
      fail("Setup failed: " + e.getMessage());
    }
  }

  @Test
  @Order(1)
  @DisplayName("1. Test de la connexion à la base de données")
  void testDatabaseConnection() {
    assertNotNull(connection, "La connexion à la base de données ne devrait pas être null");
    assertTrue(boatService != null, "Le service des bateaux devrait être initialisé");
    System.out.println("✅ Database connection test passed");
  }

  @Test
  @Order(2)
  @DisplayName("2. Test de récupération de la liste des bateaux")
  void testFetchBoats() {
    List<Boat> boats = boatService.fetchAllBoats();
    assertNotNull(boats, "La liste des bateaux ne devrait pas être null");
    assertFalse(boats.isEmpty(), "La liste des bateaux ne devrait pas être vide");
    selectedBoat = boats.getFirst();
    System.out.println("✅ Boats fetched successfully: " + boats.size() + " boats found");
  }

  @Test
  @Order(3)
  @DisplayName("3. Test de modification des informations du bateau")
  void testModifyBoat() {
    assertNotNull(selectedBoat, "Un bateau devrait être sélectionné");

    // Simulation des modifications utilisateur
    String originalName = selectedBoat.getName();
    List<String> originalEquipments = selectedBoat.getEquipments();

    try {
      // Modification du nom
      selectedBoat.setName("Test Boat Modified");
      assertEquals("Test Boat Modified", selectedBoat.getName());

      // Modification des équipements
      selectedBoat.setEquipments(Arrays.asList("GPS", "Radar", "Life Jackets"));
      assertEquals(3, selectedBoat.getEquipments().size());

      // Modification des dimensions
      selectedBoat.setLength(15.5);
      selectedBoat.setWidth(5.2);
      selectedBoat.setSpeed(25.0);

      System.out.println("✅ Boat modifications successful");

      // Restauration des valeurs originales pour ne pas affecter la base de données
      selectedBoat.setName(originalName);
      selectedBoat.setEquipments(originalEquipments);

    } catch (Exception e) {
      fail("La modification du bateau a échoué: " + e.getMessage());
    }
  }

  @Test
  @Order(4)
  @DisplayName("4. Test de validation des données")
  void testDataValidation() {
    assertThrows(IllegalArgumentException.class, () -> {
      selectedBoat.setSpeed(-10.0);
    }, "Une vitesse négative devrait lever une exception");

    assertThrows(IllegalArgumentException.class, () -> {
      selectedBoat.setLength(-5.0);
    }, "Une longueur négative devrait lever une exception");

    assertThrows(IllegalArgumentException.class, () -> {
      selectedBoat.setWidth(-2.0);
    }, "Une largeur négative devrait lever une exception");

    System.out.println("✅ Data validation tests passed");
  }

  @Test
  @Order(5)
  @DisplayName("5. Test d'export PDF")
  void testPdfExport() {
    assertNotNull(selectedBoat, "Un bateau devrait être sélectionné pour l'export");

    try {
      // Test d'export d'un seul bateau
      PdfBuilder.exportBoat(selectedBoat, TEST_PDF_PATH);
      File pdfFile = new File(TEST_PDF_PATH);
      assertTrue(pdfFile.exists(), "Le fichier PDF devrait être créé");
      assertTrue(pdfFile.length() > 0, "Le fichier PDF ne devrait pas être vide");

      System.out.println("✅ PDF export successful: " + pdfFile.getAbsolutePath());

      // Nettoyage : suppression du fichier de test
      if (pdfFile.delete()) {
        System.out.println("✅ Test PDF file cleaned up successfully");
      }

    } catch (Exception e) {
      fail("L'export PDF a échoué: " + e.getMessage());
    }
  }

  @Test
  @Order(6)
  @DisplayName("6. Test d'export PDF de tous les bateaux")
  void testPdfExportAll() {
    try {
      List<Boat> allBoats = boatService.fetchAllBoats();
      assertFalse(allBoats.isEmpty(), "La liste des bateaux ne devrait pas être vide");

      String allBoatsPdfPath = "./test_all_boats_export.pdf";
      PdfBuilder.exportAllBoats(allBoats, allBoatsPdfPath);

      File pdfFile = new File(allBoatsPdfPath);
      assertTrue(pdfFile.exists(), "Le fichier PDF pour tous les bateaux devrait être créé");
      assertTrue(pdfFile.length() > 0, "Le fichier PDF ne devrait pas être vide");

      System.out.println("✅ All boats PDF export successful: " + pdfFile.getAbsolutePath());

      // Nettoyage
      if (pdfFile.delete()) {
        System.out.println("✅ Test PDF file (all boats) cleaned up successfully");
      }

    } catch (Exception e) {
      fail("L'export PDF de tous les bateaux a échoué: " + e.getMessage());
    }
  }

  @AfterAll
  static void tearDown() {
    try {
      if (connection != null) {
        database.closeConnection();
        System.out.println("✅ Database connection closed successfully");
      }
    } catch (Exception e) {
      System.err.println("❌ Error during teardown: " + e.getMessage());
    }
  }
}