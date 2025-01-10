import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventario inventario = new Inventario();
        
        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Inventario ===");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("5. Buscar Produto");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opcao: ");
            
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1": inventario.adicionarProduto(); break;
                case "2": inventario.listarProdutos(); break;
                case "3": inventario.atualizarProduto(); break;
                case "4": inventario.excluirProduto(); break;
                case "5": inventario.buscarProduto(); break;
                case "6": System.out.println("Encerrando..."); return;
                default: System.out.println("Opcao invalida!");
            }
        }
    }
}