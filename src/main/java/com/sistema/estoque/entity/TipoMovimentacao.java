package com.sistema.estoque.entity;

/**
 * Define os tipos de movimentação de estoque
 */
public enum TipoMovimentacao {
    /** Entrada de produtos - aumenta o estoque */
    ENTRADA,

    /** Saída de produtos - reduz o estoque */
    SAIDA
}