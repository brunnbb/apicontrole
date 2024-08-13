package com.jmc.apicontrole.Controllers;

import com.jmc.apicontrole.Models.Classes.*;
import com.jmc.apicontrole.Models.HomeModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class HomeController extends Controller {

    //Apiario tela
    @FXML
    public TextField apicultor, municipio, uf, distrito;
    @FXML
    public DatePicker data_de_inicio;
    @FXML
    public TextField ponto_de_referencia, tipo_de_vegetacao, area_total;
    @FXML
    public ChoiceBox<String> presenca_de_mata_nativa, presenca_de_pomar, tipo_de_apiario;
    @FXML
    public Label qtde_colmeias_lbl, prod_total_lbl;
    @FXML
    public Button atualizar_button, sair_button;

    //Colmeia tela
    @FXML
    ChoiceBox<String> colmeia_id, status, tipo_de_colmeia;
    @FXML
    DatePicker data_de_instalacao_colmeia;
    @FXML
    ChoiceBox<String> especie, agressividade, produtividade, grau_de_limpeza;
    @FXML
    TextField qtde_de_melgueiras;
    @FXML
    TextArea obs_adicionais;
    @FXML
    Button add_colmeia_button, mudar_colmeia_button, salvar_colmeia_button, add_colheita_button;

    private HomeModel homeModel;
    private Apiario apiario;

    @Override
    public void initialize() {
        //Apiario
        homeModel = new HomeModel();
        presenca_de_mata_nativa.getItems().addAll("Sim", "Não");
        presenca_de_pomar.getItems().addAll("Sim", "Não");
        tipo_de_apiario.getItems().addAll("Fixo", "Migratório");

        atualizar_button.setOnAction(_ -> handleAtualizar());
        sair_button.setOnAction(_ -> handleVoltar());

        //Colmeias
        status.getItems().addAll("Ativa", "Abandonada");
        tipo_de_colmeia.getItems().addAll("Langstroth", "Top-bar", "Warre");
        especie.getItems().addAll("Africanizada", "Europeia", "Jataí", "Mandaçaia", "Uruçu", "Jandaíra");
        agressividade.getItems().addAll("Alta", "Média", "Baixa");
        produtividade.getItems().addAll("Alta", "Média", "Baixa");
        grau_de_limpeza.getItems().addAll("Alta", "Média", "Baixa");

        add_colmeia_button.setOnAction(_ -> handleAddColmeia());
        mudar_colmeia_button.setOnAction(_ -> handleCarregarColmeia());
        salvar_colmeia_button.setOnAction(_ -> handleSalvarColmeia());
        //add_colheita_button.setOnAction(_ -> handleAddColheita());

    }

    public void setIds(int apiarioId) {
        apiario = homeModel.getApiarioData(apiarioId);
        loadApiariodata();
        loadColmeias();
    }

    public void loadApiariodata() {
        if (apiario != null) {
            apicultor.setText(apiario.getApicultorNome());
            municipio.setText(apiario.getMunicipio());
            uf.setText(apiario.getUf());
            distrito.setText(apiario.getDistrito());

            data_de_inicio.setValue(apiario.getDataDeInicio());

            ponto_de_referencia.setText(apiario.getPontoDeReferencia());
            tipo_de_vegetacao.setText(apiario.getTipoDeVegetacao());

            presenca_de_mata_nativa.setValue(apiario.getPresencaDeMataNativa());
            presenca_de_pomar.setValue(apiario.getPresencaDePomar());
            tipo_de_apiario.setValue(apiario.getTipoDeApiario());

            qtde_colmeias_lbl.setText(String.valueOf(apiario.getQuantidadeDeColmeias()));
            prod_total_lbl.setText(String.valueOf(apiario.getProducaoTotal()));
            area_total.setText(String.valueOf(apiario.getAreaTotal()));
        }
    }

    public void handleAtualizar() {
        if (apiario != null) {
            apiario.setApicultorNome(apicultor.getText());
            apiario.setMunicipio(municipio.getText());
            apiario.setUf(uf.getText());
            apiario.setDistrito(distrito.getText());

            LocalDate localDate = data_de_inicio.getValue();
            apiario.setDataDeInicio(localDate);

            apiario.setPontoDeReferencia(ponto_de_referencia.getText());
            apiario.setTipoDeVegetacao(tipo_de_vegetacao.getText());
            apiario.setPresencaDeMataNativa(presenca_de_mata_nativa.getValue());
            apiario.setPresencaDePomar(presenca_de_pomar.getValue());
            apiario.setTipoDeApiario(tipo_de_apiario.getValue());

            try {
                apiario.setAreaTotal(Double.parseDouble(area_total.getText()));
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erro lendo área", "O valor da área precisa ser um número real positivo");
                return;
            }

            homeModel.updateApiarioData(apiario);
            showAlert(Alert.AlertType.INFORMATION, "Atualização bem-sucedida", "Os dados foram atualizados com sucesso.");
        }
    }

    public void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User.fxml"));
            Parent root = loader.load();

            UserController userController = loader.getController();
            userController.setUserId(apiario.getApicultorId());

            Stage currentStage = (Stage) sair_button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao carregar", "Não foi possível carregar a tela de usuário.");
        }
    }

    public void loadColmeias(){
        List<String> listaColmeias = homeModel.getColmeiasIds(apiario.getApiarioId());
        colmeia_id.getItems().clear();
        colmeia_id.getItems().addAll(listaColmeias);
        qtde_colmeias_lbl.setText(String.valueOf(colmeia_id.getItems().size()));
    }

    public void handleAddColmeia() {
        homeModel.insertColmeia(apiario.getApiarioId());
        // To after the colmeia is added
        loadColmeias();
        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Colmeia criada com sucesso");
    }

    public void handleCarregarColmeia() {
        try{
            int id = Integer.parseInt(colmeia_id.getValue());
            Colmeia colmeia = homeModel.getColmeiaData(id);

            status.setValue(colmeia.getStatusDaColmeia());
            tipo_de_colmeia.setValue(colmeia.getTipoDeColmeia());

            data_de_instalacao_colmeia.setValue(colmeia.getDataDeInstalacao());

            agressividade.setValue(colmeia.getAgressividade());
            especie.setValue(colmeia.getEspecie());
            produtividade.setValue(colmeia.getProdutividade());
            grau_de_limpeza.setValue(colmeia.getGrauDeLimpeza());
            qtde_de_melgueiras.setText(String.valueOf(colmeia.getQuantidadeDeMelgueiras()));
            obs_adicionais.setText(colmeia.getObservacoesAdicionais());

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR,"Alerta","Selecione uma colmeia para ser carregada");
        }
    }

    public void handleSalvarColmeia() {

        String selectedColmeiaId = colmeia_id.getValue();
        if (selectedColmeiaId == null || selectedColmeiaId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Alerta","Selecione alguma colmeia");
            return;
        }

        try {
            int colmeiaId = Integer.parseInt(selectedColmeiaId);
            Colmeia colmeia = new Colmeia();
            colmeia.setId(colmeiaId);
            colmeia.setGrauDeLimpeza(grau_de_limpeza.getValue());
            colmeia.setTipoDeColmeia(tipo_de_colmeia.getValue());
            colmeia.setEspecie(especie.getValue());
            colmeia.setProdutividade(produtividade.getValue());
            colmeia.setAgressividade(agressividade.getValue());
            colmeia.setQuantidadeDeMelgueiras(Integer.parseInt(qtde_de_melgueiras.getText()));

            LocalDate dataDeInstalacao = data_de_instalacao_colmeia.getValue();
            colmeia.setDataDeInstalacao(dataDeInstalacao);

            colmeia.setStatusDaColmeia(status.getValue());
            colmeia.setObservacoesAdicionais(obs_adicionais.getText());

            colmeia.setApiarioId(apiario.getApiarioId());

            homeModel.updateColmeiaData(colmeia);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso","Sucesso em salvar as informações da colmeia");

        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao salvar colmeia: " + e.getMessage());
        }

    }

}


