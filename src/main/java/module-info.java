module com.jmc.apicontrole {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.jmc.apicontrole to javafx.fxml;
    exports com.jmc.apicontrole;
    exports com.jmc.apicontrole.Controllers;
    exports com.jmc.apicontrole.Models;
    exports com.jmc.apicontrole.Views;
    opens com.jmc.apicontrole.Controllers to javafx.fxml;
}