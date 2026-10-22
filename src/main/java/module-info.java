module com.stilinsky.cadastro_de_funcionarios {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.stilinsky.cadastro_de_funcionarios to javafx.fxml;
    exports com.stilinsky.cadastro_de_funcionarios;
}