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

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO) // Converte cada Categoria para DTO
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = encontrarEntidadePorId(id); // Usa o método privado
        return toResponseDTO(categoria); // Converte para DTO
    }

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO createDTO) {
        // Validação (usando o método do seu repo: 'existsByNome')
        if (createDTO.getNome() == null || createDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }
        if (categoriaRepository.existsByNome(createDTO.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + createDTO.getNome());
        }

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(createDTO.getNome());
        novaCategoria.setDescricao(createDTO.getDescricao());

        Categoria categoriaSalva = categoriaRepository.save(novaCategoria);

        return toResponseDTO(categoriaSalva);
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaCreateDTO updateDTO) {

        Categoria categoriaExistente = encontrarEntidadePorId(id);

        if (updateDTO.getNome() == null || updateDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }
        if (categoriaRepository.existsByNomeAndIdNot(updateDTO.getNome(), id)) {
            throw new BusinessException("Já existe outra categoria com o nome: " + updateDTO.getNome());
        }

        categoriaExistente.setNome(updateDTO.getNome());
        categoriaExistente.setDescricao(updateDTO.getDescricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);

        return toResponseDTO(categoriaAtualizada);
    }

    @Transactional
    public void deletarCategoria(Long id) {
        Categoria categoria = encontrarEntidadePorId(id);

        if (produtoRepository.existsByCategoria(categoria)) {
            throw new BusinessException(
                    "Não é possível deletar a categoria '" + categoria.getNome() +
                            "' pois existem produtos associados a ela."
            );
        }

        categoriaRepository.delete(categoria);
    }


    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        // Assume que CategoriaResponseDTO tem construtor (Long, String, String)
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    private Categoria encontrarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }
}