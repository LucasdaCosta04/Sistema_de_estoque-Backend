package com.sistema.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object utilizado para representar informações de produtos
 * entre a camada de apresentação e a lógica de negócio. Inclui validações
 * para garantir a integridade dos dados recebidos via API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    /**
     * Identificador único do produto.
     */
    private Long id;

    /**
     * Nome do produto. Campo obrigatório.
     */
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    /**
     * Preço unitário do produto. Deve ser informado e maior que zero.
     */
    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double precoUnitario;

    /**
     * Unidade de medida do produto, por exemplo: kg, un, caixa. Obrigatório.
     */
    @NotBlank(message = "A unidade é obrigatória")
    private String unidade;

    /**
     * Quantidade atual disponível em estoque.
     */
    @NotNull(message = "A quantidade em estoque é obrigatória")
    private Integer quantidadeEstoque;

    /**
     * Quantidade mínima aceitável em estoque antes de alertas de reposição.
     */
    @NotNull(message = "A quantidade mínima é obrigatória")
    private Integer quantidadeMinima;

    /**
     * Quantidade máxima permitida em estoque.
     */
    @NotNull(message = "A quantidade máxima é obrigatória")
    private Integer quantidadeMaxima;

    /**
     * Categoria ou agrupamento do produto (opcional).
     */
    private String categoria;
}
