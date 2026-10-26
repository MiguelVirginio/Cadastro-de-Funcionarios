
package com.stilinsky.cadastro_de_funcionarios;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;

public final class FXMLUtil {

    private static ApplicationContext contextoSpring;

    private FXMLUtil() {
    }

    public static void configurar(ApplicationContext contexto) {
        contextoSpring = contexto;
    }

    public static Parent carregar(String caminho) throws IOException {
        if (contextoSpring == null) {
            throw new IllegalStateException(
                    "O contexto do Spring ainda não foi configurado."
            );
        }

        URL recurso = FXMLUtil.class.getResource(caminho);

        if (recurso == null) {
            throw new IOException("FXML não encontrado: " + caminho);
        }

        FXMLLoader loader = new FXMLLoader(recurso);
        loader.setControllerFactory(contextoSpring::getBean);

        return loader.load();
    }
}
