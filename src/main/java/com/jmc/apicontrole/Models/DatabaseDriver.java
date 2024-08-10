package com.jmc.apicontrole.Models;

import java.sql.*;

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

    // Método retorna uma coluna específica do banco de dados.
    // Parametros: nome da tabela, nome da coluna, id
    // Exemplo: exibir informações do apiario na home
    public String getColumn(String tableName, String columnName, int id){
        if(tableName == null || tableName.trim().isEmpty() || columnName == null || columnName.trim().isEmpty()) {
            System.out.println("Nome da tabela ou coluna não pode ser nulo ou vazio.");
            return null;
        }

        tableName = tableName.trim();
        columnName = columnName.trim();

        String query = "SELECT " + columnName  + " FROM " + tableName + " WHERE ID = ?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resulSet = preparedStatement.executeQuery()){
                if(resulSet.next()){
                    return resulSet.getString(columnName);
                }else{
                    System.out.println("Nenhum valor fornecido.");
                    return null;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Método que altera uma colona específica do banco de dados.
    // Parametros: nome da tabela, nome da coluna, id, novo valor
    // Exemplo: editar valores na home
    public boolean setColumn(String tableName, String columName, int id, String newValue){
        if(tableName == null || tableName.trim().isEmpty() || columName == null || columName.trim().isEmpty()){
            System.out.println("Nome da tabela ou coluna não pode ser nulo ou vazio.");
            return false;
        }

        tableName = tableName.trim();
        columName = columName.trim();

        String query = "UPDATE " + tableName + " SET " + columName + " = ? WHERE ID = ?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setString(1, newValue);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0){
                System.out.println("Edição bem sucessida");
                return true;
            }else{
                System.out.println("Erro ao editar coluna");
                return false;
            }
        }catch(SQLException e){
           e.printStackTrace();
           return false;
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
