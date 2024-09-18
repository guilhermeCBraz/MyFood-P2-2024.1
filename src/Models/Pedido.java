package Models;

import java.util.ArrayList;
import java.util.List;


public class Pedido {

    private int id;
    private String cliente;
    private String empresa;
    private String estado;
    private List<Produto> produtos;
    private float valor = 0L;

    public Pedido() {
        // Construtor vazio necessário para serialização
    }

    public Pedido(int id, String cliente, String empresa) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
    }

    // Getters e setters para todos os atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void fecharPedido() {
        this.estado = "preparando";
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(String nome) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equalsIgnoreCase(nome)) {
                produtos.remove(i);
                break;
            }
        }
    }

    public double calcularValor() {
        double total = produtos.stream().mapToDouble(Produto::getValor).sum();
        return total;
    }
}

