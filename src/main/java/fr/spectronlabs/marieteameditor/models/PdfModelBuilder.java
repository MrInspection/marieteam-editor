package fr.spectronlabs.marieteameditor.models;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.*;

import com.itextpdf.layout.properties.TextAlignment;

import java.net.MalformedURLException;

public class PdfModelBuilder {

  public static Paragraph createTitle(String title) {
    return new Paragraph(title)
            .setBold()
            .setFontSize(24)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(20);
  }

  public static Paragraph createBoatDetails(Boat boat) {
    return new Paragraph(
            "Name: " + boat.getName() + "\n" +
                    "Length: " + boat.getLength() + " m\n" +
                    "Width: " + boat.getWidth() + " m\n" +
                    "Speed: " + boat.getSpeed() + " knots")
            .setFontSize(12)
            .setMarginBottom(10);
  }

  public static List createEquipmentList(java.util.List<String> equipments) {
    List list = new List().setListSymbol("• ");
    for (String equipment : equipments) {
      list.add(equipment);
    }
    return list;
  }

  public static Image createBoatImage(String imageUrl) throws MalformedURLException {
    Image img = new Image(ImageDataFactory.create(imageUrl));
    img.setMaxWidth(300);
    img.setAutoScale(true);
    img.setTextAlignment(TextAlignment.CENTER);
    return img;
  }

  public static Div createBoatSection(Boat boat) throws MalformedURLException {
    Div boatSection = new Div();
    boatSection.add(createBoatDetails(boat));

    if (!boat.getEquipments().isEmpty()) {
      boatSection.add(new Paragraph("Equipments:").setBold().setFontSize(14));
      boatSection.add(createEquipmentList(boat.getEquipments()));
    }

    if (boat.getImageUrl().isPresent()) {
      boatSection.add(new Paragraph("Image:").setBold().setFontSize(14));
      boatSection.add(createBoatImage(boat.getImageUrl().get()));
    }

    boatSection.setMarginBottom(20);
    return boatSection;
  }
}
