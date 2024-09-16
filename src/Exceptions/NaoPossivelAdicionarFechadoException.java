package Exceptions;

public class NaoPossivelAdicionarFechadoException extends Exception{
    public NaoPossivelAdicionarFechadoException(){super("Nao e possivel adcionar produtos a um pedido fechado");}
}
