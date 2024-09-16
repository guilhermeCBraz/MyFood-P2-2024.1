package Exceptions;

public class EmpresaNomeJaExisteException extends Exception{
    public EmpresaNomeJaExisteException(){super("Empresa com esse nome ja existe");}
}
