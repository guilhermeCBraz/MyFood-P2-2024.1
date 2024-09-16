package Models;

public class Produto {
    private int id;
    private int empresaId;
    private String nome;
    private float valor;
    private String categoria;

    public Produto(){}

    public Produto(int id, int empresaId, String nome, float valor, String categoria) {
        this.id = id;
        this.empresaId = empresaId;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
