import java.util.ArrayList;
import java.util.Scanner;

public class FiapBankAtm {
    final int TENTATIVAS_MAXIMAS = 3;
    ArrayList<Usuario> usuarios = new ArrayList<>();
    Usuario sessao_atual;

    public class Usuario {
        public String nome;
        public String senha;
        private double saldo;

        Usuario(String nome, String senha) {
            this.nome = nome;
            this.senha = senha;
            this.saldo = 0.0;
            usuarios.add(this);
        }

        boolean fazerDeposito(double dinheiro) throws Exception {
            if (dinheiro <= 0) throw new Exception("Valor inválido para depósito.");
            this.saldo += dinheiro;
            return true;
        }

        boolean fazerSaque(double dinheiro) throws Exception {
            if (dinheiro <= 0) throw new Exception("Valor inválido para saque.");
            if (dinheiro > this.saldo) throw new Exception("Saldo insuficiente.");
            this.saldo -= dinheiro;
            return true;
        }

        double getSaldo() {
            return saldo;
        }
    }

    public void main(String[] args) {
        try {
            Scanner leitor = new Scanner(System.in);
            System.out.println(Mensagens.LOGO.getMensagem());
            System.out.println(Mensagens.OP_INICIO.getMensagem());
            System.out.print("-> ");
            int opcao = Integer.parseInt(leitor.nextLine().trim());
            while (true) {
                switch (opcao) {
                    case 1:  opcao = login(leitor);          break;
                    case 2:  opcao = cadastro(leitor);       break;
                    case 3:  opcao = sessao(leitor);         break;
                    case 11: opcao = consulta_saldo();       break;
                    case 12: opcao = fazer_deposito(leitor); break;
                    case 13: opcao = fazer_saque(leitor);    break;
                    case 0:  System.exit(0);
                    default:
                        System.out.println(Mensagens.OP_INICIO.getMensagem());
                        System.out.print("-> ");
                        opcao = Integer.parseInt(leitor.nextLine().trim());
                }
            }
        } catch (Exception e) {
            System.out.println("Fechando o programa ...");
            System.exit(0);
        }
    }

    public int consulta_saldo() {
        System.out.println("─".repeat(50));
        System.out.printf("  Saldo de %s%n", sessao_atual.nome);
        System.out.printf("  R$ %.2f%n", sessao_atual.getSaldo());
        System.out.println("─".repeat(50));
        return 3;
    }

    public int fazer_deposito(Scanner leitor) {
        System.out.println(Mensagens.DEPOSITO.getMensagem());
        System.out.print("-> ");
        try {
            double valor = Double.parseDouble(leitor.nextLine().trim());
            sessao_atual.fazerDeposito(valor);
            System.out.printf("  ✔ R$ %.2f depositados na conta de %s%n", valor, sessao_atual.nome);
        } catch (Exception e) {
            System.out.println("  ✘ " + e.getMessage());
        }
        return 3;
    }

    public int fazer_saque(Scanner leitor) {
        System.out.println(Mensagens.SAQUE.getMensagem());
        System.out.print("-> ");
        try {
            double valor = Double.parseDouble(leitor.nextLine().trim());
            sessao_atual.fazerSaque(valor);
            System.out.printf("  ✔ R$ %.2f sacados da conta de %s%n", valor, sessao_atual.nome);
        } catch (Exception e) {
            System.out.println("  ✘ " + e.getMessage());
        }
        return 3;
    }

    public int sessao(Scanner leitor) {
        System.out.println("─".repeat(50));
        System.out.printf("  Olá, %s! Saldo: R$ %.2f%n", sessao_atual.nome, sessao_atual.getSaldo());
        System.out.println("─".repeat(50));
        System.out.println(Mensagens.SESSAO.getMensagem());
        System.out.print("-> ");
        int opcao = Integer.parseInt(leitor.nextLine().trim());
        if (opcao == 4) return 404;
        return opcao + 10;
    }

    public int login(Scanner leitor) {
        try {
            String nome, senha;
            System.out.println(Mensagens.LOGIN_NOME.getMensagem());
            System.out.print("-> ");
            nome = leitor.nextLine().trim();

            Usuario usuario = busca_usuario(nome);

            boolean autenticado = false;
            for (int tentativas = 0; tentativas < TENTATIVAS_MAXIMAS; tentativas++) {
                System.out.println(Mensagens.LOGIN_SENHA.getMensagem() + usuario.nome);
                System.out.print("-> ");
                senha = leitor.nextLine();
                if (usuario.senha.equals(senha)) {
                    autenticado = true;
                    break;
                }
                System.out.println("  ✘ Senha incorreta. Tentativas restantes: " + (TENTATIVAS_MAXIMAS - tentativas - 1));
            }

            if (!autenticado) {
                System.out.println("  ✘ Número máximo de tentativas atingido.");
                return 0;
            }

            sessao_atual = usuario;
            System.out.printf("  ✔ Bem-vindo, %s!%n", sessao_atual.nome);

        } catch (Exception e) {
            System.out.println("  ✘ " + e.getMessage());
            return 404;
        }
        return 3;
    }

    public Usuario busca_usuario(String nome) throws Exception {
        for (var u : usuarios) {
            if (u.nome.equals(nome)) return u;
        }
        throw new Exception("Usuário " + nome + " não encontrado.");
    }

    public int cadastro(Scanner leitor) {
        try {
            String nome, senha;
            System.out.println(Mensagens.CADASTRO_NOME.getMensagem());
            nome = leitor.nextLine().trim();

            if (!teste_nome(nome)) return 404;

            System.out.println(Mensagens.CADASTRO_SENHA.getMensagem());
            senha = leitor.nextLine();

            if (!teste_senha(senha)) return 404;

            Usuario user = new Usuario(nome, senha);
            System.out.printf("  ✔ %s cadastrado com sucesso!%n", nome);

        } catch (Exception e) {
            return 404;
        }
        return 404;
    }

    public static boolean teste_nome(String nome) {
        if (nome == null || nome.isBlank()) {
            System.out.println("  ✘ Nome inválido.");
            return false;
        } else if (!nome.matches("[a-zA-ZÀ-ú ]+")) {
            System.out.println("  ✘ Nome deve conter só letras.");
            return false;
        }
        return true;
    }

    public static boolean teste_senha(String senha) {
        if (!senha.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$")) {
            System.out.println("  ✘ Senha inválida!");
            return false;
        }
        return true;
    }

    public enum Mensagens {
        LOGO("""
                          _____                _____                    _____         \s
                         /\\    \\              /\\    \\                  /\\    \\        \s
                        /::\\    \\            /::\\    \\                /::\\____\\       \s
                       /::::\\    \\           \\:::\\    \\              /::::|   |       \s
                      /::::::\\    \\           \\:::\\    \\            /:::::|   |       \s
                     /:::/\\:::\\    \\           \\:::\\    \\          /::::::|   |       \s
                    /:::/__\\:::\\    \\           \\:::\\    \\        /:::/|::|   |       \s
                   /::::\\   \\:::\\    \\          /::::\\    \\      /:::/ |::|   |       \s
                  /::::::\\   \\:::\\    \\        /::::::\\    \\    /:::/  |::|___|______ \s
                 /:::/\\:::\\   \\:::\\    \\      /:::/\\:::\\    \\  /:::/   |::::::::\\    \\\s
                /:::/  \\:::\\   \\:::\\____\\    /:::/  \\:::\\____\\/:::/    |:::::::::\\____\\
                \\::/    \\:::\\  /:::/    /   /:::/    \\::/    /\\::/    / ~~~~~/:::/    /
                 \\/____/ \\:::\\/:::/    /   /:::/    / \\/____/  \\/____/      /:::/    /\s
                          \\::::::/    /   /:::/    /                       /:::/    / \s
                           \\::::/    /   /:::/    /                       /:::/    /  \s
                           /:::/    /    \\::/    /                       /:::/    /   \s
                          /:::/    /      \\/____/                       /:::/    /    \s
                         /:::/    /                                    /:::/    /     \s
                        /:::/    /                                    /:::/    /      \s
                        \\::/    /                                     \\::/    /       \s
                         \\/____/                                       \\/____/        \s
                """),
        OP_INICIO("""
                [ 1 ] Login
                [ 2 ] Cadastro
                [ 0 ] Sair
                """),
        CADASTRO_NOME("""
                CADASTRO ──> Digite seu nome:
                """),
        CADASTRO_SENHA("""
                Digite sua senha:
                  - Mínimo 8 caracteres
                  - Ao menos um número
                  - Ao menos uma letra maiúscula
                  - Ao menos um caractere especial: !@#$%^&*()-_+=?>
                """),
        LOGIN_NOME("""
                LOGIN ──> Digite seu nome:
                """),
        LOGIN_SENHA("""
                Senha para:\s"""),
        SESSAO("""
                [ 1 ] Consultar Saldo
                [ 2 ] Fazer Depósito
                [ 3 ] Fazer Saque
                [ 4 ] Logout
                """),
        DEPOSITO("""
                Depósito ──> Digite o valor:
                """),
        SAQUE("""
                Saque ──> Digite o valor:
                """);

        private final String Mensagem;

        Mensagens(String s) {
            this.Mensagem = s;
        }

        public String getMensagem() {
            return Mensagem;
        }
    }
}