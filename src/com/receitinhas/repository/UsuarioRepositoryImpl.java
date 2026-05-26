package com.receitinhas.repository;

import com.receitinhas.exception.DatabaseException;
import com.receitinhas.model.Usuario;
import com.receitinhas.util.DatabaseConnection;

import java.sql.*;
import java.util.Optional;


public class UsuarioRepositoryImpl implements UsuarioRepository {

    private static final String SQL_INSERIR =
            "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

    private static final String SQL_AUTENTICAR =
            "SELECT nome, email, senha FROM usuario WHERE nome = ? AND senha = ?";

    @Override
    public void salvar(Usuario usuario) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_INSERIR)) {

            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenha());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> autenticar(String nome, String senha) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_AUTENTICAR)) {

            st.setString(1, nome);
            st.setString(2, senha);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }

        return Optional.empty();
    }
}
