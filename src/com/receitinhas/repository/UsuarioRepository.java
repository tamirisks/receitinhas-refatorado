package com.receitinhas.repository;

import com.receitinhas.model.Usuario;
import java.util.Optional;

/**
 * Contrato de acesso a dados para {@link Usuario}.
 */
public interface UsuarioRepository {

    /**
     * Persiste um novo usuário no banco de dados.
     */
    void salvar(Usuario usuario);

    /**
     * Busca um usuário pelo nome e senha (autenticação).
     *
     * @param nome  nome/login digitado
     * @param senha senha digitada
     * @return {@link Optional} com o usuário se encontrado, ou vazio
     */
    Optional<Usuario> autenticar(String nome, String senha);
}
