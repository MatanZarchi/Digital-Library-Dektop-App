module com.example.digitallibraryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.kordamp.bootstrapfx.core;

    opens com.hit.digitallibrary to javafx.fxml, com.google.gson;
    exports com.hit.digitallibrary;

}