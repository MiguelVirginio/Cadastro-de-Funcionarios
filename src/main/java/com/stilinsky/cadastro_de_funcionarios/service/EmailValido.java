package com.stilinsky.cadastro_de_funcionarios.service.funcions_extra;

public class EmailValido {
    public static boolean emailValido(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        int quantidadeArroba =
                email.length() - email.replace("@", "").length();

        if (quantidadeArroba != 1) {
            return false;
        }

        String[] partes = email.split("@");

        String usuario = partes[0];
        String dominio = partes[1];

        if (usuario.isEmpty()) {
            return false;
        }

        if (!dominio.contains(".")) {
            return false;
        }

        if (dominio.startsWith(".") || dominio.endsWith(".")) {
            return false;
        }

        if (dominio.contains("..")) {
            return false;
        }

        return true;
    }
}

