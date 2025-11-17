package com.sistema.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
}