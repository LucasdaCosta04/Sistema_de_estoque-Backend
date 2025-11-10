package com.sistema.estoque.service;

import com.sistema.estoque.model.Produto;
import com.sistema.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Métodos CRUD originais
    public Produto adicionar(Produto p) {
        validarProduto(p);
        return repository.save(p);
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto editar(Long id, Produto p) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        existente.setNome(p.getNome());
        existente.setPrecoUnitario(p.getPrecoUnitario());
        existente.setUnidade(p.getUnidade());
        existente.setQuantidadeEstoque(p.getQuantidadeEstoque());
        existente.setQuantidadeMinima(p.getQuantidadeMinima());
        existente.setQuantidadeMaxima(p.getQuantidadeMaxima());
        existente.setCategoria(p.getCategoria());

        validarProduto(existente);
        return repository.save(existente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

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

    // Métodos adicionais para relatórios e movimentações
    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
    }

    public List<Produto> listarOrdenadoPorNome() {
        return repository.findByOrderByNomeAsc();
    }

    public List<Produto> listarProdutosAbaixoDoMinimo() {
        return repository.findProdutosAbaixoDoMinimo();
    }

    public List<Object[]> contarProdutosPorCategoria() {
        return repository.countProdutosPorCategoria();
    }

    private void validarProduto(Produto p) {
        if (p.getNome() == null || p.getNome().isBlank())
            throw new IllegalArgumentException("Nome obrigatório");
        if (p.getPrecoUnitario() == null || p.getPrecoUnitario() < 0)
            throw new IllegalArgumentException("Preço inválido");
        if (p.getQuantidadeEstoque() == null || p.getQuantidadeEstoque() < 0)
            throw new IllegalArgumentException("Estoque inválido");
        if (p.getQuantidadeMinima() == null || p.getQuantidadeMaxima() == null)
            throw new IllegalArgumentException("Quantidades mín/max obrigatórias");
        if (p.getQuantidadeMinima() > p.getQuantidadeMaxima())
            throw new IllegalArgumentException("Quantidade mínima > máxima");
    }
}