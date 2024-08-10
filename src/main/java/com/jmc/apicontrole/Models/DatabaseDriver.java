package com.jmc.apicontrole.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver(){
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:apicontrole.db");
            System.out.println("Conexao estabelecida com sucesso");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void displayTableData(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            System.out.println("Nome da tabela não pode ser nulo ou vazio.");
            return;
        }

        tableName = tableName.trim();

        String query = "SELECT * FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectionValid(){
        try{
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
