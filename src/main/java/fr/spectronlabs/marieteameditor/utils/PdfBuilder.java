package fr.spectronlabs.marieteameditor.utils;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.models.PdfModelBuilder;

/**
 * Utilitaire pour la génération de documents PDF.
 * Permet d'exporter les informations des bateaux au format PDF.
 */
public class PdfBuilder {

  /**
   * Exporte les informations d'un bateau spécifique en PDF.
   * @param boat Bateau à exporter
   * @param filePath Chemin du fichier PDF à créer
   * @throws FileNotFoundException si le chemin est invalide
   * @throws MalformedURLException si l'URL de l'image est invalide
   */
  public static void exportBoat(Boat boat, String filePath) throws FileNotFoundException, MalformedURLException {
    PdfWriter writer = new PdfWriter(filePath);
    PdfDocument pdfDoc = new PdfDocument(writer);
    pdfDoc.setDefaultPageSize(PageSize.A4);

    Document document = new Document(pdfDoc);
    document.add(PdfModelBuilder.createTitle("Boat Details"));
    document.add(PdfModelBuilder.createBoatSection(boat));
    document.close();
  }

  /**
   * Exporte les informations de tous les bateaux en PDF.
   * @param boats Liste des bateaux à exporter
   * @param filePath Chemin du fichier PDF à créer
   * @throws FileNotFoundException si le chemin est invalide
   * @throws MalformedURLException si une URL d'image est invalide
   */
  public static void exportAllBoats(List<Boat> boats, String filePath) throws FileNotFoundException, MalformedURLException {
    PdfWriter writer = new PdfWriter(filePath);
    PdfDocument pdfDoc = new PdfDocument(writer);
    pdfDoc.setDefaultPageSize(PageSize.A4);

    Document document = new Document(pdfDoc);
    document.add(PdfModelBuilder.createTitle("MarieTeam Fleet"));

    for (Boat boat : boats) {
      document.add(PdfModelBuilder.createBoatSection(boat));
    }

    document.close();
  }
}
