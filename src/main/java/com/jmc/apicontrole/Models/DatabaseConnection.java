package com.jmc.apicontrole.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:apicontrole.db");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
