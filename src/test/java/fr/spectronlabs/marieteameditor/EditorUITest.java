package fr.spectronlabs.marieteameditor;

import fr.spectronlabs.marieteameditor.constants.Constants;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * Test de l'interface utilisateur de MarieTeam Editor
 * Utilise TestFX pour simuler les interactions utilisateur
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EditorUITest extends ApplicationTest {

  private ComboBox<String> boatSelector;
  private TextField boatName;
  private TextField lengthField;
  private TextField widthField;
  private TextField speedField;
  private TextField imageUrl;
  private TextArea equipmentsField;
  private ImageView imagePreview;

  @Override
  public void start(Stage stage) throws Exception {
    new EditorApplication().start(stage);
  }

  @BeforeEach
  public void setUp() {
    // Récupération des éléments de l'interface
    boatSelector = lookup("#boatSelector").query();
    boatName = lookup("#boatName").query();
    lengthField = lookup("#lengthField").query();
    widthField = lookup("#widthField").query();
    speedField = lookup("#speedField").query();
    imageUrl = lookup("#imageUrl").query();
    equipmentsField = lookup("#equipmentsField").query();
    imagePreview = lookup("#imagePreview").query();
  }

  @Test
  @Order(1)
  @DisplayName("1. Test de l'initialisation de l'interface")
  void testUIInitialization() {
    // Vérification du titre de la fenêtre
    verifyThat(window(Constants.APP_NAME), WindowMatchers.isShowing());

    // Vérification de la présence des composants
    assertNotNull(boatSelector, "Le sélecteur de bateau devrait être présent");
    assertNotNull(boatName, "Le champ nom devrait être présent");
    assertNotNull(lengthField, "Le champ longueur devrait être présent");
    assertNotNull(widthField, "Le champ largeur devrait être présent");
    assertNotNull(speedField, "Le champ vitesse devrait être présent");
    assertNotNull(imageUrl, "Le champ URL d'image devrait être présent");
    assertNotNull(equipmentsField, "Le champ équipements devrait être présent");
    assertNotNull(imagePreview, "L'aperçu d'image devrait être présent");

    System.out.println("✅ Interface initialization test passed");
  }

  @Test
  @Order(2)
  @DisplayName("2. Test de sélection d'un bateau")
  void testBoatSelection() {
    // Attendre le chargement des données
    WaitForAsyncUtils.waitForFxEvents();

    // Vérifier que la liste n'est pas vide
    assertFalse(boatSelector.getItems().isEmpty(), "La liste des bateaux ne devrait pas être vide");

    // Sélectionner le premier bateau
    interact(() -> boatSelector.getSelectionModel().selectFirst());

    // Vérifier que les champs sont remplis
    assertFalse(boatName.getText().isEmpty(), "Le nom du bateau devrait être rempli");
    assertFalse(lengthField.getText().isEmpty(), "La longueur devrait être remplie");
    assertFalse(widthField.getText().isEmpty(), "La largeur devrait être remplie");
    assertFalse(speedField.getText().isEmpty(), "La vitesse devrait être remplie");

    System.out.println("✅ Boat selection test passed");
  }

  @Test
  @Order(3)
  @DisplayName("3. Test de modification des champs")
  void testFieldModification() {
    WaitForAsyncUtils.waitForFxEvents();
    
    FxRobot robot = new FxRobot();
    
    // Premier bloc interact pour sauvegarder et modifier
    robot.interact(() -> {
        boatSelector.getSelectionModel().selectFirst();
        
        boatName.setText("Test Boat Modified");
        lengthField.setText("15.5");
        widthField.setText("5.2");
        speedField.setText("25.0");
        equipmentsField.setText("GPS, Radar, Life Jackets");
    });
    
    // Simuler le clic sur le bouton Save Changes
    clickOn("Save Changes");
    WaitForAsyncUtils.waitForFxEvents();
    
    System.out.println("✅ Field modification test passed");
  }

  @Test
  @Order(4)
  @DisplayName("4. Test de validation des champs")
  void testFieldValidation() {
    WaitForAsyncUtils.waitForFxEvents();
    
    FxRobot robot = new FxRobot();
    robot.interact(() -> {
        boatSelector.getSelectionModel().selectFirst();
        
        // Sauvegarder les valeurs originales
        String originalSpeed = speedField.getText();
        String originalLength = lengthField.getText();
        String originalWidth = widthField.getText();
        
        try {
            // Tester les valeurs invalides
            speedField.setText("-10");
            lengthField.setText("abc");
            widthField.setText("");
        } catch (Exception e) {
            // Restaurer les valeurs en cas d'erreur
            speedField.setText(originalSpeed);
            lengthField.setText(originalLength);
            widthField.setText(originalWidth);
            throw e;
        }
    });
    
    // Simuler la sauvegarde
    clickOn("Save Changes");
    WaitForAsyncUtils.waitForFxEvents();
    
    // Vérifier que l'erreur est affichée
    verifyThat(".dialog-pane", NodeMatchers.isVisible());
    clickOn("OK");
    
    System.out.println("✅ Field validation test passed");
  }

  @Test
  @Order(5)
  @DisplayName("5. Test d'export PDF")
  void testPdfExport() {
    WaitForAsyncUtils.waitForFxEvents();
    
    FxRobot robot = new FxRobot();
    robot.interact(() -> boatSelector.getSelectionModel().selectFirst());
    
    // Simuler le clic sur le bouton d'export
    clickOn("Export to PDF");
    WaitForAsyncUtils.waitForFxEvents();
    
    // Vérifier que la boîte de dialogue est affichée
    verifyThat(".dialog-pane", NodeMatchers.isVisible());
    
    // Fermer la boîte de dialogue
    clickOn("Cancel");
    
    System.out.println("✅ PDF export dialog test passed");
  }

  @Test
  @Order(6)
  @DisplayName("6. Test de réactivité de l'interface")
  void testUIResponsiveness() {
    // Test de changement rapide de sélection
    for (int i = 0; i < Math.min(5, boatSelector.getItems().size()); i++) {
      int index = i;
      interact(() -> boatSelector.getSelectionModel().select(index));
      WaitForAsyncUtils.waitForFxEvents();

      // Vérifier que les champs sont mis à jour
      assertFalse(boatName.getText().isEmpty(), "Le nom devrait être mis à jour");
      assertFalse(lengthField.getText().isEmpty(), "La longueur devrait être mise à jour");
    }

    System.out.println("✅ UI responsiveness test passed");
  }

  /**
   * Utilitaire pour interagir avec l'interface de manière sûre
   *
   * @return robot
   */
  public FxRobot interact(Runnable action) {
    FxRobot robot = new FxRobot();
    robot.interact(action);
    return robot;
  }

  /**
   * Classe utilitaire pour les matchers de fenêtre
   */
  private static class WindowMatchers {
    static org.hamcrest.Matcher<Window> isShowing() {
      return new org.hamcrest.TypeSafeMatcher<Window>() {
        @Override
        protected boolean matchesSafely(Window window) {
          return window.isShowing();
        }

        @Override
        public void describeTo(org.hamcrest.Description description) {
          description.appendText("window should be showing");
        }
      };
    }
  }

  /**
   * Classe utilitaire pour les matchers de nœuds
   */
  private static class NodeMatchers {
    static org.hamcrest.Matcher<Node> isVisible() {
      return new org.hamcrest.TypeSafeMatcher<Node>() {
        @Override
        protected boolean matchesSafely(Node node) {
          return node.isVisible();
        }

        @Override
        public void describeTo(org.hamcrest.Description description) {
          description.appendText("node should be visible");
        }
      };
    }
  }
}