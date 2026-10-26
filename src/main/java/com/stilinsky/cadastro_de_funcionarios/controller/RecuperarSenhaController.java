package com.stilinsky.cadastro_de_funcionarios.controller;

import com.stilinsky.cadastro_de_funcionarios.FXMLUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RecuperarSenhaController {

    @FXML
    private TextField tfEmail;

    @FXML
    private void handleRecuperarSenha() {
        String email = tfEmail.getText().trim();

        if (email.isEmpty()) {
            mostrarAviso(
                    Alert.AlertType.WARNING,
                    "Campo obrigatório",
                    "Informe o e-mail cadastrado."
            );
            return;
        }

        if (!email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            mostrarAviso(
                    Alert.AlertType.WARNING,
                    "E-mail inválido",
                    "Informe um endereço de e-mail válido."
            );
            return;
        }

        mostrarAviso(
                Alert.AlertType.INFORMATION,
                "Solicitação recebida",
                "A solicitação de recuperação foi registrada.\n\n"
                        + "Para recuperar a senha de verdade, "
                        + "é necessário implementar o envio de um link "
                        + "ou código de recuperação."
        );
    }

    @FXML
    private void handleVoltarLogin() {
        try {
            Scene scene = new Scene(
                    FXMLUtil.carregar(
                            "/com/stilinsky/cadastro_de_funcionarios/Login.fxml"
                    )
            );

            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            mostrarAviso(
                    Alert.AlertType.ERROR,
                    "Erro",
                    "Não foi possível abrir a tela de login."
            );
        }
    }

    private void mostrarAviso(
            Alert.AlertType tipo,
            String titulo,
            String mensagem) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle("Veloce");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}