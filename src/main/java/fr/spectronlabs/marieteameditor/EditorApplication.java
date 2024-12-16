package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Point d'entrée principal de l'application MarieTeam Editor.
 * Cette classe initialise l'interface graphique JavaFX et configure la fenêtre principale.
 *
 * @author  Moussa, Ousmane (Spectron Labs)
 */
public class EditorApplication extends Application {

  /**
   * Initialise et affiche la fenêtre principale de l'application.
   * Charge l'interface utilisateur depuis le fichier FXML et applique les paramètres de base.
   *
   * @param stage La fenêtre principale de l'application
   * @throws IOException Si le chargement du fichier FXML échoue
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(EditorApplication.class.getResource("editor-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
    stage.setTitle(Constants.APP_NAME);
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}