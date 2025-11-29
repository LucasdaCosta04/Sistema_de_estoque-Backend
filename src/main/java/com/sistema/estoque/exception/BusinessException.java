package com.sistema.estoque.exception;

/**
 * Exceção personalizada utilizada para representar erros de regra de negócio.
 *
 * Diferente de ResourceNotFoundException, que indica ausência de dados,
 * esta exceção é usada quando o usuário tenta executar alguma ação
 * proibida pelas regras do sistema, como:
 *
 * - cadastrar categoria com nome duplicado;
 * - excluir categoria com produtos associados;
 * - movimentação inválida;
 * - produto com estoque maior que o permitido, etc.
 *
 * Esta exceção é capturada pelo ExceptionHandler global e normalmente
 * resulta em um HTTP 400 (Bad Request), informando claramente o motivo
 * do erro ao cliente.
 */
public class BusinessException extends RuntimeException {

    /**
     * Construtor simples que recebe apenas a mensagem do erro.
     *
     * @param message descrição da violação de regra de negócio
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Construtor que recebe mensagem e a causa da exceção original.
     *
     * @param message descrição detalhada da regra violada
     * @param cause exceção original que ocasionou o erro
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
