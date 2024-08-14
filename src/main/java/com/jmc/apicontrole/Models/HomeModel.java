package com.jmc.apicontrole.Models;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.jmc.apicontrole.Models.Classes.*;

public class HomeModel {
    Connection connection;

    public HomeModel() {
        connection = DatabaseConnection.Connector();
        if (connection == null) {
            System.out.println("FALHA EM CONECTAR");
        }
    }

    //Para tela de apiario
    public Apiario getApiarioData(int apiarioId) {
        Apiario apiario = null;
        String query = "SELECT * FROM apiarios WHERE apiario_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, apiarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                apiario = new Apiario();
                apiario.setApiarioId(rs.getInt("apiario_id"));
                apiario.setApicultorId(rs.getInt("apicultor_id"));
                apiario.setApicultorNome(rs.getString("apicultor_nome"));
                apiario.setMunicipio(rs.getString("municipio"));
                apiario.setUf(rs.getString("uf"));
                apiario.setDistrito(rs.getString("distrito"));
                apiario.setPontoDeReferencia(rs.getString("ponto_de_referencia"));
                apiario.setQuantidadeDeColmeias(rs.getInt("quantidade_de_colmeias"));
                apiario.setTipoDeApiario(rs.getString("tipo_de_apiario"));
                String dataDeInicioStr = rs.getString("data_de_inicio");
                if (dataDeInicioStr != null) {
                    try {
                        long timestamp = Long.parseLong(dataDeInicioStr);
                        LocalDate localDate = Instant.ofEpochMilli(timestamp)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        apiario.setDataDeInicio(localDate);
                    } catch (NumberFormatException e) {
                        apiario.setDataDeInicio(LocalDate.parse(dataDeInicioStr));
                    }
                } else {
                    apiario.setDataDeInicio(null);
                }
                apiario.setTipoDeVegetacao(rs.getString("tipo_de_vegetacao"));
                apiario.setAreaTotal(rs.getDouble("area_total"));
                apiario.setPresencaDeMataNativa(rs.getString("presenca_de_mata_nativa"));
                apiario.setPresencaDePomar(rs.getString("presenca_de_pomar"));
                apiario.setProducaoTotal(rs.getDouble("producao_total"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pegar o dado do apiário: " + e.getMessage());
        }
        return apiario;
    }

    public void updateApiarioData(Apiario apiario) {
        String query = "INSERT OR REPLACE INTO apiarios " +
                "(apiario_id, apicultor_id, apicultor_nome, municipio, uf, distrito, ponto_de_referencia, " +
                "quantidade_de_colmeias, tipo_de_apiario, data_de_inicio, tipo_de_vegetacao, area_total, " +
                "presenca_de_mata_nativa, presenca_de_pomar, producao_total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            stmt.setInt(1, apiario.getApiarioId());
            stmt.setInt(2, apiario.getApicultorId());
            stmt.setString(3, apiario.getApicultorNome());
            stmt.setString(4, apiario.getMunicipio());
            stmt.setString(5, apiario.getUf());
            stmt.setString(6, apiario.getDistrito());
            stmt.setString(7, apiario.getPontoDeReferencia());
            stmt.setInt(8, apiario.getQuantidadeDeColmeias());
            stmt.setString(9, apiario.getTipoDeApiario());
            stmt.setString(10, apiario.getDataDeInicio() != null ?
                    String.valueOf(apiario.getDataDeInicio().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);
            stmt.setString(11, apiario.getTipoDeVegetacao());
            stmt.setDouble(12, apiario.getAreaTotal());
            stmt.setString(13, apiario.getPresencaDeMataNativa());
            stmt.setString(14, apiario.getPresencaDePomar());
            stmt.setDouble(15, apiario.getProducaoTotal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating apiario data: " + e.getMessage());
        }
    }

    public Double getProdTotal(int apiarioId) {
        String query = "SELECT producao_total FROM apiarios WHERE apiario_id = ?";
        double prodTotal = 0.0;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, apiarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                prodTotal = rs.getDouble("producao_total");
            }

        } catch (SQLException e) {
            System.out.println("Problem with getProdTotal: " + e.getMessage());
        }
        return prodTotal;
    }

    public void setProdTotal(int apiarioId, double prodTotal) {
        String query = "UPDATE apiarios SET producao_total = ? WHERE apiario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, prodTotal);
            stmt.setInt(2, apiarioId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a produção total do apiário: " + e.getMessage());
        }
    }

    //Para tela de colmeias
    public void insertColmeia(int apiarioId) {
        String sql = "insert into hive (apiario_id) values (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, apiarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro em inserir colmeia na tabela: " + e.getMessage());
        }
    }

    public List<String> getColmeiasIds(int apiarioId) {
        List<String> colmeias = new ArrayList<>();
        String sql = "SELECT id FROM hive WHERE apiario_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, apiarioId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    colmeias.add(resultSet.getString("id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro em pegar a lista de colmeias: " + e.getMessage());
        }

        return colmeias;
    }

    public Colmeia getColmeiaData(int hiveId) {
        Colmeia colmeia = null;
        String query = "SELECT * FROM hive WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, hiveId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                colmeia = new Colmeia();
                colmeia.setId(rs.getInt("id"));
                colmeia.setGrauDeLimpeza(rs.getString("grau_de_limpeza"));
                colmeia.setTipoDeColmeia(rs.getString("tipo_de_colmeia"));
                colmeia.setEspecie(rs.getString("especie"));
                colmeia.setProdutividade(rs.getString("produtividade"));
                colmeia.setAgressividade(rs.getString("agressividade"));
                colmeia.setQuantidadeDeMelgueiras(rs.getInt("quantidade_de_melgueiras"));

                // Modificar a leitura e conversão da data
                String dataDeInstalacaoStr = rs.getString("data_de_instalacao");
                if (dataDeInstalacaoStr != null) {
                    try {
                        // Tenta converter como um timestamp em milissegundos
                        long timestamp = Long.parseLong(dataDeInstalacaoStr);
                        LocalDate localDate = Instant.ofEpochMilli(timestamp)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        colmeia.setDataDeInstalacao(localDate);
                    } catch (NumberFormatException e) {
                        // Se não for um timestamp, tenta converter como uma data ISO 8601
                        try {
                            colmeia.setDataDeInstalacao(LocalDate.parse(dataDeInstalacaoStr));
                        } catch (DateTimeParseException ex) {
                            // Se a string não for uma data válida, você pode definir um comportamento de fallback ou tratamento de erro
                            System.out.println("Formato de data inválido: " + ex.getMessage());
                            colmeia.setDataDeInstalacao(null);
                        }
                    }
                } else {
                    colmeia.setDataDeInstalacao(null);
                }

                colmeia.setStatusDaColmeia(rs.getString("status_da_colmeia"));
                colmeia.setObservacoesAdicionais(rs.getString("observacoes_adicionais"));
                colmeia.setApiarioId(rs.getInt("apiario_id"));
                colmeia.setProducaoColmeia(rs.getDouble("prod_colmeia"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pegar o dado da colmeia: " + e.getMessage());
        }
        return colmeia;
    }

    public void updateColmeiaData(Colmeia colmeia) {
        String query = "INSERT OR REPLACE INTO hive " +
                "(id, grau_de_limpeza, tipo_de_colmeia, especie, produtividade, agressividade, " +
                "quantidade_de_melgueiras, data_de_instalacao, status_da_colmeia, observacoes_adicionais, apiario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, colmeia.getId());
            stmt.setString(2, colmeia.getGrauDeLimpeza());
            stmt.setString(3, colmeia.getTipoDeColmeia());
            stmt.setString(4, colmeia.getEspecie());
            stmt.setString(5, colmeia.getProdutividade());
            stmt.setString(6, colmeia.getAgressividade());
            stmt.setInt(7, colmeia.getQuantidadeDeMelgueiras());
            stmt.setString(8, colmeia.getDataDeInstalacao() != null ?
                    String.valueOf(colmeia.getDataDeInstalacao().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);
            stmt.setString(9, colmeia.getStatusDaColmeia());
            stmt.setString(10, colmeia.getObservacoesAdicionais());
            stmt.setInt(11, colmeia.getApiarioId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados da colmeia: " + e.getMessage());
        }
    }

    public Double getColmeiaProd(int hiveId) {
        double prod = 0;

        String query = "SELECT prod_colmeia FROM hive WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, hiveId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                prod = rs.getDouble("prod_colmeia");
            }
        } catch (SQLException e) {
            System.out.println("Error with getColmeiaProd: " + e.getMessage());
        }
        return prod;
    }

    public void setColmeiaProd(int hiveId, double prod) {
        String query = "UPDATE hive SET prod_colmeia = ? where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, prod);
            stmt.setInt(2, hiveId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados da colmeia: " + e.getMessage());
        }
    }
}
