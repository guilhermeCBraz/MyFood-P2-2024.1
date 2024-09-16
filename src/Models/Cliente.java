package Models;

import Exceptions.EmailInvalidoException;
import Exceptions.EnderecoInvalidoException;
import Exceptions.NomeInvalidoException;
import Exceptions.SenhaInvalidoException;

public class Cliente extends Usuario{
    public Cliente() {
        super();
    }


    @Override
    public String getTipoUsuario() {
        return "Cliente";
    }

    public Cliente(int id, String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException {
        super(id, nome, email, senha, endereco);
    }



    @Override
    public boolean isDonoRestaurante() {
        return false;
    }
}
