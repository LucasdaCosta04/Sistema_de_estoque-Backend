package com.sistema.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaCreateDTO {
    @NotBlank @Size(min = 2, max = 100)
    private String nome;

    @Size(max = 250)
    private String descricao;

    // getters/setters
}
