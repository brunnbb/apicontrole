package com.jmc.apicontrole.Models;

import java.sql.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginModel implements Model {

    public LoginModel() {
        if (connection == null) {
            System.out.println("FALHA EM CONECTAR");
        }
    }

    public boolean isUserValid(String username, String password) {
        String sql = "select * from user where username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet resultSets = pstmt.executeQuery()) {
                if (resultSets.next()) {
                    String storedHash = resultSets.getString("password");
                    return BCrypt.verifyer().verify(password.toCharArray(), storedHash).verified;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro em validar user: " + e.getMessage());
        }
        return false;
    }

    public int getUserId(String username){
        String sql = "select * from user where username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet resultSets = pstmt.executeQuery()) {
                if (resultSets.next()) {
                    return resultSets.getInt("id");
                } else {
                    System.out.println("Username not found");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro em validar user: " + e.getMessage());
        }
        return -1;
    }
}
