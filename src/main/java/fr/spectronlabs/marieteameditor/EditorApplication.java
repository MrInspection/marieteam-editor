package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorApplication extends Application {

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