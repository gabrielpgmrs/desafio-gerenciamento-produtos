public class Produto {
    private static int contador = 0;
    private String id;
    private String nome;
    private String categoria;
    private int quantidade;
    private double preco;

    public Produto(String nome, String categoria, int quantidade, double preco) {
        contador++;
        this.id = String.format("%03d", contador);
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
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
}