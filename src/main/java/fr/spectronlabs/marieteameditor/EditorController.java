package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;
import fr.spectronlabs.marieteameditor.utils.PdfBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class EditorController {

  @FXML
  private ComboBox<String> boatSelector;

  @FXML
  private TextField boatName, imageUrl, lengthField, widthField, speedField;

  @FXML
  private TextArea equipmentsField;

  @FXML
  private ImageView imagePreview;

  private List<Boat> boats;
  private Boat selectedBoat;

  @FXML
  public void initialize() {
    DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
    BoatService service = new BoatService(new BoatQuery(database.getConnection()));
    boats = service.fetchAllBoats();

    boatSelector.getItems().clear();
    for (Boat boat : boats) {
      boatSelector.getItems().add(boat.getName());
    }
    boatSelector.valueProperty().addListener((observable, oldValue, newValue) -> updateFormForSelectedBoat(newValue));
  }

  private void updateFormForSelectedBoat(String selectedBoatName) {
    for (Boat boat : boats) {
      if (boat.getName().equals(selectedBoatName)) {
        selectedBoat = boat;
        boatName.setText(boat.getName());
        lengthField.setText(String.valueOf(boat.getLength()));
        widthField.setText(String.valueOf(boat.getWidth()));
        speedField.setText(String.valueOf(boat.getSpeed()));
        equipmentsField.setText(String.join(", ", boat.getEquipments()));
        imageUrl.setText(boat.getImageUrl().orElse("No Image"));
        imagePreview.setImage(new Image(boat.getImageUrl().orElse("No Image"), true));
        break;
      }
    }
  }

  @FXML
  protected void onSaveChanges() {
    if (selectedBoat == null) {
      showAlertDialog(Alert.AlertType.ERROR, "No Boat Selected", "Please select a boat to save changes.");
      return;
    }

    try {
      selectedBoat.setName(boatName.getText());
      selectedBoat.setLength(Double.parseDouble(lengthField.getText()));
      selectedBoat.setWidth(Double.parseDouble(widthField.getText()));
      selectedBoat.setSpeed(Double.parseDouble(speedField.getText()));
      selectedBoat.setImageUrl(imageUrl.getText().isBlank() ? null : imageUrl.getText());

      // Parse equipments from the text area
      String[] equipments = equipmentsField.getText().split(",");
      selectedBoat.setEquipments(List.of(equipments));

      showAlertDialog(Alert.AlertType.INFORMATION, "Changes Saved", "The changes have been saved successfully.");
    } catch (Exception e) {
      System.err.println(" ❌ Save Failed: " + e.getMessage());
      showAlertDialog(Alert.AlertType.ERROR, "Save Failed", "An error occurred while saving the changes.");
    }
  }

  @FXML
  protected void onExportPdf() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Export PDF");
    alert.setHeaderText("Choose Export Option");
    alert.setContentText("Would you like to export the selected boat or all boats?");

    ButtonType exportSelected = new ButtonType("Export Selected");
    ButtonType exportAll = new ButtonType("Export All");
    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(exportSelected, exportAll, cancel);

    alert.showAndWait().ifPresent(buttonType -> {
      if (buttonType == exportSelected) {
        exportSelectedBoat();
      } else if (buttonType == exportAll) {
        exportAllBoats();
      }
    });
  }

  private void exportSelectedBoat() {
    if(selectedBoat == null) {
      showAlertDialog(Alert.AlertType.ERROR, "No Boat Selected", "Please select a boat to export.");
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Selected Boat as PDF");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
      try {
        PdfBuilder.exportBoat(selectedBoat, file.getAbsolutePath());
        showAlertDialog(Alert.AlertType.INFORMATION, "Export successful", "The boat has been successfully exported to PDF.");
      } catch (Exception e) {
        System.err.println(" ❌ PDF Export Failed: " + e.getMessage());
        showAlertDialog(Alert.AlertType.ERROR, "Export failed", "An error occurred while exporting the boat to PDF.");
      }
    }
  }

  private void exportAllBoats() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export MarieTeam Fleet to PDF");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
      try {
        PdfBuilder.exportAllBoats(boats, file.getAbsolutePath());
        showAlertDialog(Alert.AlertType.INFORMATION, "Export successful", "All boats have been successfully export to PDF.");
      } catch (Exception e) {
        System.err.println(" ❌ PDF Export Failed: " + e.getMessage());
        showAlertDialog(Alert.AlertType.ERROR, "Export failed", "An error occurred while exporting the boats to PDF.");
      }
    }
  }

  private void showAlertDialog(Alert.AlertType type, String title, String content) {
    Alert dialog = new Alert(type);
    dialog.setTitle(title);
    dialog.setHeaderText(null);
    dialog.setContentText(content);
    dialog.showAndWait();
  }
}
