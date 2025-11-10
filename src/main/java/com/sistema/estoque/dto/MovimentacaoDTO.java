package com.sistema.estoque.dto;

import com.sistema.estoque.model.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class MovimentacaoDTO {
    private Long id;

    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;

    // Construtores, Getters e Setters
    public MovimentacaoDTO() {}

    public MovimentacaoDTO(Long produtoId, LocalDate data, Integer quantidade, TipoMovimentacao tipo) {
        this.produtoId = produtoId;
        this.data = data;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
}