import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class SistemaInterface {

    // ── CORES ────────────────────────────────────────────────────────────────
    static Color fundo    = new Color(24, 24, 28);
    static Color painel   = new Color(34, 34, 40);
    static Color destaque = new Color(0, 180, 120);
    static Color perigo   = new Color(220, 60, 60);
    static Color texto    = Color.WHITE;

    // ── DADOS ────────────────────────────────────────────────────────────────
    static List<String[]> usuarios = new ArrayList<>();
    static List<String[]> tarefas  = new ArrayList<>();
    static boolean logado = false;

    // ── MAIN ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaInterface::telaLogin);
    }

    // =========================================================================
    // LOGIN
    // =========================================================================
    public static void telaLogin() {

        JFrame frame = criarFrame("Sistema de Tarefas - Login", 420, 340);

        JPanel panel = criarPainel(new GridLayout(6, 1, 10, 10), 30, 50);

        JLabel titulo = criarTitulo("SISTEMA DE TAREFAS");

        JTextField user = new JTextField();
        JPasswordField pw = new JPasswordField();

        estilizarCampo(user);
        estilizarCampo(pw);

        placeholder(user, "Usuário");
        placeholder(pw, "Senha");

        JLabel dica = new JLabel("usuário: admin | senha: 1234", JLabel.CENTER);

        dica.setForeground(new Color(120, 120, 130));
        dica.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JButton entrar = estilizarBotao("Entrar", destaque);

        // ENTER faz login
        frame.getRootPane().setDefaultButton(entrar);

        entrar.addActionListener(e -> {

            String u = user.getText().equals("Usuário")
                    ? ""
                    : user.getText();

            String s = new String(pw.getPassword()).equals("Senha")
                    ? ""
                    : new String(pw.getPassword());

            if (u.equals("admin") && s.equals("1234")) {

                logado = true;

                frame.dispose();

                telaMenu();

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Credenciais inválidas!\nUse: admin / 1234",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(titulo);
        panel.add(user);
        panel.add(pw);
        panel.add(dica);
        panel.add(new JLabel());
        panel.add(entrar);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // MENU
    // =========================================================================
    public static void telaMenu() {

        JFrame frame = criarFrame("Sistema de Tarefas - Menu", 480, 460);

        JPanel panel = criarPainel(new GridLayout(7, 1, 12, 12), 30, 50);

        JLabel titulo = criarTitulo("MENU PRINCIPAL");

        JButton btnUsuario   = estilizarBotao("1 - Cadastrar Usuário", destaque);
        JButton btnLogin     = estilizarBotao("2 - Login (já conectado ✓)", new Color(60, 60, 70));
        JButton btnTarefa    = estilizarBotao("3 - Cadastrar Tarefa", destaque);
        JButton btnStatus    = estilizarBotao("4 - Alterar Status", destaque);
        JButton btnRelatorio = estilizarBotao("5 - Gerar Relatório", destaque);
        JButton btnSair      = estilizarBotao("0 - Sair", perigo);

        btnLogin.setEnabled(false);

        btnUsuario.addActionListener(e -> telaCadastroUsuario(frame));
        btnTarefa.addActionListener(e -> telaCadastroTarefa(frame));
        btnStatus.addActionListener(e -> telaAlterarStatus(frame));
        btnRelatorio.addActionListener(e -> telaRelatorio(frame));

        btnSair.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Deseja realmente sair?",
                    "Sair",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        panel.add(titulo);
        panel.add(btnUsuario);
        panel.add(btnLogin);
        panel.add(btnTarefa);
        panel.add(btnStatus);
        panel.add(btnRelatorio);
        panel.add(btnSair);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // CADASTRAR USUÁRIO
    // =========================================================================
    public static void telaCadastroUsuario(JFrame parent) {

        JFrame frame = criarFrame("Cadastrar Usuário", 420, 320);

        JPanel panel = criarPainel(new GridLayout(5, 1, 10, 10), 25, 45);

        JLabel titulo = criarTitulo("CADASTRAR USUÁRIO");

        JTextField nome = new JTextField();
        JTextField email = new JTextField();

        estilizarCampo(nome);
        estilizarCampo(email);

        placeholder(nome, "Nome (apenas letras)");
        placeholder(email, "Email (ex: nome@empresa.com.br)");

        JButton salvar = estilizarBotao("Salvar", destaque);

        frame.getRootPane().setDefaultButton(salvar);

        salvar.addActionListener(e -> {

            String n = nome.getText().equals("Nome (apenas letras)")
                    ? ""
                    : nome.getText().trim();

            String em = email.getText().equals("Email (ex: nome@empresa.com.br)")
                    ? ""
                    : email.getText().trim();

            if (!n.matches("[a-zA-ZÀ-ÿ ]+") || n.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Nome inválido!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (!em.matches("^[\\w.+-]+@[\\w-]+\\.com\\.br$")) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Email inválido!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            usuarios.add(new String[]{n, em});

            JOptionPane.showMessageDialog(
                    frame,
                    "✓ Usuário cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
        });

        panel.add(titulo);
        panel.add(nome);
        panel.add(email);
        panel.add(new JLabel());
        panel.add(salvar);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // CADASTRAR TAREFA
    // =========================================================================
    public static void telaCadastroTarefa(JFrame parent) {

        JFrame frame = criarFrame("Cadastrar Tarefa", 440, 460);

        JPanel panel = criarPainel(new GridLayout(7, 1, 10, 10), 25, 40);

        JLabel titulo = criarTitulo("CADASTRAR TAREFA");

        JTextField descricao = new JTextField();
        JTextField responsavel = new JTextField();

        estilizarCampo(descricao);
        estilizarCampo(responsavel);

        placeholder(descricao, "Descrição da tarefa");
        placeholder(responsavel, "Responsável");

        // CAMPO DATA COM MÁSCARA
        JFormattedTextField prazo = criarCampoData();

        String[] opcoesPrioridade = {
                "Prioridade: BAIXA",
                "Prioridade: MÉDIA",
                "Prioridade: ALTA"
        };

        JComboBox<String> prioridade = new JComboBox<>(opcoesPrioridade);

        estilizarCombo(prioridade);

        JButton salvar = estilizarBotao("Salvar Tarefa", destaque);

        // ENTER salva tarefa
        frame.getRootPane().setDefaultButton(salvar);

        salvar.addActionListener(e -> {

            String desc = descricao.getText().equals("Descrição da tarefa")
                    ? ""
                    : descricao.getText().trim();

            String resp = responsavel.getText().equals("Responsável")
                    ? ""
                    : responsavel.getText().trim();

            String pr = prazo.getText().trim();

            String prior = ((String) prioridade.getSelectedItem())
                    .replace("Prioridade: ", "");

            if (desc.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Descrição vazia!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (!resp.matches("[a-zA-ZÀ-ÿ ]+") || resp.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Responsável inválido!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (pr.contains("_")) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Preencha a data corretamente!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            tarefas.add(new String[]{
                    desc,
                    resp,
                    prior,
                    pr,
                    "PENDENTE"
            });

            JOptionPane.showMessageDialog(
                    frame,
                    "✓ Tarefa cadastrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
        });

        panel.add(titulo);
        panel.add(descricao);
        panel.add(responsavel);
        panel.add(prioridade);
        panel.add(prazo);
        panel.add(new JLabel());
        panel.add(salvar);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // ALTERAR STATUS
    // =========================================================================
    public static void telaAlterarStatus(JFrame parent) {

        if (tarefas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Nenhuma tarefa cadastrada!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        JFrame frame = criarFrame("Alterar Status", 480, 320);

        JPanel panel = criarPainel(new GridLayout(5, 1, 10, 10), 25, 40);

        JLabel titulo = criarTitulo("ALTERAR STATUS");

        String[] nomesTarefas = new String[tarefas.size()];

        for (int i = 0; i < tarefas.size(); i++) {

            nomesTarefas[i] =
                    i + " - " +
                    tarefas.get(i)[0] +
                    " [" +
                    tarefas.get(i)[4] +
                    "]";
        }

        JComboBox<String> listaTarefas = new JComboBox<>(nomesTarefas);

        estilizarCombo(listaTarefas);

        String[] opcoesStatus = {
                "1 - PENDENTE",
                "2 - EM_ANDAMENTO",
                "3 - CONCLUÍDA"
        };

        JComboBox<String> listaStatus = new JComboBox<>(opcoesStatus);

        estilizarCombo(listaStatus);

        JButton alterar = estilizarBotao("Alterar Status", destaque);

        alterar.addActionListener(e -> {

            int idx = listaTarefas.getSelectedIndex();

            String statusEscolhido =
                    (String) listaStatus.getSelectedItem();

            String novoStatus =
                    statusEscolhido.substring(4);

            tarefas.get(idx)[4] = novoStatus;

            JOptionPane.showMessageDialog(
                    frame,
                    "✓ Status alterado!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
        });

        panel.add(titulo);
        panel.add(listaTarefas);
        panel.add(listaStatus);
        panel.add(new JLabel());
        panel.add(alterar);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // RELATÓRIO
    // =========================================================================
    public static void telaRelatorio(JFrame parent) {

        JFrame frame = criarFrame("Relatório", 560, 480);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBackground(painel);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = criarTitulo("RELATÓRIO");

        JTextArea area = new JTextArea();

        area.setBackground(new Color(28, 28, 33));
        area.setForeground(Color.WHITE);

        area.setFont(new Font("Consolas", Font.PLAIN, 13));

        area.setEditable(false);

        StringBuilder sb = new StringBuilder();

        sb.append("========== USUÁRIOS ==========\n");

        for (String[] u : usuarios) {

            sb.append("• ")
                    .append(u[0])
                    .append(" | ")
                    .append(u[1])
                    .append("\n");
        }

        sb.append("\n========== TAREFAS ==========\n");

        for (String[] t : tarefas) {

            sb.append("Descrição: ").append(t[0]).append("\n");
            sb.append("Responsável: ").append(t[1]).append("\n");
            sb.append("Prioridade: ").append(t[2]).append("\n");
            sb.append("Prazo: ").append(t[3]).append("\n");
            sb.append("Status: ").append(t[4]).append("\n");
            sb.append("--------------------------------------\n");
        }

        area.setText(sb.toString());

        JButton voltar = estilizarBotao("Voltar", destaque);

        voltar.addActionListener(e -> frame.dispose());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(voltar, BorderLayout.SOUTH);

        frame.add(panel);

        frame.setVisible(true);
    }

    // =========================================================================
    // HELPERS
    // =========================================================================
    static JFrame criarFrame(String titulo, int w, int h) {

        JFrame f = new JFrame(titulo);

        f.setSize(w, h);

        f.setLocationRelativeTo(null);

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f.getContentPane().setBackground(fundo);

        return f;
    }

    static JPanel criarPainel(LayoutManager layout, int vertical, int horizontal) {

        JPanel p = new JPanel(layout);

        p.setBackground(painel);

        p.setBorder(
                BorderFactory.createEmptyBorder(
                        vertical,
                        horizontal,
                        vertical,
                        horizontal));

        return p;
    }

    static JLabel criarTitulo(String texto) {

        JLabel lbl = new JLabel(texto, JLabel.CENTER);

        lbl.setForeground(Color.WHITE);

        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));

        return lbl;
    }

    static void estilizarCampo(JTextField campo) {

        campo.setBackground(new Color(50, 50, 55));

        campo.setForeground(Color.WHITE);

        campo.setCaretColor(Color.WHITE);

        campo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    static void estilizarCombo(JComboBox<?> combo) {

        combo.setBackground(new Color(50, 50, 55));

        combo.setForeground(Color.WHITE);

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    static JButton estilizarBotao(String textoBtn, Color cor) {

        JButton btn = new JButton(textoBtn);

        btn.setBackground(cor);

        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        return btn;
    }

    // CAMPO DATA
    static JFormattedTextField criarCampoData() {

        try {

            MaskFormatter mascaraData =
                    new MaskFormatter("##/##/####");

            mascaraData.setPlaceholderCharacter('_');

            JFormattedTextField campo =
                    new JFormattedTextField(mascaraData);

            estilizarCampo(campo);

            return campo;

        } catch (Exception e) {

            throw new RuntimeException("Erro ao criar campo de data");
        }
    }

    static void placeholder(JTextField campo, String ph) {

        campo.setText(ph);

        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (campo.getText().equals(ph)) {

                    campo.setText("");

                    campo.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (campo.getText().isEmpty()) {

                    campo.setText(ph);

                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}
