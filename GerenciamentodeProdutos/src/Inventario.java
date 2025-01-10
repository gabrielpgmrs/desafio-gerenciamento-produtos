import java.io.*;
import java.util.*;

public class Inventario {
    private List<Produto> produtos;
    private static final String ARQUIVO_DADOS = "inventario.json";
    private Scanner scanner;

    public Inventario() {
        this.produtos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        carregarDados();
    }

    public void adicionarProduto() {
        // Validação do nome
        String nome;
        while (true) {
            System.out.print("Nome do produto: ");
            nome = scanner.nextLine();
            if (nome.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                break;
            } else {
                System.out.println("Erro: O nome deve conter apenas letras e espaços.");
            }
        }

        // Validação da categoria
        String categoria;
        while (true) {
            System.out.print("Categoria: ");
            categoria = scanner.nextLine();
            if (categoria.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                break;
            } else {
                System.out.println("Erro: A categoria deve conter apenas letras e espaços.");
            }
        }

        // Validação da quantidade
        int quantidade;
        while (true) {
            try {
                System.out.print("Quantidade: ");
                quantidade = Integer.parseInt(scanner.nextLine());
                if (quantidade >= 0) {
                    break;
                } else {
                    System.out.println("Erro: A quantidade não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número inteiro válido.");
            }
        }

        // Validação do preço
        double preco;
        while (true) {
            try {
                System.out.print("Preço: ");
                preco = Double.parseDouble(scanner.nextLine());
                if (preco >= 0) {
                    break;
                } else {
                    System.out.println("Erro: O preço não pode ser negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numérico válido.");
            }
        }

        Produto produto = new Produto(nome, categoria, quantidade, preco);
        produtos.add(produto);
        salvarDados();
        System.out.println("Produto adicionado com sucesso! ID: " + produto.getId());
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.printf("%-6s %-20s %-15s %-10s %-10s%n", 
            "ID", "Nome", "Categoria", "Quantidade", "Preço");
        System.out.println("-".repeat(65));

        for (Produto p : produtos) {
            System.out.printf("%-6s %-20s %-15s %-10d R$%.2f%n",
                p.getId(), p.getNome(), p.getCategoria(), p.getQuantidade(), p.getPreco());
        }
    }

    public void atualizarProduto() {
        System.out.print("Digite o ID do produto: ");
        String id = scanner.nextLine();
        
        Produto produto = buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.println("Deixe em branco para manter o valor atual.");
        
        // Validação do nome
        while (true) {
            System.out.print("Novo nome (" + produto.getNome() + "): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) {
                break;
            } else if (nome.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                produto.setNome(nome);
                break;
            } else {
                System.out.println("Erro: O nome deve conter apenas letras e espaços.");
            }
        }

        // Validação da categoria
        while (true) {
            System.out.print("Nova categoria (" + produto.getCategoria() + "): ");
            String categoria = scanner.nextLine();
            if (categoria.isEmpty()) {
                break;
            } else if (categoria.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                produto.setCategoria(categoria);
                break;
            } else {
                System.out.println("Erro: A categoria deve conter apenas letras e espaços.");
            }
        }

        // Validação da quantidade
        while (true) {
            System.out.print("Nova quantidade (" + produto.getQuantidade() + "): ");
            String quantidadeStr = scanner.nextLine();
            if (quantidadeStr.isEmpty()) {
                break;
            }
            try {
                int quantidade = Integer.parseInt(quantidadeStr);
                if (quantidade >= 0) {
                    produto.setQuantidade(quantidade);
                    break;
                } else {
                    System.out.println("Erro: A quantidade não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número inteiro válido.");
            }
        }

        // Validação do preço
        while (true) {
            System.out.print("Novo preço (" + produto.getPreco() + "): ");
            String precoStr = scanner.nextLine();
            if (precoStr.isEmpty()) {
                break;
            }
            try {
                double preco = Double.parseDouble(precoStr);
                if (preco >= 0) {
                    produto.setPreco(preco);
                    break;
                } else {
                    System.out.println("Erro: O preço não pode ser negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numérico válido.");
            }
        }

        salvarDados();
        System.out.println("Produto atualizado com sucesso!");
    }

    public void excluirProduto() {
        System.out.print("Digite o ID do produto: ");
        String id = scanner.nextLine();
        
        Produto produto = buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Confirma a exclusão do produto " + produto.getNome() + "? (S/N): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            produtos.remove(produto);
            salvarDados();
            System.out.println("Produto excluído com sucesso!");
        }
    }

    public void buscarProduto() {
        System.out.print("Buscar por (1-ID, 2-Nome): ");
        String opcao = scanner.nextLine();
        
        if (opcao.equals("1")) {
            System.out.print("Digite o ID: ");
            String id = scanner.nextLine();
            Produto produto = buscarPorId(id);
            if (produto != null) exibirProduto(produto);
            else System.out.println("Produto não encontrado.");
        } else if (opcao.equals("2")) {
            System.out.print("Digite o nome: ");
            String nome = scanner.nextLine().toLowerCase();
            boolean encontrou = false;
            
            for (Produto p : produtos) {
                if (p.getNome().toLowerCase().contains(nome)) {
                    exibirProduto(p);
                    encontrou = true;
                }
            }
            
            if (!encontrou) System.out.println("Nenhum produto encontrado.");
        }
    }

    private Produto buscarPorId(String id) {
        return produtos.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private void exibirProduto(Produto p) {
        System.out.println("\nDetalhes do Produto:");
        System.out.println("ID: " + p.getId());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Categoria: " + p.getCategoria());
        System.out.println("Quantidade: " + p.getQuantidade());
        System.out.println("Preço: R$" + String.format("%.2f", p.getPreco()));
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_DADOS)) {
            writer.write("[\n");
            for (int i = 0; i < produtos.size(); i++) {
                Produto p = produtos.get(i);
                writer.write(String.format(
                    "  {\"id\":\"%s\",\"nome\":\"%s\",\"categoria\":\"%s\",\"quantidade\":%d,\"preco\":%.2f}",
                    p.getId(), p.getNome(), p.getCategoria(), p.getQuantidade(), p.getPreco()
                ));
                if (i < produtos.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            StringBuilder json = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                json.append(linha);
            }
            
            // Ao carregar os produtos, encontre o maior ID
            int maiorId = 0;
            for (Produto p : produtos) {
                int idAtual = Integer.parseInt(p.getId());
                if (idAtual > maiorId) {
                    maiorId = idAtual;
                }
            }
            // Atualiza o contador para continuar a sequência
            Produto.atualizarContador(maiorId);
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}