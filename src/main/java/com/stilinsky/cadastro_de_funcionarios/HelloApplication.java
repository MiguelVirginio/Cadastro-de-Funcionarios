
package com.stilinsky.cadastro_de_funcionarios;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.stilinsky.cadastro_de_funcionarios.AplicacaoSpring;

import java.io.IOException;

public class HelloApplication extends Application {

    private ConfigurableApplicationContext contextoSpring;

    @Override
    public void init() {
        contextoSpring = new SpringApplicationBuilder(AplicacaoSpring.class)
                .web(WebApplicationType.NONE)
                .run();

        FXMLUtil.configurar(contextoSpring);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(
                FXMLUtil.carregar(
                        "/com/stilinsky/cadastro_de_funcionarios/Login.fxml"
                )
        );

        stage.setTitle("Cadastro de Funcionários");
        stage.setResizable(true);
        stage.setWidth(1100);
        stage.setHeight(750);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        if (contextoSpring != null) {
            contextoSpring.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
