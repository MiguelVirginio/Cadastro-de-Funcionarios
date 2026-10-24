package com.stilinsky.cadastro_de_funcionarios.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;


public class LoginController {
    @FXML
    private Hyperlink btnCadastrar;
    @FXML
    private Hyperlink btnRecuperarSenha;
    @FXML
    protected Button btnEntrar;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private TextField textField;
    @FXML
    private CheckBox passShow;


    private String SenhaVdd = "teste";
    private String Emailvdd = "teste";

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
 
    int tentativas = 3;
    @FXML
    protected void handleLogin() {

        String email = tfEmail.getText().trim();
        String senha = getSenhaAtual().trim();

        // 1. Validar campos vazios
        if (email.isEmpty() || senha.isEmpty()) {
            TelaDeAviso.mensagemDeWarnig(
                    "Preencha os campos corretamente"
            );
            return;
        }

        // 2. Validar credenciais
        if (!email.equals(Emailvdd) || !senha.equals(SenhaVdd)) {
            TelaDeAviso.mensagemDeErro(
                    "E-mail ou senha estão incorretos"
            );
            return;
        }

        // 3. Login realizado com sucesso

        if (email.equals(Emailvdd) && senha.equals(SenhaVdd)) {
            System.out.println("Email e Senha Corretos, Bem vindo: " + email);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/stilinsky/cadastro_de_funcionarios/sucesso-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) btnCadastrar.getScene().getWindow();
                stage.setScene(scene);

                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao abrir a tela de cadastro.");
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Erro ao abrir a tela de cadastro.");
            }
        }
    }

    private String getSenhaAtual() {
        return passShow.isSelected()
                ? textField.getText()
                : pfSenha.getText();
    }

    // ==============================
    // 👁 MOSTRAR/OCULTAR SENHA
    // ==============================
    @FXML
    private void toggleSenha() {
        if (passShow.isSelected()) {
            textField.setText(pfSenha.getText());
            textField.setVisible(true);
            pfSenha.setVisible(false);
        } else {
            pfSenha.setText(textField.getText());
            pfSenha.setVisible(true);
            textField.setVisible(false);
        }
    }

    @FXML
    private void teclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

}
