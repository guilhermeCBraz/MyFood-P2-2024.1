package Exceptions;

public class NaoPossivelLiberarPedidoAbertoException extends Exception{
    public NaoPossivelLiberarPedidoAbertoException(){super("Nao e possivel liberar um produto que nao esta sendo preparado");}
}
