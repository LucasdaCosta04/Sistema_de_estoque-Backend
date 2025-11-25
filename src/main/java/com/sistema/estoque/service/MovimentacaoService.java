package com.sistema.estoque.service;

import com.sistema.estoque.dto.MovimentacaoDTO;
import com.sistema.estoque.entity.Movimentacao;
import com.sistema.estoque.entity.Produto;
import com.sistema.estoque.entity.TipoMovimentacao;
import com.sistema.estoque.repository.MovimentacaoRepository;
import com.sistema.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviço para gerenciar operações de movimentação de estoque
 */
@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Cria uma nova movimentação e atualiza o estoque do produto
     */
    @Transactional
    public Movimentacao criarMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        // Busca o produto pelo ID
        Produto produto = produtoRepository.findById(movimentacaoDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + movimentacaoDTO.getProdutoId()));

        // Valida e atualiza o estoque
        validarMovimentacao(produto, movimentacaoDTO.getQuantidade(), movimentacaoDTO.getTipo());
        atualizarEstoque(produto, movimentacaoDTO.getQuantidade(), movimentacaoDTO.getTipo());

        // Cria e salva a movimentação
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setData(movimentacaoDTO.getData() != null ? movimentacaoDTO.getData() : LocalDate.now());
        movimentacao.setQuantidade(movimentacaoDTO.getQuantidade());
        movimentacao.setTipo(movimentacaoDTO.getTipo());

        return movimentacaoRepository.save(movimentacao);
    }

    /**
     * Lista todas as movimentações ordenadas por data (mais recente primeiro)
     */
    public List<Movimentacao> listarTodas() {
        return movimentacaoRepository.findAllByOrderByDataDesc();
    }

    /**
     * Busca uma movimentação pelo ID
     */
    public Movimentacao buscarPorId(Long id) {
        return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada com ID: " + id));
    }

    /**
     * Lista movimentações de um produto específico
     */
    public List<Movimentacao> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataDesc(produtoId);
    }

    /**
     * Atualiza uma movimentação existente e ajusta o estoque
     */
    @Transactional
    public Movimentacao atualizarMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) {
        // Busca a movimentação existente e reverte seus efeitos no estoque
        Movimentacao movimentacaoExistente = buscarPorId(id);
        reverterMovimentacao(movimentacaoExistente);

        // Busca o novo produto e valida a movimentação
        Produto produto = produtoRepository.findById(movimentacaoDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + movimentacaoDTO.getProdutoId()));

        validarMovimentacao(produto, movimentacaoDTO.getQuantidade(), movimentacaoDTO.getTipo());
        atualizarEstoque(produto, movimentacaoDTO.getQuantidade(), movimentacaoDTO.getTipo());

        // Atualiza os dados da movimentação
        movimentacaoExistente.setProduto(produto);
        movimentacaoExistente.setData(movimentacaoDTO.getData() != null ? movimentacaoDTO.getData() : LocalDate.now());
        movimentacaoExistente.setQuantidade(movimentacaoDTO.getQuantidade());
        movimentacaoExistente.setTipo(movimentacaoDTO.getTipo());

        return movimentacaoRepository.save(movimentacaoExistente);
    }

    /**
     * Exclui uma movimentação e reverte seus efeitos no estoque
     */
    @Transactional
    public void excluirMovimentacao(Long id) {
        Movimentacao movimentacao = buscarPorId(id);
        reverterMovimentacao(movimentacao);
        movimentacaoRepository.delete(movimentacao);
    }

    /**
     * Valida se uma movimentação de saída é possível
     */
    private void validarMovimentacao(Produto produto, Integer quantidade, TipoMovimentacao tipo) {
        if (tipo == TipoMovimentacao.SAIDA) {
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new IllegalArgumentException(
                        "Quantidade insuficiente em estoque. Disponível: " +
                                produto.getQuantidadeEstoque() + ", Solicitado: " + quantidade
                );
            }
        }
    }

    /**
     * Atualiza o estoque do produto baseado no tipo de movimentação
     */
    private void atualizarEstoque(Produto produto, Integer quantidade, TipoMovimentacao tipo) {
        int novoSaldo = produto.getQuantidadeEstoque();

        if (tipo == TipoMovimentacao.ENTRADA) {
            novoSaldo += quantidade;
        } else {
            novoSaldo -= quantidade;
        }

        produto.setQuantidadeEstoque(novoSaldo);
        produtoRepository.save(produto);
        verificarAlertas(produto);
    }

    /**
     * Reverte os efeitos de uma movimentação no estoque
     */
    private void reverterMovimentacao(Movimentacao movimentacao) {
        Produto produto = movimentacao.getProduto();
        int quantidade = movimentacao.getQuantidade();

        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        } else {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        }

        produtoRepository.save(produto);
    }

    /**
     * Verifica e exibe alertas para estoque mínimo e máximo
     */
    private void verificarAlertas(Produto produto) {
        int estoqueAtual = produto.getQuantidadeEstoque();
        int estoqueMinimo = produto.getQuantidadeMinima();
        int estoqueMaximo = produto.getQuantidadeMaxima();

        if (estoqueAtual < estoqueMinimo) {
            System.out.println("ALERTA: Produto " + produto.getNome() +
                    " está abaixo do estoque mínimo. " +
                    "Estoque atual: " + estoqueAtual +
                    ", Mínimo: " + estoqueMinimo);
        }

        if (estoqueAtual > estoqueMaximo) {
            System.out.println("ALERTA: Produto " + produto.getNome() +
                    " está acima do estoque máximo. " +
                    "Estoque atual: " + estoqueAtual +
                    ", Máximo: " + estoqueMaximo);
        }
    }
}