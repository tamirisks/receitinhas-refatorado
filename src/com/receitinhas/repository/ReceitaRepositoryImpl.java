package com.receitinhas.repository;

import com.receitinhas.exception.DatabaseException;
import com.receitinhas.model.Receita;
import com.receitinhas.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReceitaRepositoryImpl implements ReceitaRepository {

    private static final String SQL_INSERIR =
            "INSERT INTO receita (nome, ingredientes, descricao, rendimento, tempo_de_preparo, tipo_receita) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_BUSCAR_POR_TIPO =
            "SELECT * FROM receita WHERE tipo_receita LIKE ?";

    private static final String SQL_LISTAR_TODAS =
            "SELECT * FROM receita";

    @Override
    public void salvar(Receita receita) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_INSERIR)) {

            st.setString(1, receita.getNome());
            st.setString(2, receita.getIngredientes());
            st.setString(3, receita.getDescricao());
            st.setString(4, receita.getRendimento());
            st.setString(5, receita.getTempoDePreparo());
            st.setString(6, receita.getTipoReceita());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar receita: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Receita> buscarPorTipo(String tipo) {
        List<Receita> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_BUSCAR_POR_TIPO)) {

            st.setString(1, "%" + tipo + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearReceita(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar receitas por tipo: " + e.getMessage(), e);
        }

        return resultado;
    }

    @Override
    public List<Receita> listarTodas() {
        List<Receita> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_LISTAR_TODAS);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                resultado.add(mapearReceita(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar receitas: " + e.getMessage(), e);
        }

        return resultado;
    }

    // --- helpers privados ---

    private Receita mapearReceita(ResultSet rs) throws SQLException {
        return new Receita(
                rs.getString("nome"),
                rs.getString("ingredientes"),
                rs.getString("descricao"),
                rs.getString("rendimento"),
                rs.getString("tempo_de_preparo"),
                rs.getString("tipo_receita")
        );
    }
}
