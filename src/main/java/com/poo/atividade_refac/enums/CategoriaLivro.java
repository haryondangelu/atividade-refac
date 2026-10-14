package com.poo.atividade_refac.enums;

public enum CategoriaLivro {

    TECNOLOGIA {
        @Override
        public double calcularDesconto(double preco) {
            return preco * 0.90; // 10% de desconto
        }
    },
    FICCAO {
        @Override
        public double calcularDesconto(double preco) {
            return preco * 0.80; // 20% de desconto
        }
    },
    DIDATICO {
        @Override
        public double calcularDesconto(double preco) {
            return preco; // Sem desconto
        }
    };

    // Método abstrato que força cada categoria a ter sua própria regra de desconto
    public abstract double calcularDesconto(double preco);
}