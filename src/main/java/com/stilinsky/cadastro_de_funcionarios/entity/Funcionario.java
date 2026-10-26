package com.stilinsky.cadastro_de_funcionarios.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(
        name = "funcionarios",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_funcionarios_email",
                        columnNames = "email"
                )
        }
)
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "sobrenome", nullable = false, length = 100)
    private String sobrenome;

    @Column(name = "email", nullable = false, length = 180)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    protected Funcionario() {
        // Necessário para o JPA
    }

    public Funcionario(
            String nome,
            String sobrenome,
            String email,
            String senhaHash) {

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    @PrePersist
    protected void antesDeSalvar() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
}