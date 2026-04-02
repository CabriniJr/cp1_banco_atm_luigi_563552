import java.util.ArrayList;
import java.util.Scanner;

public class FiapBankAtm {
    final int TENTATIVAS_MAXIMAS = 3;
    ArrayList<Usuario> usuarios = new ArrayList<>();

    Usuario sessao_atual;

    public class Usuario{
        public String nome;
        public String senha;
        double saldo;

        Usuario(String nome, String senha){
            this.nome = nome;
            this.senha = senha;
            this.saldo = 0.0;
            usuarios.add(this);
        }

    }




    public void main(String[] args){
        try{
            Scanner leitor = new Scanner(System.in);
            System.out.println(Mensagens.LOGO.getMensagem());
            System.out.println(Mensagens.OP_INICIO.getMensagem());
            int opcao = leitor.nextInt();
            while(true){
                //Lógica do código
                switch (opcao){
                    case 1:
                        opcao = login();
                        break;
                    case 2:
                        opcao = cadastro();
                        break;
                    case 3:
                        opcao = sessao();
                        break;
                    case 0:
                        System.exit(0);

                    default:
                        System.out.println(Mensagens.OP_INICIO.getMensagem());
                        opcao = leitor.nextInt();

                }

            }
        }catch(Exception e){
            System.out.println("Fechando o programa ...");
            System.exit(0);
        }
    }

    public int sessao(){
        System.out.println("Olá "+ sessao_atual.nome + " tudo bem?");
        return 0;
    }



    public int login(){
        try{
            Scanner leitor = new Scanner(System.in);
            String nome, senha;

            System.out.println(Mensagens.LOGIN_NOME.getMensagem());
            nome = leitor.nextLine().trim();
            //Busca do usuário
            Usuario usuario = busca_usuario(nome);


            boolean autenticado = false;
            for (int tentativas = 0; tentativas < TENTATIVAS_MAXIMAS; tentativas++) {
                System.out.println(Mensagens.LOGIN_SENHA.getMensagem() + usuario.nome);
                senha = leitor.nextLine();
                if (usuario.senha.equals(senha)) {
                    autenticado = true;
                    break;
                }
                System.out.println("Senha incorreta, tentativas restantes: " + (TENTATIVAS_MAXIMAS - tentativas - 1));
            }

            if (!autenticado) {
                System.out.println("Número máximo de tentativas atingido.");
                return 0;
            }
            //Usuario e senha válidos, inicia a sessão
            sessao_atual = usuario;


        }catch (Exception e){
            System.out.println(e.getMessage());
            return 404;
        }
        return 3;
    }
    public Usuario busca_usuario(String nome) throws Exception {
        for (var u : usuarios) {
            if (u.nome.equals(nome)) {
                return u;
            }
        }
        throw new Exception("Usuário " + nome + " não existe!");
    }


    public int cadastro(){
        try{
            Scanner leitor = new Scanner(System.in);
            String nome, senha;

            System.out.println(Mensagens.CADASTRO_NOME.getMensagem());
            nome = leitor.nextLine();
            nome = nome.trim();

            if(!teste_nome(nome)) return 404;
            //Testa o nome

            System.out.println(Mensagens.CADASTRO_SENHA.getMensagem());
            senha = leitor.nextLine();

            if(!teste_senha(senha)) return 404;
            //Testa a senha
            //Código só chega aqui caso tudo tenha sido validado, logo insiro na lista de usuarios e na sessão atual
            System.out.println("Sucesso " + nome + " cadastrado!");
            Usuario user = new Usuario(nome, senha);
            usuarios.add(user);

        }catch (Exception e){
            return 404;
        }
        return 404;
    }

    public static boolean teste_nome(String nome){
        // Testes executados no nome no cadastro
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome inválido.");
            return false;
        } else if (!nome.matches("[a-zA-ZÀ-ú ]+")) {
            System.out.println("Nome deve conter só letras.");
            return false;
        }
        return true;
    }

    public static boolean teste_senha(String senha){
        // Teste executados na senha
        if(!senha.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$")){
            System.out.println("Senha inválida!");
            return false;
        }
        return true;
    }



    public enum Mensagens{
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
                CADASTRO --> Digite um nome válido
                """),
        CADASTRO_SENHA("""
                Digite sua senha - requisitos
                - No mínimo 8 caracteres.
                - Ao menos um número.
                - Ao menos uma letra maiúscula.
                - Ao menos um caracter especial da lista: !@#$%^&*()-_+=?><
                ->
                """),
        LOGIN_NOME("""
                LOGIN --> Digite seu nome
                """),
        LOGIN_SENHA("""
                Digite a sua senha
                """);


        private final String Mensagem;
        Mensagens(String s) {
            this.Mensagem = s;
        }

        public String getMensagem(){
            return Mensagem;
        }

    }
}
