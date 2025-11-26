package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por acessar os dados da entidade Categoria no banco.
 *
 * Estende JpaRepository, que já fornece operações padrão como salvar,
 * buscar, atualizar e excluir, além de paginação e ordenação.
 *
 * Aqui são declarados métodos adicionais necessários para validações
 * específicas, como verificar nomes duplicados.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Verifica se já existe alguma categoria cadastrada com o nome informado.
     *
     * Usado na criação de novas categorias para impedir duplicação.
     *
     * @param nome Nome da categoria a ser validada
     * @return true se existir categoria com o mesmo nome
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se já existe uma categoria com o mesmo nome,
     * desconsiderando uma categoria específica pelo seu ID.
     *
     * Usado na atualização, permitindo que a categoria mantenha seu próprio nome
     * sem gerar falso positivo de duplicidade.
     *
     * @param nome Nome a ser verificado
     * @param id ID da categoria que deve ser desconsiderada na busca
     * @return true se existir outra categoria com esse nome
     */
    boolean existsByNomeAndIdNot(String nome, Long id);
}
