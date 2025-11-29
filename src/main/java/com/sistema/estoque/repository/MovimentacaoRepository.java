package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Movimentacao;
import com.sistema.estoque.entity.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de banco de dados com a entidade Movimentacao
 */
@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    /**
     * Busca movimentações de um produto específico ordenadas por data (mais recente primeiro)
     */
    List<Movimentacao> findByProdutoIdOrderByDataDesc(Long produtoId);

    /**
     * Busca todas as movimentações ordenadas por data (mais recente primeiro)
     */
    List<Movimentacao> findAllByOrderByDataDesc();

    /**
     * Busca apenas as entradas de um produto específico
     */
    @Query("SELECT m FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'ENTRADA'")
    List<Movimentacao> findEntradasByProdutoId(@Param("produtoId") Long produtoId);

    /**
     * Busca apenas as saídas de um produto específico
     */
    @Query("SELECT m FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'SAIDA'")
    List<Movimentacao> findSaidasByProdutoId(@Param("produtoId") Long produtoId);

    /**
     * Busca os produtos mais movimentados por tipo (entrada ou saída)
     * Retorna lista de arrays [Produto, soma das quantidades]
     */
    @Query("SELECT m.produto, SUM(m.quantidade) FROM Movimentacao m WHERE m.tipo = :tipo GROUP BY m.produto ORDER BY SUM(m.quantidade) DESC")
    List<Object[]> findProdutoMaisMovimentadoPorTipo(@Param("tipo") TipoMovimentacao tipo);
}