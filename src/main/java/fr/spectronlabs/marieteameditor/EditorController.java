package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

  @FXML
  public void initialize() {
    DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
    BoatService service = new BoatService(new BoatQuery(database.getConnection()));
    boats = service.fetchAllBoats();

    boatSelector.getItems().clear();
    for (Boat boat : boats) {
      boatSelector.getItems().add(boat.getName());
    }
    boatSelector.valueProperty().addListener((observable, oldValue, newValue) -> updateFormForSelectedBoat(newValue, boats));
  }

  private void updateFormForSelectedBoat(String selectedBoatName, List<Boat> boats) {
    for (Boat boat : boats) {
      if (boat.getName().equals(selectedBoatName)) {
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
  protected void onExportPdf() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Export PDF");
    alert.setHeaderText(null);
    alert.setContentText("PDF export functionality is not implemented in this version.");
    alert.showAndWait();
  }
}
