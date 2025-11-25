package com.sistema.estoque.dto;

import com.sistema.estoque.entity.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para transferência de dados de movimentação
 * entre as camadas do sistema (Controller ↔ Service)
 * Responsável por validar e transportar dados de movimentações de estoque
 * sem expor a entidade completa do domínio
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    /**
     * ID único da movimentação (gerado automaticamente pelo banco)
     * Opcional na criação, obrigatório na atualização
     */
    private Long id;

    /**
     * ID do produto relacionado à movimentação
     * Validação: Não pode ser nulo - deve referenciar um produto existente
     */
    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    /**
     * Data em que a movimentação ocorreu
     * Validação: Não pode ser nula - define o momento do registro
     */
    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    /**
     * Quantidade de produtos movimentados
     * Validações:
     * - Não pode ser nula
     * - Deve ser maior que zero (não permite movimentação zero ou negativa)
     */
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    /**
     * Tipo da movimentação (ENTRADA ou SAIDA)
     * Validação: Não pode ser nulo - define se é entrada ou saída de estoque
     * - ENTRADA: Adiciona ao estoque
     * - SAIDA: Remove do estoque (sujeito a validação de saldo disponível)
     */
    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;
}