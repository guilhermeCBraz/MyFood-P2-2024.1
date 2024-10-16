package Models;

import java.io.Serializable;
import java.util.List;

public class Entrega {
    private int id;
    private String cliente;
    private String empresa;
    private int pedido;
    private int entregador;
    private String destino;
    private List<Produto> produtos;

    public Entrega(){}
    public Entrega(int id, String cliente, String empresa, int pedido, int entregador, String destino, List<Produto> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.pedido = pedido;
        this.entregador = entregador;
        this.destino = destino;
        this.produtos = produtos;
    }

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

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getEntregador() {
        return entregador;
    }

    public void setEntregador(int entregador) {
        this.entregador = entregador;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }


}
