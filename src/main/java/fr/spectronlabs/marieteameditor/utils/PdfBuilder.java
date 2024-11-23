package fr.spectronlabs.marieteameditor.utils;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.models.PdfModelBuilder;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

public class PdfBuilder {

  public static void exportBoat(Boat boat, String filePath) throws FileNotFoundException, MalformedURLException {
    PdfWriter writer = new PdfWriter(filePath);
    PdfDocument pdfDoc = new PdfDocument(writer);
    pdfDoc.setDefaultPageSize(PageSize.A4);

    Document document = new Document(pdfDoc);
    document.add(PdfModelBuilder.createTitle("Boat Details"));
    document.add(PdfModelBuilder.createBoatSection(boat));
    document.close();
  }

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
