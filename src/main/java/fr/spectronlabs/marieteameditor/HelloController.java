package fr.spectronlabs.marieteameditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {

  @FXML
  private ComboBox<String> boatSelector;

  @FXML
  private TextField boatName, imageUrl, lengthField, widthField, speedField;

  @FXML
  private TextArea equipmentsField, pdfPreview;

  @FXML
  private ImageView imagePreview;

  @FXML
  public void initialize() {
    // Mock data for boats
    boatSelector.getItems().addAll("Boat A", "Boat B", "Boat C");

    // Add listener for dropdown selection changes
    boatSelector.valueProperty().addListener((observable, oldValue, newValue) -> updateFormForSelectedBoat(newValue));
  }

  private void updateFormForSelectedBoat(String selectedBoat) {
    // Mock data for each boat
    switch (selectedBoat) {
      case "Boat A":
        boatName.setText("Boat A");
        lengthField.setText("30");
        widthField.setText("10");
        speedField.setText("25");
        equipmentsField.setText("Radar, GPS, Life Jackets");
        imageUrl.setText("https://static.wikia.nocookie.net/subnautica/images/2/25/Fox3d-entertainment-cyclops.jpg/revision/latest/scale-to-width-down/660?cb=20180427014349");
        imagePreview.setImage(new Image("https://static.wikia.nocookie.net/subnautica/images/2/25/Fox3d-entertainment-cyclops.jpg/revision/latest/scale-to-width-down/660?cb=20180427014349", true));
        break;
      case "Boat B":
        boatName.setText("Boat B");
        lengthField.setText("40");
        widthField.setText("15");
        speedField.setText("20");
        equipmentsField.setText("Sonar, Anchor, Emergency Rafts");
        imageUrl.setText("https://via.placeholder.com/400");
        imagePreview.setImage(new Image("https://via.placeholder.com/400", true));
        break;
      case "Boat C":
        boatName.setText("Boat C");
        lengthField.setText("50");
        widthField.setText("20");
        speedField.setText("30");
        equipmentsField.setText("Auto Pilot, Navigation System, Life Buoys");
        imageUrl.setText("https://via.placeholder.com/400");
        imagePreview.setImage(new Image("https://via.placeholder.com/400", true));
        break;
      default:
        boatName.clear();
        lengthField.clear();
        widthField.clear();
        speedField.clear();
        equipmentsField.clear();
        imageUrl.clear();
        imagePreview.setImage(null);
        break;
    }
  }

  @FXML
  protected void onGeneratePreview() {
    String previewContent = "Boat Flyer\n" +
            "Boat Name: " + boatName.getText() + "\n" +
            "Dimensions: " + lengthField.getText() + " x " + widthField.getText() + " meters\n" +
            "Speed: " + speedField.getText() + " knots\n" +
            "Equipments: " + equipmentsField.getText();
    pdfPreview.setText(previewContent);
  }

  @FXML
  protected void onExportPdf() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Export PDF");
    alert.setHeaderText(null);
    alert.setContentText("PDF export functionality is not implemented in this version.");
    alert.showAndWait();
  }
}
