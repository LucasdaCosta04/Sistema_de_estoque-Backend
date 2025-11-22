package com.sistema.estoque.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado na aplicação.
 * É utilizada para representar falhas previstas, normalmente associadas a
 * buscas em repositórios que não retornam resultados.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Cria uma nova ResourceNotFoundException com uma mensagem descritiva.
     *
     * @param message mensagem explicando o motivo da exceção
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Cria uma nova ResourceNotFoundException com mensagem e causa original.
     *
     * @param message mensagem explicando o motivo da exceção
     * @param cause exceção original que provocou o erro
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
