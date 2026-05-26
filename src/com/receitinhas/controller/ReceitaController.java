package com.receitinhas.controller;

import com.receitinhas.model.Receita;
import com.receitinhas.service.ReceitaService;

import java.util.List;

public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    /**
     * Cadastra uma nova receita.
     */
    public void cadastrar(String nome, String ingredientes, String descricao,
                          String rendimento, String tempoDePreparo, String tipoReceita) {
        Receita receita = new Receita(nome, ingredientes, descricao,
                rendimento, tempoDePreparo, tipoReceita);
        receitaService.cadastrar(receita);
    }

    /**
     * Busca receitas por tipo. Retorna todas se o tipo for vazio.
     */
    public List<Receita> buscarPorTipo(String tipo) {
        return receitaService.buscarPorTipo(tipo);
    }

    /** Retorna todas as receitas cadastradas. */
    public List<Receita> listarTodas() {
        return receitaService.listarTodas();
    }
}
