package com.sistema.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidade responsável por representar produtos no banco de dados.
 * Mapeada para a tabela "produto". Contém validações adicionais
 * na camada de persistência.
 */
@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    /**
     * Identificador único do produto gerado automaticamente no banco.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do produto. Não pode ser nulo ou vazio.
     */
    @NotBlank
    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * Preço unitário do produto. Deve ser um valor maior ou igual a zero.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private Double precoUnitario;

    /**
     * Unidade de medida do produto, como "kg", "un", etc.
     */
    @Column(length = 50)
    private String unidade;

    /**
     * Quantidade atual disponível em estoque. Não pode ser negativa.
     */
    @NotNull
    @Min(0)
    private Integer quantidadeEstoque;

    /**
     * Quantidade mínima permitida em estoque antes de alertar necessidade de reposição.
     */
    @NotNull
    @Min(0)
    private Integer quantidadeMinima;

    /**
     * Quantidade máxima aceitável em estoque.
     */
    @NotNull
    @Min(0)
    private Integer quantidadeMaxima;

    /**
     * Categoria do produto. Opcional e não possui restrições.
     */
    private String categoria;
}
