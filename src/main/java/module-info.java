module com.udu.arcfind {
    requires javafx.controls;
    requires javafx.fxml;

    requires jarchivelib;
    requires java.desktop;

    opens com.udu.arcfind to javafx.fxml;
    exports com.udu.arcfind;
    exports com.udu.arcfind.ui.table;
    opens com.udu.arcfind.ui.table to javafx.fxml;
    exports com.udu.arcfind.ui;
    opens com.udu.arcfind.ui to javafx.fxml;
}