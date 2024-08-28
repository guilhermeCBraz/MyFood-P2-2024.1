package Models;

import Exceptions.*;

public class DonoRestaurante extends Usuario{
    String endereco;
    String cpf;
    public DonoRestaurante(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha);
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public static void validaUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, CpfInvalidoException {
        validaUsuario(nome, email, senha, endereco);
        if (cpf == null || cpf.length() < 14) throw new CpfInvalidoException();
    }
}
