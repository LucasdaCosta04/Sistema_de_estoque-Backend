package com.sistema.service;

import com.sistema.entity.Categoria;
import com.sistema.repository.CategoriaRepository;
import com.sistema.exception.BusinessException;
import com.sistema.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Busca todas as categorias
     */
    public List<Categoria> buscarTodas() {
        return categoriaRepository.findAll();
    }

    /**
     * Busca categoria por ID
     */
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }

    /**
     * Cria uma nova categoria
     * Validação: Nome não pode ser duplicado
     */
    @Transactional
    public Categoria criar(Categoria categoria) {
        // Validação: Nome obrigatório
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }

        // Validação: Nome duplicado
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + categoria.getNome());
        }

        return categoriaRepository.save(categoria);
    }

    /**
     * Atualiza uma categoria existente
     */
    @Transactional
    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = buscarPorId(id);

        // Validação: Nome obrigatório
        if (categoriaAtualizada.getNome() == null || categoriaAtualizada.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome da categoria é obrigatório");
        }

        // Validação: Nome duplicado (exceto a própria categoria)
        if (categoriaRepository.existsByNomeAndIdNot(categoriaAtualizada.getNome(), id)) {
            throw new BusinessException("Já existe outra categoria com o nome: " + categoriaAtualizada.getNome());
        }

        categoriaExistente.setNome(categoriaAtualizada.getNome());
        categoriaExistente.setDescricao(categoriaAtualizada.getDescricao());

        return categoriaRepository.save(categoriaExistente);
    }

    /**
     * Deleta uma categoria
     * Validação: Não pode deletar se houver produtos associados
     */
    @Transactional
    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);

        // Validação: Verificar se há produtos associados
        if (categoria.getProdutos() != null && !categoria.getProdutos().isEmpty()) {
            throw new BusinessException(
                    "Não é possível deletar a categoria pois existem " +
                            categoria.getProdutos().size() + " produto(s) associado(s)"
            );
        }

        categoriaRepository.delete(categoria);
    }
}