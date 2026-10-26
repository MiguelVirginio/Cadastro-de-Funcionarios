package com.stilinsky.cadastro_de_funcionarios.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

@Component
public class SucessoController {

    @FXML
    private void handleConfirmar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Veloce");
        alerta.setHeaderText("Login confirmado!");
        alerta.setContentText(
                "Login feito com sucesso, parabéns!"
        );

        alerta.showAndWait();
    }
}