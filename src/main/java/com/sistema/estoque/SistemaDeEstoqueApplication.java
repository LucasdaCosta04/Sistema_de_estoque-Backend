package com.sistema.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsável pela inicialização do sistema de estoque.
 * Contém o método main que executa a aplicação Spring Boot.
 */
@SpringBootApplication
public class SistemaDeEstoqueApplication {

    /**
     * Método de entrada da aplicação. Inicializa o contexto Spring Boot
     * e configura todos os componentes e serviços definidos no projeto.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        SpringApplication.run(SistemaDeEstoqueApplication.class, args);
    }

}
