import java.util.HashMap;
import java.util.Scanner;

public class FiapBankAtm {
    HashMap<String, String> cadastro = new HashMap<>();
    HashMap<String, String> usuario = new HashMap<>();




    public static void main(String[] args){
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
                        opcao = cadastro(leitor);
                        break;
                    case 0:
                        System.exit(0);
                }

            }
        }catch(Exception e){
            System.out.println("Fechando o programa ...");
            System.exit(0);
        }
    }

    public static int login(){

    }

    public static int cadastro(Scanner leitor){
        try{

                System.out.println(Mensagens.CADASTRO_NOME.getMensagem());

        }
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
                Digite um nome válido
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
