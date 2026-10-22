package com.stilinsky.cadastro_de_funcionarios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloController {
    @FXML
    private Hyperlink btnLogar;

    @FXML
    protected void handleLogarConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stilinsky/cadastro_de_funcionarios/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnLogar.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir a tela de cadastro.");
        }
    }
}
