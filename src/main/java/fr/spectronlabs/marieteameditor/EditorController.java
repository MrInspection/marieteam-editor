package fr.spectronlabs.marieteameditor;

import java.io.File;
import java.util.List;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.models.Boat;
import fr.spectronlabs.marieteameditor.services.BoatService;
import fr.spectronlabs.marieteameditor.utils.PdfBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * Contrôleur principal de l'interface graphique de MarieTeam Editor.
 * Gère les interactions utilisateur et la logique de l'application.
 * Cette classe fait le lien entre l'interface utilisateur (FXML) et les services métier.
 */
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

  /**
   * Initialise le contrôleur et charge les données initiales.
   * Cette méthode est appelée automatiquement par JavaFX après le chargement du FXML.
   * Elle établit la connexion à la base de données, charge la liste des bateaux
   * et configure les listeners pour la mise à jour du formulaire.
   */
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

  /**
   * Met à jour le formulaire avec les informations du bateau sélectionné.
   * Remplit tous les champs du formulaire avec les données du bateau choisi
   * dans le ComboBox et met à jour l'aperçu de l'image si disponible.
   *
   * @param selectedBoatName Le nom du bateau sélectionné dans le ComboBox
   */
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

  /**
   * Gère la sauvegarde des modifications apportées au bateau sélectionné.
   * Vérifie la validité des données avant la sauvegarde et affiche un message
   * de confirmation ou d'erreur selon le résultat.
   * Note: Les modifications sont actuellement sauvegardées uniquement en mémoire.
   * Une future version devrait implémenter la persistance en base de données.
   */
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

  /**
   * Gère l'export des données en PDF.
   * Affiche une boîte de dialogue permettant à l'utilisateur de choisir entre
   * l'export du bateau sélectionné ou de tous les bateaux de la flotte.
   * L'utilisateur peut ensuite choisir l'emplacement du fichier PDF à générer.
   */
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

  /**
   * Exporte les informations du bateau sélectionné en PDF.
   * Vérifie qu'un bateau est sélectionné, demande à l'utilisateur de choisir
   * l'emplacement du fichier et génère le PDF avec les informations détaillées
   * du bateau.
   */
  private void exportSelectedBoat() {
    if (selectedBoat == null) {
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

  /**
   * Exporte les informations de tous les bateaux en PDF.
   * Demande à l'utilisateur de choisir l'emplacement du fichier et génère
   * un PDF contenant les informations détaillées de tous les bateaux de la flotte.
   */
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

  /**
   * Affiche une boîte de dialogue avec un message pour l'utilisateur.
   * Utilisé pour les confirmations, les erreurs et les messages d'information.
   *
   * @param type Le type d'alerte (ERROR, INFORMATION, CONFIRMATION)
   * @param title Le titre de la boîte de dialogue
   * @param content Le message à afficher
   */
  private void showAlertDialog(Alert.AlertType type, String title, String content) {
    Alert dialog = new Alert(type);
    dialog.setTitle(title);
    dialog.setHeaderText(null);
    dialog.setContentText(content);
    dialog.showAndWait();
  }
}
