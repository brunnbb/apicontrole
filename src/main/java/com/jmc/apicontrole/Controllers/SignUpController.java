package com.jmc.apicontrole.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import com.jmc.apicontrole.Models.SignUpModel;

public class SignUpController extends Controller {
    @FXML
    private TextField user_field, password_field;
    @FXML
    private Button create_user_button, return_button;

    private SignUpModel model;

    @Override
    public void initialize() {
        model = new SignUpModel();
        create_user_button.setOnAction(_ -> handleCreateUser());
        return_button.setOnAction(_ -> handleReturn());
    }

    public void handleCreateUser() {
        String username = user_field.getText();
        String password = password_field.getText();

        if (model.isUsernameAvailable(username)) {
            showAlert(Alert.AlertType.INFORMATION, "Criação de um novo usuário bem sucedida", "Bem-vindo " + username + "!");
            model.insertUser(username, password);
            handleReturn();
        } else {
            showAlert(Alert.AlertType.ERROR, "Alerta", "Esse nome de usuário já está em uso");
        }
    }

    public void handleReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) return_button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Button fail", "The Return button failed to load");
        }
    }

}
