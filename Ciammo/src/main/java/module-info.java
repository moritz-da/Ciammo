module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;

    opens de.hdm_stuttgart.mi to javafx.fxml;
    opens de.hdm_stuttgart.mi.Users to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;
    opens de.hdm_stuttgart.mi.Wgs to javafx.fxml, com.fasterxml.jackson.databind;
    opens de.hdm_stuttgart.mi.GuiController to javafx.fxml;
    opens de.hdm_stuttgart.mi.Threads to javafx.fxml;
    opens de.hdm_stuttgart.mi.Helper to javafx.fxml;

    exports de.hdm_stuttgart.mi;
    exports de.hdm_stuttgart.mi.GuiController;
    exports de.hdm_stuttgart.mi.Wgs;
    exports de.hdm_stuttgart.mi.Users;
    exports de.hdm_stuttgart.mi.Threads;
    exports de.hdm_stuttgart.mi.Helper;
}