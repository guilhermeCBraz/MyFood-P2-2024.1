package Exceptions;

public class NaoExisteNadaEntregueException extends Exception{
    public NaoExisteNadaEntregueException(){super("Nao existe nada para ser entregue com esse id");}
}
