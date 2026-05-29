import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
// ==================== CLASSE LOGIN ====================
class Login {
    private String username;
    private String password;
 
    private static final String USER_VALIDO = "admin";
    private static final String SENHA_VALIDA = "1234";
 
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
 
    public boolean authenticate() {
        return username.equals(USER_VALIDO) && password.equals(SENHA_VALIDA);
    }
}
 
// ==================== CLASSE USUARIO ====================
class Usuario {
    private String nome;
    private String email;
 
    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
 
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
 
    @Override
    public String toString() {
        return "Usuário: " + nome + " | Email: " + email;
    }
}
 
// ==================== CLASSE TAREFA ====================
class Tarefa {
    private String descricao;
    private String responsavel;
    private String prioridade;
    private String prazo;
    private String status;
 
    public Tarefa(String descricao, String responsavel, String prioridade, String prazo) {
        this.descricao   = descricao;
        this.responsavel = responsavel;
        this.prioridade  = prioridade;
        this.prazo       = prazo;
        this.status      = "PENDENTE";
    }
 
    public String getDescricao()   { return descricao; }
    public String getResponsavel() { return responsavel; }
    public String getPrioridade()  { return prioridade; }
    public String getPrazo()       { return prazo; }
    public String getStatus()      { return status; }
    public void setStatus(String status) { this.status = status; }
 
    @Override
    public String toString() {
        return "Tarefa: "       + descricao
             + " | Responsável: " + responsavel
             + " | Prioridade: "  + prioridade
             + " | Prazo: "       + prazo
             + " | Status: "      + status;
    }
}
 
// ==================== CLASSE ALTERACAO DE STATUS ====================
class AlteracaoDeStatus {
    private Tarefa tarefa;
 
    public AlteracaoDeStatus(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
 
    public void alterarStatus(String novoStatus) {
        tarefa.setStatus(novoStatus);
        System.out.println("✓ Status alterado para: " + novoStatus + "\n");
    }
}
 
// ==================== CLASSE RELATORIO SIMPLES ====================
class RelatorioSimples {
    private Usuario usuario;
    private List<Tarefa> tarefas;
 
    public RelatorioSimples(Usuario usuario, List<Tarefa> tarefas) {
        this.usuario = usuario;
        this.tarefas = tarefas;
    }
 
    public void gerarRelatorio() {
        System.out.println("\n========== RELATÓRIO ==========");
        System.out.println(usuario);
        System.out.println("Tarefas cadastradas: " + tarefas.size());
        System.out.println("--------------------------------");
        for (Tarefa t : tarefas) {
            System.out.println("  - " + t);
        }
        System.out.println("================================\n");
    }
}
 
// ==================== CLASSE PRINCIPAL ====================
public class Sistema {
 
    static Scanner scanner  = new Scanner(System.in);
    static List<Usuario> usuarios = new ArrayList<>();
    static List<Tarefa>  tarefas  = new ArrayList<>();
    static boolean logado = false;
 
    public static void main(String[] args) {
 
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("===== MENU =====");
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Login");
            System.out.println("3 - Cadastrar Tarefa");
            System.out.println("4 - Alterar Status de Tarefa");
            System.out.println("5 - Gerar Relatório");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
 
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("✗ Entrada inválida.\n");
                continue;
            }
 
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> fazerLogin();
                case 3 -> cadastrarTarefa();
                case 4 -> alterarStatus();
                case 5 -> gerarRelatorio();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("✗ Opção inválida.\n");
            }
        }
    }
 
    // ── LOGIN ────────────────────────────────────────────────────────────────
    static void fazerLogin() {
        System.out.print("Nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
 
        Login login = new Login(username, password);
        if (login.authenticate()) {
            logado = true;
            System.out.println("✓ Login bem-sucedido!\n");
        } else {
            System.out.println("✗ Credenciais inválidas. (usuário: admin | senha: 1234)\n");
        }
    }
 
    // ── CADASTRO DE USUÁRIO ──────────────────────────────────────────────────
    static void cadastrarUsuario() {
 
        // Nome: apenas letras e espaços
        String nome;
        while (true) {
            System.out.print("Nome (apenas letras): ");
            nome = scanner.nextLine().trim();
            if (nome.matches("[a-zA-ZÀ-ÿ ]+")) break;
            System.out.println("  ✗ Nome inválido! Use apenas letras (sem números ou símbolos).");
        }
 
        // Email: obrigatório terminar em .com.br
        String email;
        while (true) {
            System.out.print("Email (ex: nome@empresa.com.br): ");
            email = scanner.nextLine().trim();
            if (email.matches("^[\\w.+-]+@[\\w-]+\\.com\\.br$")) break;
            System.out.println("  ✗ Email inválido! Use o formato nome@dominio.com.br");
        }
 
        usuarios.add(new Usuario(nome, email));
        System.out.println("✓ Usuário cadastrado com sucesso!\n");
    }
 
    // ── CADASTRO DE TAREFA ───────────────────────────────────────────────────
    static void cadastrarTarefa() {
        if (!logado) { System.out.println("✗ Faça login primeiro! (opção 2)\n"); return; }
 
        // Descrição
        String descricao;
        while (true) {
            System.out.print("Descrição da tarefa: ");
            descricao = scanner.nextLine().trim();
            if (!descricao.isEmpty()) break;
            System.out.println("  ✗ Descrição não pode ser vazia.");
        }
 
        // Responsável
        String responsavel;
        while (true) {
            System.out.print("Responsável (apenas letras): ");
            responsavel = scanner.nextLine().trim();
            if (responsavel.matches("[a-zA-ZÀ-ÿ ]+")) break;
            System.out.println("  ✗ Nome inválido! Use apenas letras.");
        }
 
        // Prioridade
        String prioridade;
        while (true) {
            System.out.println("Prioridade:");
            System.out.println("  1 - BAIXA");
            System.out.println("  2 - MÉDIA");
            System.out.println("  3 - ALTA");
            System.out.print("Escolha (1-3): ");
            String p = scanner.nextLine().trim();
            if      (p.equals("1")) { prioridade = "BAIXA"; break; }
            else if (p.equals("2")) { prioridade = "MÉDIA"; break; }
            else if (p.equals("3")) { prioridade = "ALTA";  break; }
            else System.out.println("  ✗ Opção inválida!");
        }
 
        // Prazo
        String prazo;
        while (true) {
            System.out.print("Prazo (dd/mm/aaaa): ");
            prazo = scanner.nextLine().trim();
            if (prazo.matches("^\\d{2}/\\d{2}/\\d{4}$")) break;
            System.out.println("  ✗ Formato inválido! Use dd/mm/aaaa.");
        }
 
        tarefas.add(new Tarefa(descricao, responsavel, prioridade, prazo));
        System.out.println("✓ Tarefa cadastrada com sucesso!\n");
    }
 
    // ── ALTERAÇÃO DE STATUS ──────────────────────────────────────────────────
    static void alterarStatus() {
        if (!logado) { System.out.println("✗ Faça login primeiro! (opção 2)\n"); return; }
 
        if (tarefas.isEmpty()) {
            System.out.println("✗ Nenhuma tarefa cadastrada.\n");
            return;
        }
 
        System.out.println("Tarefas disponíveis:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println(i + " - " + tarefas.get(i));
        }
 
        // Seleciona a tarefa
        int idx = -1;
        while (idx < 0 || idx >= tarefas.size()) {
            System.out.print("Número da tarefa: ");
            try {
                idx = Integer.parseInt(scanner.nextLine().trim());
                if (idx < 0 || idx >= tarefas.size())
                    System.out.println("  ✗ Índice fora do intervalo!");
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Entrada inválida!");
            }
        }
 
        // Seleciona o novo status
        String novoStatus;
        while (true) {
            System.out.println("Novo status:");
            System.out.println("  1 - PENDENTE");
            System.out.println("  2 - EM_ANDAMENTO");
            System.out.println("  3 - CONCLUÍDA");
            System.out.print("Escolha (1-3): ");
            String s = scanner.nextLine().trim();
            if      (s.equals("1")) { novoStatus = "PENDENTE";     break; }
            else if (s.equals("2")) { novoStatus = "EM_ANDAMENTO"; break; }
            else if (s.equals("3")) { novoStatus = "CONCLUÍDA";    break; }
            else System.out.println("  ✗ Opção inválida! Escolha 1, 2 ou 3.");
        }
 
        AlteracaoDeStatus alteracao = new AlteracaoDeStatus(tarefas.get(idx));
        alteracao.alterarStatus(novoStatus);
    }
 
    // ── RELATÓRIO ────────────────────────────────────────────────────────────
    static void gerarRelatorio() {
        if (!logado) { System.out.println("✗ Faça login primeiro! (opção 2)\n"); return; }
 
        if (usuarios.isEmpty()) {
            System.out.println("✗ Cadastre ao menos um usuário.\n");
            return;
        }
        if (tarefas.isEmpty()) {
            System.out.println("✗ Cadastre ao menos uma tarefa.\n");
            return;
        }
 
        RelatorioSimples relatorio = new RelatorioSimples(usuarios.get(0), tarefas);
        relatorio.gerarRelatorio();
    }
}