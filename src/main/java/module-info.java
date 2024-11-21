module fr.spectronlabs.marieteameditor {
  requires javafx.controls;
  requires javafx.fxml;
  requires kernel;
  requires layout;
  requires io;
  requires java.sql;


  opens fr.spectronlabs.marieteameditor to javafx.fxml;
  exports fr.spectronlabs.marieteameditor;
}