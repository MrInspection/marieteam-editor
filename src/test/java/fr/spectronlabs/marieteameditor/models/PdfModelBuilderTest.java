package fr.spectronlabs.marieteameditor.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.services.BoatService;
import fr.spectronlabs.marieteameditor.utils.PdfBuilder;

class PdfModelBuilderTest {

  private final DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
  private final Connection connection = database.getConnection();
  private final BoatService service = new BoatService(new BoatQuery(connection));
  private final List<Boat> boats = service.fetchAllBoats();

  @Test
  void testBuildPdfModel() throws MalformedURLException, FileNotFoundException {
    // Vérification des données
    assertNotNull(boats);
    Boat boat = boats.getFirst(); // Premier bateau

    // Définir le chemin du fichier PDF à la racine du projet
    String outputPath = "./test_boat_export.pdf"; // './' correspond à la racine du projet
    File file = new File(outputPath);

    // Générer le PDF avec PdfBuilder
    PdfBuilder pdfBuilder = new PdfBuilder();
    pdfBuilder.exportBoat(boat, outputPath);

    // Vérifier que le fichier a été créé
    assertNotNull(file);
    assertNotNull(file.exists());
    System.out.println("PDF généré avec succès à : " + file.getAbsolutePath());
  }
}