package com.sistema.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidade JPA que representa uma movimentação de estoque no sistema
 *
 * Mapeia a tabela "movimentacao" no banco de dados e registra todas as
 * operações de entrada e saída de produtos do estoque
 */
@Entity
@Table(name = "movimentacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    /**
     * Identificador único da movimentação (chave primária)
     * Gerado automaticamente pelo banco de dados com estratégia de auto incremento
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento Many-to-One com a entidade Produto
     * Cada movimentação está associada a um único produto
     * @JoinColumn define a coluna de chave estrangeira "produto_id" na tabela movimentacao
     * nullable = false garante que toda movimentação deve ter um produto associado
     */
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    /**
     * Data em que a movimentação foi realizada
     * Campo obrigatório para histórico e relatórios temporais
     */
    @NotNull
    @Column(nullable = false)
    private LocalDate data;

    /**
     * Quantidade de produtos movimentados
     * Para ENTRADA: valor positivo que será somado ao estoque
     * Para SAIDA: valor positivo que será subtraído do estoque
     * Validações:
     * - @NotNull: Campo obrigatório
     * - nullable = false: Restrição a nível de banco de dados
     */
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    /**
     * Tipo da movimentação (ENTRADA ou SAIDA)
     * @Enumerated(EnumType.STRING): Armazena o valor do enum como String no banco
     * (mais legível que armazenar como ordinal)
     * Validações:
     * - nullable = false: Tipo é obrigatório
     * - Valores possíveis: ENTRADA, SAIDA
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;
}