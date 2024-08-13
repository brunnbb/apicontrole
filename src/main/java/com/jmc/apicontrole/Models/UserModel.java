package com.jmc.apicontrole.Models;

import java.sql.*;
import java.util.*;

public class UserModel {
    Connection connection;

    public UserModel() {
        connection = DatabaseConnection.Connector();
        if (connection == null) {
            System.out.println("FALHA EM CONECTAR");
        }
    }

    public String getUsername(int userId) {
        String sql = "SELECT username FROM user WHERE id = ?";
        String username = "";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro em pegar a lista de apiarios: " + e.getMessage());
        }
        return username;
    }

    public List<String> getListaApiarios(int userId) {
        List<String> apiarios = new ArrayList<>();
        String sql = "SELECT apiario_id FROM apiarios WHERE apicultor_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    apiarios.add(resultSet.getString("apiario_id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro em pegar a lista de apiarios: " + e.getMessage());
        }

        return apiarios;
    }

    public void insertApiario(int userId) {
        String sql = "insert into apiarios (apicultor_id) values (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro em inserir apiario na tabela: " + e.getMessage());

        }
    }
}
