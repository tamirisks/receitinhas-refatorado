package com.receitinhas.service;

import com.receitinhas.model.Receita;
import com.receitinhas.repository.ReceitaRepository;

import java.util.List;

public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    /**
     * Valida e salva uma nova receita.
     */
    public void cadastrar(Receita receita) {
        validar(receita);
        receitaRepository.salvar(receita);
    }

    /**
     * Retorna receitas filtradas por tipo.
     * Se o tipo for null ou vazio, retorna todas.
     */
    public List<Receita> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return receitaRepository.listarTodas();
        }
        return receitaRepository.buscarPorTipo(tipo.trim());
    }

    /** Retorna todas as receitas cadastradas. */
    public List<Receita> listarTodas() {
        return receitaRepository.listarTodas();
    }

    // --- validações privadas ---

    private void validar(Receita receita) {
        if (receita == null) {
            throw new IllegalArgumentException("Receita não pode ser nula.");
        }
        if (isVazio(receita.getNome())) {
            throw new IllegalArgumentException("O nome da receita é obrigatório.");
        }
        if (isVazio(receita.getIngredientes())) {
            throw new IllegalArgumentException("Os ingredientes são obrigatórios.");
        }
        if (isVazio(receita.getTipoReceita())) {
            throw new IllegalArgumentException("O tipo da receita é obrigatório.");
        }
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
