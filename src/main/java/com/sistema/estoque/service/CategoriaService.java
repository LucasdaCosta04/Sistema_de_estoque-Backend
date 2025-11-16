package com.sistema.estoque.service;

import com.sistema.estoque.dto.CategoriaCreateDTO;
import com.sistema.estoque.dto.CategoriaResponseDTO;
import com.sistema.estoque.entity.Categoria;
import com.sistema.estoque.exception.BusinessException;
import com.sistema.estoque.exception.ResourceNotFoundException;
import com.sistema.estoque.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = encontrarEntidadePorId(id);
        return toResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO dto) {

        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + dto.getNome());
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return toResponseDTO(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaCreateDTO dto) {

        Categoria categoria = encontrarEntidadePorId(id);

        if (categoriaRepository.existsByNomeAndIdNot(dto.getNome(), id)) {
            throw new BusinessException("Já existe outra categoria com o nome: " + dto.getNome());
        }

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return toResponseDTO(categoriaRepository.save(categoria));
    }

    @Transactional
    public void deletarCategoria(Long id) {

        Categoria categoria = encontrarEntidadePorId(id);

        if (!categoria.getProdutos().isEmpty()) {
            throw new BusinessException(
                    "Não é possível deletar a categoria '" + categoria.getNome() +
                            "' pois existem produtos associados."
            );
        }

        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    private Categoria encontrarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }
}