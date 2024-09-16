package Exceptions;

public class PedidoFechadoException extends Exception{
    public PedidoFechadoException(){super("Nao e possivel remover produtos de um pedido fechado");}
}
