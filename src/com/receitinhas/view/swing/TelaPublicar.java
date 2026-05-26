package com.receitinhas.view.swing;

import com.receitinhas.controller.ReceitaController;
import com.receitinhas.repository.ReceitaRepositoryImpl;
import com.receitinhas.service.ReceitaService;

import javax.swing.*;
import java.awt.*;

/**
 * Tela para publicação de novas receitas.
 *
 * Corresponde à tela "Publique" original, reescrita sem .form e sem lógica de negócio.
 */
public class TelaPublicar extends JFrame {

    private final ReceitaController receitaController;

    private JTextField campoNome;
    private JTextArea campoIngredientes;
    private JTextArea campoDescricao;
    private JTextField campoRendimento;
    private JTextField campoTempoPreparo;
    private JComboBox<String> comboTipo;

    public TelaPublicar() {
        ReceitaService receitaService = new ReceitaService(new ReceitaRepositoryImpl());
        this.receitaController = new ReceitaController(receitaService);

        configurarJanela();
        construirComponentes();
    }

    private void configurarJanela() {
        setTitle("Receitinhas — Publicar Receita");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(254, 235, 210));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;

        campoNome = new JTextField();
        campoRendimento = new JTextField();
        campoTempoPreparo = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"Doce", "Salgado", "Com restrição"});

        campoIngredientes = new JTextArea(3, 20);
        campoDescricao    = new JTextArea(3, 20);
        campoIngredientes.setLineWrap(true);
        campoDescricao.setLineWrap(true);

        adicionarLinha(painel, gbc, 0, "Nome:", campoNome);
        adicionarLinha(painel, gbc, 1, "Ingredientes:", new JScrollPane(campoIngredientes));
        adicionarLinha(painel, gbc, 2, "Descrição:", new JScrollPane(campoDescricao));
        adicionarLinha(painel, gbc, 3, "Rendimento:", campoRendimento);
        adicionarLinha(painel, gbc, 4, "Tempo de preparo:", campoTempoPreparo);
        adicionarLinha(painel, gbc, 5, "Tipo:", comboTipo);

        JButton btnSalvar = new JButton("Salvar Receita");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSalvar.addActionListener(e -> salvar());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltar());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.setBackground(new Color(254, 235, 210));
        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnSalvar);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 4, 4, 4);
        painel.add(painelBotoes, gbc);

        add(painel, BorderLayout.CENTER);
    }

    private void adicionarLinha(JPanel painel, GridBagConstraints gbc,
                                 int linha, String label, Component campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0;
        painel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painel.add(campo, gbc);
    }

    private void salvar() {
        try {
            receitaController.cadastrar(
                    campoNome.getText(),
                    campoIngredientes.getText(),
                    campoDescricao.getText(),
                    campoRendimento.getText(),
                    campoTempoPreparo.getText(),
                    (String) comboTipo.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, "Receita cadastrada com sucesso!");
            voltar();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltar() {
        new TelaGeral().setVisible(true);
        dispose();
    }
}
