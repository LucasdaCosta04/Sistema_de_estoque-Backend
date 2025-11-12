package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // 🔍 Buscar produtos cujo nome contenha parte do texto (case-insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // 🏷️ Buscar produtos por categoria (case-insensitive)
    List<Produto> findByCategoriaIgnoreCase(String categoria);

    // ⚠️ Buscar produtos com estoque abaixo do mínimo
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < p.quantidadeMinima")
    List<Produto> findProdutosComEstoqueBaixo();

    // 💲 Buscar produtos por faixa de preço
    List<Produto> findByPrecoUnitarioBetween(Double min, Double max);

    // ✅ ADICIONAR ESTES MÉTODOS PARA OS RELATÓRIOS:

    // Para Relatório de Lista de Preços
    List<Produto> findByOrderByNomeAsc();

    // Para Relatório de Produtos Abaixo do Mínimo (alias para o método existente)
    default List<Produto> findProdutosAbaixoDoMinimo() {
        return findProdutosComEstoqueBaixo();
    }

    // Para Relatório de Produtos por Categoria
    @Query("SELECT p.categoria, COUNT(p) FROM Produto p GROUP BY p.categoria")
    List<Object[]> countProdutosPorCategoria();
}