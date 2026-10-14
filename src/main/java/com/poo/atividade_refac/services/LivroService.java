package com.poo.atividade_refac.services;

import com.poo.atividade_refac.entities.Livro;
import com.poo.atividade_refac.repositories.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    // Injeção de dependência via construtor (Boa prática - DIP)
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro cadastrar(Livro livro) {
        // Aplica o desconto usando o Enum antes de salvar no banco
        if (livro.getCategoria() != null && livro.getPreco() != null) {
            double precoComDesconto = livro.getCategoria().calcularDesconto(livro.getPreco());
            livro.setPreco(precoComDesconto);
        }
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id).map(livro -> {
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setCategoria(livroAtualizado.getCategoria());

            // Recalcula o desconto com base na categoria
            if (livroAtualizado.getCategoria() != null && livroAtualizado.getPreco() != null) {
                double precoComDesconto = livroAtualizado.getCategoria().calcularDesconto(livroAtualizado.getPreco());
                livro.setPreco(precoComDesconto);
            } else {
                livro.setPreco(livroAtualizado.getPreco());
            }

            return livroRepository.save(livro);
        }).orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
    }

    public void remover(Long id) {
        livroRepository.deleteById(id);
    }
}