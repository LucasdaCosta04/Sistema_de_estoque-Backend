package com.sistema.estoque.exception;

/**
 * Exceção de regra de negócio utilizada para indicar falhas decorrentes de
 * validações e regras específicas do domínio da aplicação. Diferentemente
 * de exceções técnicas, esta exceção representa situações previstas
 * que devem ser compreendidas e tratadas pela camada de aplicação.
 */
public class BusinessException extends RuntimeException {

    /**
     * Cria uma nova BusinessException com uma mensagem descritiva.
     *
     * @param message mensagem explicando o motivo da exceção
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Cria uma nova BusinessException com uma mensagem descritiva
     * e a causa original da falha.
     *
     * @param message mensagem explicando o motivo da exceção
     * @param cause exceção original que ocasionou o erro
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
