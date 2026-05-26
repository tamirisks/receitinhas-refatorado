package com.receitinhas.model;

/**
 * Representa uma receita cadastrada no sistema.
 * Campos UI (largura/altura) foram removidos — não pertencem ao modelo de domínio.
 */
public class Receita {

    private String nome;
    private String ingredientes;
    private String descricao;
    private String rendimento;
    private String tempoDePreparo;
    private String tipoReceita;

    public Receita() {}

    public Receita(String nome, String ingredientes, String descricao,
                   String rendimento, String tempoDePreparo, String tipoReceita) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.descricao = descricao;
        this.rendimento = rendimento;
        this.tempoDePreparo = tempoDePreparo;
        this.tipoReceita = tipoReceita;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIngredientes() { return ingredientes; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRendimento() { return rendimento; }
    public void setRendimento(String rendimento) { this.rendimento = rendimento; }

    public String getTempoDePreparo() { return tempoDePreparo; }
    public void setTempoDePreparo(String tempoDePreparo) { this.tempoDePreparo = tempoDePreparo; }

    public String getTipoReceita() { return tipoReceita; }
    public void setTipoReceita(String tipoReceita) { this.tipoReceita = tipoReceita; }

    @Override
    public String toString() {
        return "Receita{nome='" + nome + "', tipo='" + tipoReceita + "'}";
    }
}
