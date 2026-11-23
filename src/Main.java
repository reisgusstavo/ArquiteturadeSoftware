import decorator.Bebida;
import decorator.Cafe;
import decorator.Chantilly;
import decorator.Chocolate;
import decorator.Leite;
import model.Pedido;
import observer.Barista;
import observer.Caixa;
import singleton.GerenciadorPedidos;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GerenciadorPedidos gerenciador = GerenciadorPedidos.getInstance();
        gerenciador.registrar(new Barista());   // Observer: quem será avisado
        gerenciador.registrar(new Caixa());

        Scanner in = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n=== CAFETERIA ===");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Ver pedidos do dia");
            System.out.println("3 - Demonstração dos 3 padrões");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerNumero(in);
            switch (opcao) {
                case 1 -> novoPedido(in, gerenciador);
                case 2 -> listarPedidos(gerenciador);
                case 3 -> demonstracao();
                case 0 -> System.out.println("Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void novoPedido(Scanner in, GerenciadorPedidos gerenciador) {
        Bebida bebida = new Cafe();
        int escolha;
        do {
            System.out.println("\nSeu pedido: " + bebida.getDescricao() + " = " + bebida.getPrecoFormatado());
            System.out.println("Adicionar: 1 - Leite (+R$ 2,00) | 2 - Chantilly (+R$ 3,00) | 3 - Chocolate (+R$ 4,00) | 0 - Finalizar");
            System.out.print("Escolha: ");
            escolha = lerNumero(in);
            switch (escolha) {
                case 1 -> bebida = new Leite(bebida);          // Decorator: embrulha a bebida
                case 2 -> bebida = new Chantilly(bebida);
                case 3 -> bebida = new Chocolate(bebida);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);

        System.out.println();
        gerenciador.criarPedido(bebida);                       // Observer: avisa barista e caixa
    }

    private static void listarPedidos(GerenciadorPedidos gerenciador) {
        if (gerenciador.getPedidos().isEmpty()) {
            System.out.println("Nenhum pedido ainda.");
            return;
        }
        for (Pedido p : gerenciador.getPedidos()) {
            System.out.println(p);
        }
    }

    /** Mostra os 3 padrões funcionando, sem precisar digitar nada. */
    private static void demonstracao() {
        System.out.println("\n--- 1) SINGLETON ---");
        boolean mesma = GerenciadorPedidos.getInstance() == GerenciadorPedidos.getInstance();
        System.out.println("getInstance() duas vezes devolve o mesmo objeto? " + mesma);

        System.out.println("\n--- 2) DECORATOR ---");
        Bebida bebida = new Cafe();
        System.out.println(bebida.getDescricao() + " = " + bebida.getPrecoFormatado());
        bebida = new Leite(bebida);
        System.out.println(bebida.getDescricao() + " = " + bebida.getPrecoFormatado());
        bebida = new Chantilly(bebida);
        System.out.println(bebida.getDescricao() + " = " + bebida.getPrecoFormatado());

        System.out.println("\n--- 3) OBSERVER ---");
        System.out.println("Criando o pedido: o barista e o caixa são avisados sozinhos.");
        GerenciadorPedidos.getInstance().criarPedido(bebida);
    }

    private static int lerNumero(Scanner in) {
        if (!in.hasNextLine()) return 0;                       // fim da entrada: encerra
        try {
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
