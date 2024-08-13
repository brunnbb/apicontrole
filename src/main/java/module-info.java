module com.jmc.apicontrole {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.emojione;
    requires java.sql;
    requires bcrypt;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens com.jmc.apicontrole to javafx.fxml;
    exports com.jmc.apicontrole;
    exports com.jmc.apicontrole.Controllers;
    exports com.jmc.apicontrole.Models;
    opens com.jmc.apicontrole.Controllers to javafx.fxml;
}