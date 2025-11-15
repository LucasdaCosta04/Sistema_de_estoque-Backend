package com.sistema.estoque.service;

// 1. Imports de DTOs e Exceções
import com.sistema.estoque.dto.CategoriaCreateDTO;
import com.sistema.estoque.dto.CategoriaResponseDTO;
import com.sistema.estoque.entity.Categoria;
import com.sistema.estoque.exception.BusinessException;
import com.sistema.estoque.exception.ResourceNotFoundException;
import com.sistema.estoque.repository.CategoriaRepository;
import com.sistema.estoque.repository.ProdutoRepository; // Importe o Repo de Produto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    // 2. Injeção de dependência via construtor (melhor prática)
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository; // Necessário para a validação de delete

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Busca todas as categorias e as converte para DTO.
     */
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO) // Converte cada Categoria para DTO
                .collect(Collectors.toList());
    }

    /**
     * Busca categoria por ID e converte para DTO.
     * Este é o método que o seu Controller (linha 78) espera.
     */
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = encontrarEntidadePorId(id); // Usa o método privado
        return toResponseDTO(categoria); // Converte para DTO
    }

    /**
     * Cria uma nova categoria a partir de um DTO.
     */
    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO createDTO) {
        // Validação (usando o método do seu repo: 'existsByNome')
        if (createDTO.getNome() == null || createDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }
        if (categoriaRepository.existsByNome(createDTO.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + createDTO.getNome());
        }

        // Mapeamento: DTO -> Entidade
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(createDTO.getNome());
        novaCategoria.setDescricao(createDTO.getDescricao());
        // 'criadoEm' e 'produtos' são gerenciados pelo JPA

        Categoria categoriaSalva = categoriaRepository.save(novaCategoria);

        // Mapeamento: Entidade -> DTO
        return toResponseDTO(categoriaSalva);
    }

    /**
     * Atualiza uma categoria existente a partir de um DTO.
     * Este é o método que o seu Controller (linha 91) espera.
     */
    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaCreateDTO updateDTO) {
        // 1. Encontra a entidade (ou lança ResourceNotFoundException)
        Categoria categoriaExistente = encontrarEntidadePorId(id);

        // 2. Validações (usando o método do seu repo: 'existsByNomeAndIdNot')
        if (updateDTO.getNome() == null || updateDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }
        if (categoriaRepository.existsByNomeAndIdNot(updateDTO.getNome(), id)) {
            throw new BusinessException("Já existe outra categoria com o nome: " + updateDTO.getNome());
        }

        // 3. Atualiza os dados da entidade
        categoriaExistente.setNome(updateDTO.getNome());
        categoriaExistente.setDescricao(updateDTO.getDescricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);

        // 4. Retorna o DTO atualizado
        return toResponseDTO(categoriaAtualizada);
    }

    /**
     * Deleta uma categoria por ID.
     */
    @Transactional
    public void deletarCategoria(Long id) {
        Categoria categoria = encontrarEntidadePorId(id);

        // Validação: Verificar se há produtos associados
        // (Assume que Produto.java tem @ManyToOne Categoria)
        if (produtoRepository.existsByCategoria(categoria)) {
            throw new BusinessException(
                    "Não é possível deletar a categoria '" + categoria.getNome() +
                            "' pois existem produtos associados a ela."
            );
        }

        categoriaRepository.delete(categoria);
    }


    // --- MÉTODOS PRIVADOS AUXILIARES ---

    /**
     * Converte uma Entidade Categoria para um CategoriaResponseDTO.
     */
    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        // Assume que CategoriaResponseDTO tem construtor (Long, String, String)
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    /**
     * Encontra uma Categoria por ID ou lança ResourceNotFoundException.
     */
    private Categoria encontrarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }
}