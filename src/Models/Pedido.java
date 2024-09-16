package Models;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private String cliente;
    private String empresa;
    private String estado;
    private List<Produto> produtos;
    private float valor;

    public Pedido(int id, String cliente, String empresa) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void fecharPedido() {
        this.estado = "preparando";
    }

    public  void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(String nome) {
        produtos.removeIf(produto -> produto.getNome().equalsIgnoreCase(nome));
    }

    public String getProdutos() {
        StringBuilder sb = new StringBuilder("{[");
        boolean first = true;

        for (Produto produto : produtos) {
                if (!first){ sb.append(", ");}
                sb.append(produto.getNome());
                first = false;
        }
        sb.append("]}");

        return sb.toString();
    }

    public double calcularValor() {
        double total = produtos.stream().mapToDouble(Produto::getValor).sum();
        //System.out.println("Valor total: " + total);
        return total;
    }
}
