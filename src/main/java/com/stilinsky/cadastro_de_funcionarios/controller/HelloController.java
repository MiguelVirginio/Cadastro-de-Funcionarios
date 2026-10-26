
package com.stilinsky.cadastro_de_funcionarios.controller;

import com.stilinsky.cadastro_de_funcionarios.entity.Funcionario;
import com.stilinsky.cadastro_de_funcionarios.service.FuncionarioService;
import com.stilinsky.cadastro_de_funcionarios.service.funcions_extra.EmailValido;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HelloController {

    private final FuncionarioService funcionarioService;

    public HelloController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSobrenome;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Hyperlink btnLogar;

    @FXML
    protected void handleCadastrar() {
        String nome = tfNome.getText();
        String sobrenome = tfSobrenome.getText();
        String email = tfEmail.getText();
        String senha = pfSenha.getText();

        if (nome == null || nome.isBlank()
                || sobrenome == null || sobrenome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.isBlank()) {

            TelaDeAviso.mensagemDeWarnig(
                    "Preencha todos os campos."
            );
            return;
        }

        if (!EmailValido.emailValido(email.trim())) {
            TelaDeAviso.mensagemDeWarnig(
                    "Digite um e-mail válido."
            );
            return;
        }

        try {
            Funcionario funcionario = funcionarioService.cadastrar(
                    nome,
                    sobrenome,
                    email,
                    senha
            );

            TelaDeAviso.mensagemDeCadastrado(
                    "Cadastro realizado com sucesso!"
            );

            abrirLogin();

        } catch (IllegalArgumentException e) {
            TelaDeAviso.mensagemDeWarnig(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Não foi possível realizar o cadastro. "
                            + "Verifique a conexão com o banco de dados."
            );
        }
    }

    @FXML
    protected void handleLogarConta() {
        abrirLogin();
    }

    private void abrirLogin() {
        try {
            Scene scene = new Scene(
                    com.stilinsky.cadastro_de_funcionarios.FXMLUtil.carregar(
                            "/com/stilinsky/cadastro_de_funcionarios/Login.fxml"
                    )
            );

            Stage stage = (Stage) btnLogar.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            TelaDeAviso.mensagemDeErro(
                    "Não foi possível abrir a tela de login."
            );
        }
    }
}
