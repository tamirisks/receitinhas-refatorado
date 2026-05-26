package com.receitinhas.view.swing;

import com.receitinhas.controller.LoginController;
import com.receitinhas.repository.UsuarioRepositoryImpl;
import com.receitinhas.service.UsuarioService;

import javax.swing.*;
import java.awt.*;


public class TelaCadastroUsuario extends JFrame {

    private final LoginController loginController;

    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;

    public TelaCadastroUsuario() {
        UsuarioService usuarioService = new UsuarioService(new UsuarioRepositoryImpl());
        this.loginController = new LoginController(usuarioService);

        configurarJanela();
        construirComponentes();
    }

    private void configurarJanela() {
        setTitle("Receitinhas — Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(204, 204, 204));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Cadastro de Usuário");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoNome  = criarCampo();
        campoEmail = criarCampo();
        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.addActionListener(e -> cadastrar());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.addActionListener(e -> voltar());

        painel.add(lblTitulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("E-mail:"));
        painel.add(campoEmail);
        painel.add(Box.createVerticalStrut(10));
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);
        painel.add(Box.createVerticalStrut(20));
        painel.add(btnCadastrar);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnVoltar);

        add(painel, BorderLayout.CENTER);
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return campo;
    }

    private void cadastrar() {
        String nome  = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        try {
            loginController.cadastrarUsuario(nome, email, senha);
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            voltar();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void voltar() {
        new TelaLogin().setVisible(true);
        dispose();
    }
}
