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
   * Exporte les informations d'un bateau spécifique dans un fichier PDF.
   * Crée un document PDF formaté contenant toutes les informations du bateau,
   * y compris son image et ses équipements s'ils sont disponibles.
   *
   * @param boat Bateau dont les informations doivent être exportées
   * @param filePath Chemin où le fichier PDF sera créé
   * @throws FileNotFoundException si le chemin du fichier est invalide
   * @throws MalformedURLException si l'URL de l'image du bateau est invalide
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
   * Exporte les informations de tous les bateaux dans un seul fichier PDF.
   * Crée un document PDF contenant une section pour chaque bateau de la flotte.
   *
   * @param boats Liste des bateaux à exporter
   * @param filePath Chemin où le fichier PDF sera créé
   * @throws FileNotFoundException si le chemin du fichier est invalide
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
