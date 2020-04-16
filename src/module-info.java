module SoftwareOne {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.openjfx to javafx.fxml;
    opens controllers to javafx.fxml;
    exports org.openjfx;
}