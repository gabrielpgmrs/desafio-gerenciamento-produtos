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
        
        // Variáveis temporárias para armazenar as novas informações
        String novoNome = produto.getNome();
        String novaCategoria = produto.getCategoria();
        int novaQuantidade = produto.getQuantidade();
        double novoPreco = produto.getPreco();
        boolean dadosValidos = true;
        
        // Validação do nome
        while (true) {
            System.out.print("Novo nome (" + produto.getNome() + "): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) {
                break;
            } else if (nome.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                novoNome = nome;
                break;
            } else {
                System.out.println("Erro: O nome deve conter apenas letras e espaços.");
                dadosValidos = false;
                break;
            }
        }

        if (dadosValidos) {
            // Validação da categoria
            while (true) {
                System.out.print("Nova categoria (" + produto.getCategoria() + "): ");
                String categoria = scanner.nextLine();
                if (categoria.isEmpty()) {
                    break;
                } else if (categoria.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                    novaCategoria = categoria;
                    break;
                } else {
                    System.out.println("Erro: A categoria deve conter apenas letras e espaços.");
                    dadosValidos = false;
                    break;
                }
            }
        }

        if (dadosValidos) {
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
                        novaQuantidade = quantidade;
                        break;
                    } else {
                        System.out.println("Erro: A quantidade não pode ser negativa.");
                        dadosValidos = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite um número inteiro válido.");
                    dadosValidos = false;
                    break;
                }
            }
        }

        if (dadosValidos) {
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
                        novoPreco = preco;
                        break;
                    } else {
                        System.out.println("Erro: O preço não pode ser negativo.");
                        dadosValidos = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite um valor numérico válido.");
                    dadosValidos = false;
                    break;
                }
            }
        }

        // Só atualiza o produto se todos os dados forem válidos
        if (dadosValidos) {
            // Mostra um resumo das alterações
            System.out.println("\nResumo das alterações:");
            if (!novoNome.equals(produto.getNome())) {
                System.out.println("Nome: " + produto.getNome() + " -> " + novoNome);
            }
            if (!novaCategoria.equals(produto.getCategoria())) {
                System.out.println("Categoria: " + produto.getCategoria() + " -> " + novaCategoria);
            }
            if (novaQuantidade != produto.getQuantidade()) {
                System.out.println("Quantidade: " + produto.getQuantidade() + " -> " + novaQuantidade);
            }
            if (novoPreco != produto.getPreco()) {
                System.out.println("Preço: " + produto.getPreco() + " -> " + novoPreco);
            }

            System.out.print("\nDeseja salvar as alterações? (S/N): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("S")) {
                produto.setNome(novoNome);
                produto.setCategoria(novaCategoria);
                produto.setQuantidade(novaQuantidade);
                produto.setPreco(novoPreco);
                salvarDados();
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Atualização cancelada pelo usuário.");
            }
        } else {
            System.out.println("Atualização cancelada devido a dados inválidos.");
        }
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
            Produto.liberarId(produto.getId()); // Libera o ID para reutilização
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
            
            // Ao carregar os produtos, registra os IDs em uso
            for (Produto p : produtos) {
                Produto.registrarIdEmUso(p.getId());
            }
            
            // Encontra o maior ID para atualizar o contador
            int maiorId = produtos.stream()
                .mapToInt(p -> Integer.parseInt(p.getId()))
                .max()
                .orElse(0);
                
            // Atualiza o contador para continuar a sequência
            Produto.atualizarContador(maiorId);
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}