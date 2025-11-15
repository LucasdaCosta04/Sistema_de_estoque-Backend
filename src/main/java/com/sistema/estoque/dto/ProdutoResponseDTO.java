package com.sistema.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private Double precoUnitario;
    private String unidade;
    private Integer quantidadeEstoque;
    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;
    private String categoria;
}