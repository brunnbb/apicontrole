package com.jmc.apicontrole.Models;

import java.sql.Connection;

public interface Model {
    Connection connection = DatabaseConnection.Connector();

}

