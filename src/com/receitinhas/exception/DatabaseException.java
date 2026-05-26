package com.receitinhas.exception;

/**
 * Exceção lançada quando ocorre um erro de acesso ao banco de dados.
 * Centraliza o tratamento de erros de persistência no sistema.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
