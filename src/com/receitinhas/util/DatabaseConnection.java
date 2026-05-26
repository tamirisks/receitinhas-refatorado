package com.receitinhas.util;

import com.receitinhas.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static final String DRIVER  = "com.mysql.cj.jdbc.Driver";
    private static final String URL     = "jdbc:mysql://localhost:3306/receitinha";
    private static final String USER    = "root";
    private static final String DEFAULT_PASSWORD = "G@tinhos16";

    private DatabaseConnection() {
        // utilitária — não instanciar
    }

    /**
     * Abre e retorna uma conexão com o banco.
     *
     * @return {@link Connection} ativa
     * @throws DatabaseException se o driver não for encontrado ou a conexão falhar
     */
    public static Connection getConnection() {
        String password = System.getenv("DATABASE_PASSWORD");
        if (password == null || password.isBlank()) {
            password = DEFAULT_PASSWORD;
        }

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, password);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Driver MySQL não encontrado.", e);
        } catch (SQLException e) {
            throw new DatabaseException("Falha ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Aviso: falha ao fechar conexão — " + e.getMessage());
            }
        }
    }
}
