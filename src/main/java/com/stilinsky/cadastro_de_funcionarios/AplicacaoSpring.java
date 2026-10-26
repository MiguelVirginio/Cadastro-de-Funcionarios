package com.stilinsky.cadastro_de_funcionarios;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = "com.stilinsky.cadastro_de_funcionarios"
)
@EnableJpaRepositories(
        basePackages = "com.stilinsky.cadastro_de_funcionarios.repository"
)
public class AplicacaoSpring {
}