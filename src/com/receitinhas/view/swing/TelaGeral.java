package com.receitinhas.view.swing;

import com.receitinhas.controller.ReceitaController;
import com.receitinhas.model.Receita;
import com.receitinhas.repository.ReceitaRepositoryImpl;
import com.receitinhas.service.ReceitaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class TelaGeral extends JFrame {

    private final ReceitaController receitaController;

    private JTable tabelaReceitas;
    private DefaultTableModel modeloTabela;
    private JTextField campoFiltro;

    private static final String[] COLUNAS = {
        "Nome", "Ingredientes", "Descrição", "Rendimento", "Tempo de preparo", "Tipo"
    };

    public TelaGeral() {
        ReceitaService receitaService = new ReceitaService(new ReceitaRepositoryImpl());
        this.receitaController = new ReceitaController(receitaService);

        configurarJanela();
        construirComponentes();
        carregarReceitas("");
    }

    private void configurarJanela() {
        setTitle("Receitinhas — Receitas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(new Color(254, 235, 210));
    }

    private void construirComponentes() {
        // --- barra de navegação ---
        JPanel barraNav = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraNav.setBackground(new Color(254, 235, 210));

        JLabel lblTitulo = new JLabel("RECEITAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        String[] categorias = {"Todas", "Doce", "Salgado", "Restrições"};
        for (String cat : categorias) {
            JButton btn = new JButton(cat);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.addActionListener(e -> {
                String filtro = cat.equals("Todas") ? "" : cat;
                campoFiltro.setText(filtro);
                carregarReceitas(filtro);
            });
            barraNav.add(btn);
        }

        JButton btnPublicar = new JButton("Publicar Receita");
        btnPublicar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnPublicar.addActionListener(e -> abrirPublicacao());
        barraNav.add(Box.createHorizontalStrut(20));
        barraNav.add(btnPublicar);

        JButton btnVoltar = new JButton("Sair");
        btnVoltar.addActionListener(e -> voltar());
        barraNav.add(btnVoltar);

        // --- filtro de busca ---
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.setBackground(new Color(254, 235, 210));
        JLabel lblFiltro = new JLabel("Pesquisar por tipo:");
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 16));
        campoFiltro = new JTextField(18);
        campoFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> carregarReceitas(campoFiltro.getText()));
        painelFiltro.add(lblFiltro);
        painelFiltro.add(campoFiltro);
        painelFiltro.add(btnBuscar);

        // --- tabela ---
        modeloTabela = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelaReceitas = new JTable(modeloTabela);
        tabelaReceitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaReceitas.setRowHeight(24);
        tabelaReceitas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scroll = new JScrollPane(tabelaReceitas);

        // --- montagem ---
        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.setBackground(new Color(254, 235, 210));
        painelNorte.add(new JLabel("  " + lblTitulo.getText(), JLabel.LEFT), BorderLayout.NORTH);
        painelNorte.add(barraNav, BorderLayout.CENTER);
        painelNorte.add(painelFiltro, BorderLayout.SOUTH);

        add(painelNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // --- ações ---

    private void carregarReceitas(String filtro) {
        modeloTabela.setRowCount(0);
        try {
            List<Receita> receitas = receitaController.buscarPorTipo(filtro);
            for (Receita r : receitas) {
                modeloTabela.addRow(new Object[]{
                    r.getNome(), r.getIngredientes(), r.getDescricao(),
                    r.getRendimento(), r.getTempoDePreparo(), r.getTipoReceita()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar receitas: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPublicacao() {
        new TelaPublicar().setVisible(true);
        dispose();
    }

    private void voltar() {
        new TelaLogin().setVisible(true);
        dispose();
    }
}
