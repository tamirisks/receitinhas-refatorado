package com.receitinhas.view.swing;

import com.receitinhas.controller.LoginController;
import com.receitinhas.model.Usuario;
import com.receitinhas.repository.UsuarioRepositoryImpl;
import com.receitinhas.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;


public class TelaLogin extends JFrame {

    private final LoginController loginController;

    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public TelaLogin() {
        // Injeção de dependências manual (sem framework)
        UsuarioService usuarioService = new UsuarioService(new UsuarioRepositoryImpl());
        this.loginController = new LoginController(usuarioService);

        configurarJanela();
        construirComponentes();
    }

    // --- configuração ---

    private void configurarJanela() {
        setTitle("Receitinhas — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 236, 189));
    }

    private void construirComponentes() {
        // Painel lateral com o nome do app
        JPanel painelMarca = new JPanel(new GridBagLayout());
        painelMarca.setBackground(new Color(255, 236, 189));
        JLabel lblApp = new JLabel("Receitinhas");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painelMarca.add(lblApp);

        // Painel de login
        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new BoxLayout(painelLogin, BoxLayout.Y_AXIS));
        painelLogin.setBackground(new Color(204, 204, 204));
        painelLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblBemVindo = new JLabel("BEM VINDO DE VOLTA!");
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblFaca = new JLabel("Faça seu login:");
        lblFaca.setFont(new Font("Segoe UI", Font.BOLD, 16));

        campoLogin = new JTextField(20);
        campoLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirmar.addActionListener(e -> realizarLogin());

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLimpar.addActionListener(e -> limparCampos());

        JButton btnCadastro = new JButton("Cadastrar-se");
        btnCadastro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCadastro.addActionListener(e -> abrirCadastro());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(new Color(204, 204, 204));
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnConfirmar);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelRodape.setBackground(new Color(204, 204, 204));
        painelRodape.add(new JLabel("Não possui login?"));
        painelRodape.add(btnCadastro);

        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(lblBemVindo);
        painelLogin.add(Box.createVerticalStrut(20));
        painelLogin.add(lblFaca);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(new JLabel("Login:"));
        painelLogin.add(campoLogin);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(new JLabel("Senha:"));
        painelLogin.add(campoSenha);
        painelLogin.add(Box.createVerticalStrut(20));
        painelLogin.add(painelBotoes);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(painelRodape);

        add(painelLogin, BorderLayout.WEST);
        add(painelMarca, BorderLayout.CENTER);
    }

    // --- ações ---

    private void realizarLogin() {
        String login = campoLogin.getText().trim();
        String senha = new String(campoSenha.getPassword());

        try {
            Optional<Usuario> usuario = loginController.login(login, senha);
            if (usuario.isPresent()) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                new TelaGeral().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!",
                        "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        campoLogin.setText("");
        campoSenha.setText("");
        campoLogin.requestFocus();
    }

    private void abrirCadastro() {
        new TelaCadastroUsuario().setVisible(true);
        dispose();
    }

    // --- main ---

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
