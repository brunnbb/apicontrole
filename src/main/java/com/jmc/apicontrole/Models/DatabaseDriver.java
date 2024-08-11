package com.jmc.apicontrole.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // Metodo que verifica se determinado valor existe no db
    // Parametros: nome da tabela, nome da coluna, valor a ser buscado
    // Exemplo: quando o usuário fazer login, é necessário verificar se o username digitado por ele existe no db
    public boolean valueExists(String tableName, String columName, String value){
        if(tableName == null || tableName.trim().isEmpty() || columName == null || columName.trim().isEmpty() || value == null || value.trim().isEmpty()){
            System.out.println("Nome da tabela ou coluna nao pode ser nulo ou vazio.");
            return false;
        }

        tableName = tableName.trim();
        columName = columName.trim();

        String query = "SELECT 1 FROM " + tableName + " WHERE " + columName + " = ? LIMIT 1";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, value);

            try(ResultSet resulSet = preparedStatement.executeQuery()){
                return resulSet.next();
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Função retorna todas as colunas de uma determinada tabela
    // Parametros: nome da tabela, nome da coluna
    // Exemplo: listar todos os id de apiários disponiveis para um determinado usuário
    public String[] getAllColumnValues(String tableName, String columnName){
        if(tableName == null || tableName.trim().isEmpty() || columnName == null || columnName.trim().isEmpty()){
            System.out.println("Nome da tabela ou coluna não pode ser luno ou vazio.");
            return null;
        }

        tableName = tableName.trim();
        columnName = columnName.trim();

        String query = "SELECT " + columnName + " FROM "  + tableName;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                List<String> valueColumn = new ArrayList<>();

                while(resultSet.next()){
                   valueColumn.add(resultSet.getString(columnName));
                }

                return valueColumn.toArray(new String[0]);
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // Método que deleta um registro especifico da tabela
    // Parametros: nome da tabela e id
    // Exemplo: deletar user, apiario, colmeia ou colheita
    public boolean deleteRow(String tableName, int id){
        if(tableName == null || tableName.trim().isEmpty() || id < 0){
            System.out.println("Nome da tabela  não pode ser nuno ou vazio e id deve ser maior que 0.");
            return false;
        }

        tableName = tableName.trim();
        String query =  "DELETE FROM " + tableName + " WHERE id = ?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Registro da tabela " + tableName + " com id " + id + " foi apagada com sucesso");
                return true;
            }else{
                System.out.println("Nenhum registro encontrado");
                return false;
            }
        }catch(SQLException e){
           e.printStackTrace();
           return false;
        }
    }

    public boolean createUser(String username, String password){
        if(username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()){
            System.out.println("username ou password nao podem ser nulos ou vazios");
            return false;
        }
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Usuário " + username + " criado com sucesso");
                return true;
            }else{
                System.out.println("Falha ao criar usuário.");
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createApiario(int apicultorId, String municipio, String uf, String distrito,
                                 String pontoDeReferencia, int quantidadeDeColmeias, String tipoDeApiario,
                                 String dataDeInicio, String tipoDeVegetacao, Double areaTotal,
                                 String presencaDeMataNativa, String presencaDePomar, Double producaoTotal) {
        if(municipio == null || municipio.trim().isEmpty() ||
                uf == null || uf.trim().isEmpty() ||
                tipoDeApiario == null || tipoDeApiario.trim().isEmpty() ||
                dataDeInicio == null || dataDeInicio.trim().isEmpty() ||
                presencaDeMataNativa == null || (!presencaDeMataNativa.equals("Sim") && !presencaDeMataNativa.equals("Não")) ||
                presencaDePomar == null || (!presencaDePomar.equals("Sim") && !presencaDePomar.equals("Não"))) {
            System.out.println("Campos obrigatórios não podem ser nulos ou vazios e devem estar em um formato válido.");
            return false;
        }

        String query = "INSERT INTO apiario (apicultor_id, municipio, uf, distrito, ponto_de_referencia, " +
                "quantidade_de_colmeias, tipo_de_apiario, data_de_inicio, tipo_de_vegetacao, " +
                "area_total, presenca_de_mata_nativa, presenca_de_pomar, producao_total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, apicultorId);
            preparedStatement.setString(2, municipio);
            preparedStatement.setString(3, uf);
            preparedStatement.setString(4, distrito);
            preparedStatement.setString(5, pontoDeReferencia);
            preparedStatement.setInt(6, quantidadeDeColmeias);
            preparedStatement.setString(7, tipoDeApiario);
            preparedStatement.setString(8, dataDeInicio);
            preparedStatement.setString(9, tipoDeVegetacao);
            preparedStatement.setDouble(10, areaTotal != null ? areaTotal : 0.0);
            preparedStatement.setString(11, presencaDeMataNativa);
            preparedStatement.setString(12, presencaDePomar);
            preparedStatement.setDouble(13, producaoTotal != null ? producaoTotal : 0.0);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Apiário criado com sucesso.");
                return true;
            }else {
                System.out.println("Falha ao criar o apiário.");
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createHive(String grauDeLimpeza, String tipoDeColmeia, String especie, String produtividade,
                              String agressividade, Integer quantidadeDeMelgueiras, String dataDeInstalacao,
                              String statusDaColmeia, String observacoesAdicionais, int apiarioId) {
        if(tipoDeColmeia == null || tipoDeColmeia.trim().isEmpty() ||
                especie == null || especie.trim().isEmpty() ||
                dataDeInstalacao == null || dataDeInstalacao.trim().isEmpty() ||
                statusDaColmeia == null || statusDaColmeia.trim().isEmpty()) {
            System.out.println("Campos obrigatórios não podem ser nulos ou vazios.");
            return false;
        }

        String query = "INSERT INTO hive (grau_de_limpeza, tipo_de_colmeia, especie, produtividade, " +
                "agressividade, quantidade_de_melgueiras, data_de_instalacao, status_da_colmeia, " +
                "observacoes_adicionais, apiario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, grauDeLimpeza);
            preparedStatement.setString(2, tipoDeColmeia);
            preparedStatement.setString(3, especie);
            preparedStatement.setString(4, produtividade);
            preparedStatement.setString(5, agressividade);
            preparedStatement.setObject(6, quantidadeDeMelgueiras, java.sql.Types.INTEGER);
            preparedStatement.setString(7, dataDeInstalacao);
            preparedStatement.setString(8, statusDaColmeia);
            preparedStatement.setString(9, observacoesAdicionais);
            preparedStatement.setInt(10, apiarioId);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Colmeia criada com sucesso.");
                return true;
            }else{
                System.out.println("Falha ao criar a colmeia.");
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createColheita(int hiveId, String dataColheita, double quantidadeMel) {
        if(dataColheita == null || dataColheita.trim().isEmpty()) {
            System.out.println("Data da colheita não pode ser nula ou vazia.");
            return false;
        }

        String query = "INSERT INTO colheita (hive_id, data_colheita, quantidade_mel) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hiveId);
            preparedStatement.setString(2, dataColheita);
            preparedStatement.setDouble(3, quantidadeMel);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Colheita criada com sucesso.");
                return true;
            }else {
                System.out.println("Falha ao criar a colheita.");
                return false;
            }
        }catch(SQLException e) {
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
