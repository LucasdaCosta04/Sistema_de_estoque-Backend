package com.sistema.estoque.service;

import com.sistema.estoque.dto.ProdutoDTO;
import com.sistema.estoque.entity.Produto;
import com.sistema.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Adicionar produto
    public ProdutoDTO adicionar(ProdutoDTO dto) {
        Produto produto = toEntity(dto);
        validarProduto(produto);
        Produto salvo = repository.save(produto);
        return toDTO(salvo);
    }

    // Listar todos os produtos
    public List<ProdutoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Editar produto
    public ProdutoDTO editar(Long id, ProdutoDTO dto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        existente.setNome(dto.getNome());
        existente.setPrecoUnitario(dto.getPrecoUnitario());
        existente.setUnidade(dto.getUnidade());
        existente.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        existente.setQuantidadeMinima(dto.getQuantidadeMinima());
        existente.setQuantidadeMaxima(dto.getQuantidadeMaxima());
        existente.setCategoria(dto.getCategoria());
        validarProduto(existente);
        return toDTO(repository.save(existente));
    }

    // Excluir produto
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    // Reajustar preços em massa
    @Transactional
    public void reajustarPrecos(double percentual) {
        if (percentual == 0) throw new IllegalArgumentException("Percentual inválido");
        List<Produto> produtos = repository.findAll();
        for (Produto p : produtos) {
            double novo = p.getPrecoUnitario() * (1.0 + percentual / 100.0);
            if (novo < 0) throw new IllegalArgumentException("Reajuste gerou preço negativo");
            p.setPrecoUnitario(novo);
        }
        repository.saveAll(produtos);
    }

    // Conversão de entidade para DTO
    private ProdutoDTO toDTO(Produto p) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setPrecoUnitario(p.getPrecoUnitario());
        dto.setUnidade(p.getUnidade());
        dto.setQuantidadeEstoque(p.getQuantidadeEstoque());
        dto.setQuantidadeMinima(p.getQuantidadeMinima());
        dto.setQuantidadeMaxima(p.getQuantidadeMaxima());
        dto.setCategoria(p.getCategoria());
        return dto;
    }

    // Conversão de DTO para entidade
    private Produto toEntity(ProdutoDTO dto) {
        Produto p = new Produto();
        p.setId(dto.getId());
        p.setNome(dto.getNome());
        p.setPrecoUnitario(dto.getPrecoUnitario());
        p.setUnidade(dto.getUnidade());
        p.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        p.setQuantidadeMinima(dto.getQuantidadeMinima());
        p.setQuantidadeMaxima(dto.getQuantidadeMaxima());
        p.setCategoria(dto.getCategoria());
        return p;
    }

    // Validação de regras de negócio
    private void validarProduto(Produto p) {
        if (p.getNome() == null || p.getNome().isBlank())
            throw new IllegalArgumentException("Nome obrigatório");
        if (p.getPrecoUnitario() == null || p.getPrecoUnitario() < 0)
            throw new IllegalArgumentException("Preço inválido");
        if (p.getQuantidadeEstoque() == null || p.getQuantidadeEstoque() < 0)
            throw new IllegalArgumentException("Estoque inválido");
        if (p.getQuantidadeMinima() == null || p.getQuantidadeMaxima() == null)
            throw new IllegalArgumentException("Quantidades mínima e máxima obrigatórias");
        if (p.getQuantidadeMinima() > p.getQuantidadeMaxima())
            throw new IllegalArgumentException("Quantidade mínima maior que a máxima");
    }
}
