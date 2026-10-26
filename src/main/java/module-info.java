module com.stilinsky.cadastro_de_funcionarios {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    requires static lombok;
    requires jakarta.persistence;
    requires spring.data.jpa;

    opens com.stilinsky.cadastro_de_funcionarios to javafx.fxml;
    opens com.stilinsky.cadastro_de_funcionarios.controller to javafx.fxml;

    exports com.stilinsky.cadastro_de_funcionarios;
    exports com.stilinsky.cadastro_de_funcionarios.controller;
}