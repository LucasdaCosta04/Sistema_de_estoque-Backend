package com.sistema.estoque.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoCreateDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser positivo")
    private Double precoUnitario;

    @Size(max = 50)
    private String unidade;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer quantidadeEstoque;

    @NotNull(message = "A quantidade mínima é obrigatória")
    @Min(value = 0, message = "A quantidade mínima não pode ser negativa")
    private Integer quantidadeMinima;

    @NotNull(message = "A quantidade máxima é obrigatória")
    @Min(value = 0, message = "A quantidade máxima não pode ser negativa")
    private Integer quantidadeMaxima;

    private String categoria;
}