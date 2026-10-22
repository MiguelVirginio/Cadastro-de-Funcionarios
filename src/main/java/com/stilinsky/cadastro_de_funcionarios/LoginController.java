package com.stilinsky.cadastro_de_funcionarios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Hyperlink btnCadastrar;


    @FXML
    protected void handleCriarConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stilinsky/cadastro_de_funcionarios/hello-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir a tela de cadastro.");
        }
    }
}
