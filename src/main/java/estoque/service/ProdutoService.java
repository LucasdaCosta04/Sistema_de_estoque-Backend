package estoque.service;

import estoque.dao.ProdutoDAO;
import estoque.model.Produto;

import java.util.List;

public class ProdutoService {

    private ProdutoDAO dao = new ProdutoDAO();

    // ✅ 1. ADICIONAR PRODUTO
    public String adicionarProduto(Produto p) {

        if (!validarProduto(p)) {
            return "❌ Dados inválidos! Produto não foi inserido.";
        }

        dao.inserir(p);
        return "✅ Produto cadastrado com sucesso!";
    }

    // ✅ 2. LISTAR PRODUTOS
    public List<Produto> listarProdutos() {
        return dao.listar();
    }

    // ✅ 3. EDITAR PRODUTO
    public String editarProduto(Produto p) {

        if (dao.buscarPorId(p.getId()) == null) {
            return "❌ Produto não encontrado!";
        }

        if (!validarProduto(p)) {
            return "❌ Dados inválidos! Não foi possível atualizar.";
        }

        dao.atualizar(p);
        return "✅ Produto atualizado com sucesso!";
    }

    // ✅ 4. EXCLUIR PRODUTO
    public String excluirProduto(int id) {

        if (dao.buscarPorId(id) == null) {
            return "❌ Produto não encontrado!";
        }

        dao.excluir(id);
        return "✅ Produto excluído com sucesso!";
    }

    // ✅ 5. REAJUSTAR PREÇOS (sua tarefa específica)
    public String reajustarPrecos(double percentual) {

        if (percentual == 0) {
            return "❌ Percentual inválido (não pode ser 0).";
        }

        List<Produto> produtos = dao.listar();

        if (produtos.isEmpty()) {
            return "❌ Não há produtos cadastrados para reajustar.";
        }

        for (Produto p : produtos) {
            double novoPreco = p.getPrecoUnitario() + (p.getPrecoUnitario() * (percentual / 100));

            if (novoPreco < 0) {
                return "❌ Reajuste inválido: preço resultante não pode ser negativo.";
            }

            p.setPrecoUnitario(novoPreco);
            dao.atualizar(p);
        }

        return "✅ Preços reajustados em " + percentual + "% com sucesso!";
    }

    // ✅ MÉTODO INTERNO DE VALIDAÇÃO COMPLETA DO PRODUTO
    private boolean validarProduto(Produto p) {

        if (p.getNome() == null || p.getNome().isBlank()) {
            System.out.println("❌ O nome não pode ser vazio.");
            return false;
        }

        if (p.getPrecoUnitario() < 0) {
            System.out.println("❌ O preço não pode ser negativo.");
            return false;
        }

        if (p.getQuantidadeMinima() < 0 || p.getQuantidadeMaxima() < 0) {
            System.out.println("❌ Quantidades não podem ser negativas.");
            return false;
        }

        if (p.getQuantidadeMinima() > p.getQuantidadeMaxima()) {
            System.out.println("❌ Quantidade mínima não pode ser maior que a máxima.");
            return false;
        }

        if (p.getQuantidadeEstoque() < 0) {
            System.out.println("❌ Estoque não pode ser negativo.");
            return false;
        }

        return true;
    }
}
