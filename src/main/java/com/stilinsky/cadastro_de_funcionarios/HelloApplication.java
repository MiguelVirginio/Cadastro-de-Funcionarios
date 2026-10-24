package com.stilinsky.cadastro_de_funcionarios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(true);
        stage.setWidth(1100);
        stage.setHeight(750);
        // Centraliza a janela
        stage.centerOnScreen();
        stage.setTitle("Cadastro!");
        stage.setScene(scene);
        stage.show();
    }
}
