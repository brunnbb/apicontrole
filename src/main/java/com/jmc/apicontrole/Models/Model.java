package com.jmc.apicontrole.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Model {
    Connection connection;

    public Model() {
        connection = DatabaseConnection.Connector();
        if (connection == null) {
            System.out.println("FALHA EM CONECTAR");
        }
    }

    public List<String> loadItems(String tableName, int idColumn) {
        List<String> items = new ArrayList<>();
        String query = "SELECT " + idColumn + " FROM " + tableName;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Aqui você pode ajustar a forma como as strings são formatadas conforme necessário
                items.add(rs.getString(idColumn));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter itens: " + e.getMessage());
        }

        return items;
    }
}
