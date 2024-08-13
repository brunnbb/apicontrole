package com.jmc.apicontrole.Controllers;

import com.jmc.apicontrole.Models.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller{
    @FXML
    private TextField user_field, password_field;;
    @FXML
    private Button login_button, signup_button;

    private LoginModel loginModel;
    private int idUser;

    @Override
    public void initialize() {
        loginModel = new LoginModel();
        login_button.setOnAction(_ -> handleLogin());
        signup_button.setOnAction(_ -> handleSignup());
    }

    private void handleLogin() {
        String username = user_field.getText();
        String password = password_field.getText();

        if (loginModel.isUserValid(username, password)) {
            idUser = loginModel.getUserId(username);
            loadUserView();
        } else {
            showAlert(Alert.AlertType.ERROR, "Alerta", "Usuário ou senha inválida");
        }
    }

    public void handleSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SignUp.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) signup_button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Button fail", "The Sign Up button failed to load");
        }
    }

    public void loadUserView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User.fxml"));
            Parent root = loader.load();

            UserController userController = loader.getController();
            userController.setUserId(idUser);

            Stage currentStage = (Stage) signup_button.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Button fail", "The Login button failed to load");
        }
    }

}
