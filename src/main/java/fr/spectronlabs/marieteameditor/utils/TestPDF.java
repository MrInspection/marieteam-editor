package fr.spectronlabs.marieteameditor.utils;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class TestPDF {

  public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
    String[] equipments = {"Radar", "GPS", "Life Jackets"};

    String path = "labs.pdf";
    PdfWriter writer = new PdfWriter(path);
    PdfDocument pdfDoc = new PdfDocument(writer);
    pdfDoc.setDefaultPageSize(PageSize.A4);
    Document doc = new Document(pdfDoc);

    doc.add(new Paragraph("Hello World"));
    doc.add(new Paragraph("Me"));
    doc.add(new Paragraph("Acteurs : "));
    List l = new List();
    l.setListSymbol("• ");
    for(String a : equipments){
      l.add(a);
    }
    doc.add(l);

    Image img = new Image(ImageDataFactory.create("https://avatars.githubusercontent.com/u/1869588?v=4\""));
    img.setWidth(200);
    img.setHeight(200);
    img.setFixedPosition(350,600);
    doc.add(img);

    doc.close();
    try {
      writer.close();
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }
}
