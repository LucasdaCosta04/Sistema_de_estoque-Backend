package com.sistema.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double precoUnitario;

    @NotBlank(message = "A unidade é obrigatória")
    private String unidade;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    private Integer quantidadeEstoque;

    @NotNull(message = "A quantidade mínima é obrigatória")
    private Integer quantidadeMinima;

    @NotNull(message = "A quantidade máxima é obrigatória")
    private Integer quantidadeMaxima;

    private String categoria;
}
