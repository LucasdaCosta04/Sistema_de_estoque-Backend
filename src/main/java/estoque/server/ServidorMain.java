package estoque.server;

import estoque.dao.ConnectionFactory;

public class ServidorMain {
    public static void main(String[] args) {
        try {
            var conn = ConnectionFactory.getConnection();
            System.out.println("✅ Conexão funcionando!");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
