package com.stilinsky.cadastro_de_funcionarios.controller;


import javafx.scene.control.Alert;


//🧠 Uma forma fácil de lembrar
//TODO
//WARNING = "Ei, atenção! Você precisa corrigir alguma coisa."
//
//ERROR = "Algo deu errado."
//
//INFORMATION = "Só estou te informando alguma coisa."
//----------------------------------------------------------------------
//No seu projeto Veloce, eu faria campo vazio → WARNING
// credenciais inválidas → ERROR
// login/cadastro bem-sucedido → INFORMATION.


public class TelaDeAviso {


    public static void mensagemDeErro(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Erro de Login");
        alert.setHeaderText(mensagem);

        alert.showAndWait();

        // Para Casos de:
        // email ou senha incorretos.
        // Não foi possível conectar ao servidor.

    }
    public static void mensagemDeExcessosDeTentativas(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Erro de Login");
        alert.setHeaderText(mensagem);

        alert.showAndWait();

        // Para Casos de:
        // email ou senha incorretos.
        // Não foi possível conectar ao servidor.

    }

    public static void mensagemDeWarnig(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Erro de Login");
        alert.setHeaderText(mensagem);

        alert.showAndWait();

        // Para casos de:
        // Digite um email válido.
        // Preencha o campo de email.
    }

    public static void mensagemDeCadastrado(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(mensagem); //"Cadrastro realizado"
        alert.setContentText("Bem-vindo!");
        alert.showAndWait();

        // Para casos de:
        // Login/cadastro bem-sucedido
    }

    public static void mensagemDeConfirmacao(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atencao");
        alert.setHeaderText(mensagem); //Deseja realmente sair
        alert.showAndWait();

        // Para casos de:

        // "Deseja realmente sair?"
    }

    private void mostrarAviso(
            String titulo,
            String mensagem) {

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);

        alerta.setHeaderText(null);

        alerta.setContentText(mensagem);

        alerta.showAndWait();

    }
}
