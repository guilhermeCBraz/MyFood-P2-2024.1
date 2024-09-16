package Exceptions;

public class DoisPedidosAbertosException extends Exception{
    public DoisPedidosAbertosException(){super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");}
}
