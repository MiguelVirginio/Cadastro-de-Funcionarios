
package com.stilinsky.cadastro_de_funcionarios.controller;

import com.stilinsky.cadastro_de_funcionarios.entity.Funcionario;
import com.stilinsky.cadastro_de_funcionarios.service.FuncionarioService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class LoginController {

    private final FuncionarioService funcionarioService;

    public LoginController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @FXML
    private Hyperlink btnCadastrar;

    @FXML
    private Hyperlink btnRecuperarSenha;

    @FXML
    private Button btnEntrar;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private TextField textField;

    @FXML
    private CheckBox passShow;


    @FXML
    protected void handleCriarConta() {
        try {
            Scene scene = new Scene(
                    com.stilinsky.cadastro_de_funcionarios.FXMLUtil.carregar(
                            "/com/stilinsky/cadastro_de_funcionarios/hello-view.fxml"
                    )
            );

            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Não foi possível abrir a tela de cadastro."
            );
        }
    }

    @FXML
    protected void handleLogin() {
        String email = tfEmail.getText();
        String senha = getSenhaAtual();

        if (email == null || email.isBlank()
                || senha == null || senha.isBlank()) {

            TelaDeAviso.mensagemDeWarnig(
                    "Preencha o e-mail e a senha."
            );
            return;
        }

        Optional<Funcionario> resultado;

        try {
            resultado = funcionarioService.autenticar(email, senha);

        } catch (Exception e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Não foi possível conectar ao banco de dados."
            );
            return;
        }

        if (resultado.isEmpty()) {
            TelaDeAviso.mensagemDeErro(
                    "E-mail ou senha incorretos."
            );
            return;
        }

        Funcionario funcionario = resultado.get();

        System.out.println(
                "Login realizado: " + funcionario.getEmail()
        );


        try {
            Scene scene = new Scene(
                    com.stilinsky.cadastro_de_funcionarios.FXMLUtil.carregar(
                            "/com/stilinsky/cadastro_de_funcionarios/sucesso-view.fxml"
                    )
            );

            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Não foi possível abrir a tela de sucesso."
            );
        }

    }

    @FXML
    protected void handleRecuperarSenha() {

        try {
            Scene scene = new Scene(
                    com.stilinsky.cadastro_de_funcionarios.FXMLUtil.carregar(
                            "/com/stilinsky/cadastro_de_funcionarios/recuperar-senha-view.fxml"
                    )
            );

            Stage stage = (Stage) btnCadastrar.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Erro ao abrir recuperação de senha."
            );
        }
    }

    private String getSenhaAtual() {
        return passShow.isSelected()
                ? textField.getText()
                : pfSenha.getText();
    }

    @FXML
    private void toggleSenha() {
        if (passShow.isSelected()) {
            textField.setText(pfSenha.getText());
            textField.setVisible(true);
            textField.setManaged(true);

            pfSenha.setVisible(false);
            pfSenha.setManaged(false);
        } else {
            pfSenha.setText(textField.getText());
            pfSenha.setVisible(true);
            pfSenha.setManaged(true);

            textField.setVisible(false);
            textField.setManaged(false);
        }
    }

    @FXML
    private void teclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }
}
