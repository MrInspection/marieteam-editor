module fr.spectronlabs.marieteameditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.spectronlabs.marieteameditor to javafx.fxml;
    exports fr.spectronlabs.marieteameditor;
}