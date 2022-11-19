module editor.skilllogiceditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires javacsv;
    requires com.google.gson;

    opens editor to javafx.fxml;
    opens dialog to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    exports editor;
    exports dialog;
    exports model;
    exports controller;
    exports config;
}

