package com.jmc.apicontrole.Models;

import java.sql.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignUpModel implements Model{

    public SignUpModel() {
        if (connection == null) {
            System.out.println("FALHA EM CONECTAR");
        }
    }

    public boolean isUsernameAvailable(String username) {
        String sql = "select 1 from user where username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet resultSets = pstmt.executeQuery()) {
                return !resultSets.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro em procurar user: " + e.getMessage());
            return false;
        }

    }

    public void insertUser(String username, String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        String sql = "insert into user (username, password) values (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro em inserir user na tabela: " + e.getMessage());

        }
    }

}
