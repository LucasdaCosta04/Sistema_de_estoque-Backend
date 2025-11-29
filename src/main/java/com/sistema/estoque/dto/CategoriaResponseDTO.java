package com.sistema.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO responsável por representar os dados que serão
 * enviados ao cliente quando uma categoria for retornada
 * pela API (response).
 *
 * Este DTO garante que apenas as informações desejadas
 * sejam expostas, evitando enviar entidades completas
 * diretamente ao front-end.
 */
@Getter
@Setter
@AllArgsConstructor
public class CategoriaResponseDTO {

    /**
     * Identificador único da categoria.
     */
    private Long id;

    /**
     * Nome da categoria cadastrada.
     */
    private String nome;

    /**
     * Descrição detalhada da categoria.
     */
    private String descricao;
}
