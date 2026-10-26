module com.stilinsky.cadastro_de_funcionarios {

    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    // Spring Boot
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.aop;
    requires spring.expression;

    // Spring Data JPA e Hibernate
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.orm;
    requires spring.tx;
    requires spring.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // Criptografia de senhas
    requires spring.security.crypto;

    // Lombok
    requires static lombok;

    // Reflexão liberada para JavaFX, Spring e Hibernate
    opens com.stilinsky.cadastro_de_funcionarios;
    opens com.stilinsky.cadastro_de_funcionarios.controller;
    opens com.stilinsky.cadastro_de_funcionarios.service;
    opens com.stilinsky.cadastro_de_funcionarios.config;
    opens com.stilinsky.cadastro_de_funcionarios.repository;
    opens com.stilinsky.cadastro_de_funcionarios.entity;

    exports com.stilinsky.cadastro_de_funcionarios;
    exports com.stilinsky.cadastro_de_funcionarios.controller;
    exports com.stilinsky.cadastro_de_funcionarios.entity;
}