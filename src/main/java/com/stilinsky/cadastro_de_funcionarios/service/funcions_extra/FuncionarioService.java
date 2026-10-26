package com.stilinsky.cadastro_de_funcionarios.service;

import com.stilinsky.cadastro_de_funcionarios.entity.Funcionario;
import com.stilinsky.cadastro_de_funcionarios.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Funcionario cadastrar(
            String nome,
            String sobrenome,
            String email,
            String senha) {

        if (nome == null || nome.isBlank()
                || sobrenome == null || sobrenome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "Preencha todos os campos."
            );
        }

        email = email.trim().toLowerCase(Locale.ROOT);

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        Funcionario funcionario = new Funcionario(
                nome.trim(),
                sobrenome.trim(),
                email,
                passwordEncoder.encode(senha)
        );

        return repository.save(funcionario);
    }

    @Transactional(readOnly = true)
    public Optional<Funcionario> autenticar(
            String email,
            String senha) {

        if (email == null || senha == null
                || email.isBlank() || senha.isBlank()) {
            return Optional.empty();
        }

        return repository.findByEmailIgnoreCase(email.trim())
                .filter(funcionario ->
                        passwordEncoder.matches(
                                senha,
                                funcionario.getSenhaHash()
                        )
                );
    }
}