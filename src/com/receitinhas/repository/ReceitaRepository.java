package com.receitinhas.repository;

import com.receitinhas.model.Receita;

import java.util.List;


public interface ReceitaRepository {

    /**
     * Persiste uma nova receita no banco de dados.
     *
     * @param receita receita a ser salva
     */
    void salvar(Receita receita);

    /**
     * Busca receitas cujo tipo contenha o termo informado.
     *
     * @param tipo trecho do tipo de receita (ex.: "doce", "salgado")
     * @return lista de receitas correspondentes (nunca null)
     */
    List<Receita> buscarPorTipo(String tipo);

    /**
     * Retorna todas as receitas cadastradas.
     *
     * @return lista completa de receitas
     */
    List<Receita> listarTodas();
}
