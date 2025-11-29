package com.sistema.estoque.exception;

/**
 * Exceção personalizada utilizada quando um recurso solicitado
 * (categoria, produto, movimentação etc.) não é encontrado no sistema.
 *
 * Essa exceção é normalmente lançada pela camada Service
 * quando um ID informado não corresponde a nenhum registro no banco.
 *
 * É capturada pelo ExceptionHandler global para retornar um
 * HTTP 404 (Not Found) de forma padronizada.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor simples que recebe somente a mensagem de erro.
     *
     * @param message descrição detalhada do erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe mensagem e causa original.
     *
     * @param message descrição detalhada do erro
     * @param cause exceção original que ocasionou o problema
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
