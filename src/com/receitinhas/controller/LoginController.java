package com.receitinhas.controller;

import com.receitinhas.model.Usuario;
import com.receitinhas.service.UsuarioService;

import java.util.Optional;

public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Tenta autenticar o usuário.
     *
     * @param login login digitado
     * @param senha senha digitada
     * @return {@link Optional} com o usuário, ou vazio se credenciais inválidas
     * @throws IllegalArgumentException propagada do serviço se campos vazios
     */
    public Optional<Usuario> login(String login, String senha) {
        return usuarioService.autenticar(login, senha);
    }

    /**
     * Cadastra um novo usuário.
     *
     * @param nome  nome do usuário
     * @param email e-mail
     * @param senha senha
     * @throws IllegalArgumentException propagada do serviço se dados inválidos
     */
    public void cadastrarUsuario(String nome, String email, String senha) {
        Usuario usuario = new Usuario(nome, email, senha);
        usuarioService.cadastrar(usuario);
    }
}
