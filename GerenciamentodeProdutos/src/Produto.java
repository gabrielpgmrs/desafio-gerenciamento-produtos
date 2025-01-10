import java.util.Set;
import java.util.TreeSet;

public class Produto {
    private static int contador = 0;
    private static Set<Integer> idsEmUso = new TreeSet<>();
    private String id;
    private String nome;
    private String categoria;
    private int quantidade;
    private double preco;

    public Produto(String nome, String categoria, int quantidade, double preco) {
        this.id = gerarProximoId();
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    private String gerarProximoId() {
        // Procura o primeiro ID disponível
        int novoId = 1;
        while (idsEmUso.contains(novoId)) {
            novoId++;
        }
        idsEmUso.add(novoId);
        contador = Math.max(contador, novoId);
        return String.format("%03d", novoId);
    }

    public static void liberarId(String id) {
        try {
            idsEmUso.remove(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            // Ignora se o ID não for um número válido
        }
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public static void atualizarContador(int ultimoId) {
        contador = ultimoId;
    }

    public static void registrarIdEmUso(String id) {
        try {
            idsEmUso.add(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            // Ignora se o ID não for um número válido
        }
    }
}