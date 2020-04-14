module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.presentation to javafx.fxml;
    exports org.openjfx.presentation;
}