package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade Produto
 * no banco utilizando Spring Data JPA. Inclui consultas derivadas e consultas
 * específicas via JPQL para suporte às funcionalidades do sistema de estoque.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca produtos cujo nome contenha o texto informado, ignorando diferenças de caixa.
     *
     * @param nome trecho a ser pesquisado no nome do produto
     * @return lista de produtos que contenham o trecho informado
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca produtos por categoria ignorando diferenças entre maiúsculas e minúsculas.
     *
     * @param categoria nome da categoria
     * @return lista de produtos pertencentes à categoria informada
     */
    List<Produto> findByCategoriaIgnoreCase(String categoria);

    /**
     * Retorna produtos cujo estoque atual esteja abaixo da quantidade mínima definida.
     *
     * @return lista de produtos com estoque inferior ao mínimo
     */
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < p.quantidadeMinima")
    List<Produto> findProdutosComEstoqueBaixo();

    /**
     * Busca produtos filtrando por uma faixa de preço mínima e máxima.
     *
     * @param min valor mínimo do preço unitário
     * @param max valor máximo do preço unitário
     * @return lista de produtos dentro da faixa de preço definida
     */
    List<Produto> findByPrecoUnitarioBetween(Double min, Double max);

    /**
     * Retorna todos os produtos ordenados alfabeticamente pelo nome.
     * Utilizado em relatórios de listagem de preços.
     *
     * @return lista de produtos ordenados pelo nome em ordem crescente
     */
    List<Produto> findByOrderByNomeAsc();

    /**
     * Alias para {@link #findProdutosComEstoqueBaixo()} para fins de relatórios.
     *
     * @return lista de produtos com estoque abaixo do mínimo
     */
    default List<Produto> findProdutosAbaixoDoMinimo() {
        return findProdutosComEstoqueBaixo();
    }

    /**
     * Retorna a quantidade de produtos agrupados por categoria.
     * O resultado retornado é uma lista de arrays contendo:
     * [0] nome da categoria (String)
     * [1] quantidade de produtos (Long)
     *
     * @return lista contendo categoria e respectiva quantidade total
     */
    @Query("SELECT p.categoria, COUNT(p) FROM Produto p GROUP BY p.categoria")
    List<Object[]> countProdutosPorCategoria();
}
