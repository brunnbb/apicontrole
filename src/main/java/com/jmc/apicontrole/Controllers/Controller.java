package com.jmc.apicontrole.Controllers;

import javafx.scene.control.Alert;

public abstract class Controller {

    public abstract void initialize();

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

