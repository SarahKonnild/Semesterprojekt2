module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires postgresql;

    opens org.openjfx.presentation to javafx.fxml;
    exports org.openjfx.presentation;
}