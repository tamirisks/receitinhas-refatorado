package com.receitinhas.service;

import com.receitinhas.model.Usuario;
import com.receitinhas.repository.UsuarioRepository;

import java.util.Optional;


public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Valida e cadastra um novo usuário.
     *
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void cadastrar(Usuario usuario) {
        validar(usuario);
        usuarioRepository.salvar(usuario);
    }

    /**
     * Autentica um usuário pelo login e senha.
     */
    public Optional<Usuario> autenticar(String login, String senha) {
        if (isVazio(login) || isVazio(senha)) {
            throw new IllegalArgumentException("Login e senha são obrigatórios.");
        }
        return usuarioRepository.autenticar(login.trim(), senha);
    }

    // --- validações privadas ---

    private void validar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        if (isVazio(usuario.getNome())) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (isVazio(usuario.getEmail())) {
            throw new IllegalArgumentException("O e-mail do usuário é obrigatório.");
        }
        if (isVazio(usuario.getSenha()) || usuario.getSenha().length() < 4) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres.");
        }
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
