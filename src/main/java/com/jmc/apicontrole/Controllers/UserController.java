package com.jmc.apicontrole.Controllers;

import com.jmc.apicontrole.Models.Classes.Apiario;
import com.jmc.apicontrole.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController extends Controller {
    @FXML
    private Label bemvindo_label;
    @FXML
    private ChoiceBox<String> apiario_choicebox;
    @FXML
    private Button ir_button, criar_apiario_button, sair_button;

    private UserModel userModel;
    private int userId;
    private List<Apiario> listaApiarios;

    @Override
    public void initialize() {
        userModel = new UserModel();
        ir_button.setOnAction(_ -> handleIr());
        criar_apiario_button.setOnAction(_ -> handleCriar());
        sair_button.setOnAction(_ -> handleSair());
    }

    public void setUserId(int userId) {
        this.userId = userId;
        showUsername();
        loadChoiceBox();
    }

    public void showUsername() {
        bemvindo_label.setText("Bem-vindo " + userModel.getUsername(userId) + ",");
    }

    public void loadChoiceBox() {
        List<String> lista = userModel.getListaApiarios(userId);
        listaApiarios = new ArrayList<>();
        apiario_choicebox.getItems().clear();

        int i = 1;
        for (String s : lista) {
            int apiarioId = Integer.parseInt(s);
            Apiario apiario = new Apiario(userId, apiarioId, i);
            apiario_choicebox.getItems().add(String.valueOf(i));
            listaApiarios.add(apiario);
            i++;
        }
    }

    public void handleIr() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));
            Parent root = loader.load();

            try {
                int numeroDeDisplay= Integer.parseInt(apiario_choicebox.getValue());
                Apiario apiarioSelecionado = null;
                for(Apiario apiario : listaApiarios) {
                    if(apiario.getNumeroDisplay() == numeroDeDisplay) {
                        apiarioSelecionado = apiario;
                    }
                }

                HomeController homeController = loader.getController();
                homeController.setIds(apiarioSelecionado.getApiarioId());

                Stage currentStage = (Stage) ir_button.getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Alerta", "Selecione um apiário válido");
            }

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Button fail", "The Return button failed to load");
        }
    }

    public void handleCriar() {
        userModel.insertApiario(userId);
        loadChoiceBox();
        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Apiário criado com sucesso");
    }

    public void handleSair() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) sair_button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Button fail", "The Return button failed to load");
        }
    }

}
