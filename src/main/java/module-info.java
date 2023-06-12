module ma.emsi.firstfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires  com.fasterxml.jackson.databind;

    opens ma.emsi.firstfx to javafx.fxml;
    opens  ma.emsi.firstfx.interfaces.dashboard to javafx.fxml;
    exports ma.emsi.firstfx;

    // Add the following line to include the logic.entities package
    exports ma.emsi.firstfx.logic.entities;
    exports ma.emsi.firstfx.interfaces.dashboard;
}


