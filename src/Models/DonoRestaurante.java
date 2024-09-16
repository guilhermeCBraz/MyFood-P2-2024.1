package Models;

import Exceptions.*;

public class DonoRestaurante extends Usuario{
    private String cpf;

    public DonoRestaurante() {
        super();
    }

    @Override
    public String getTipoUsuario() {
        return "DonoRestaurante";
    }

    public DonoRestaurante(int id, String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException {
        super(id, nome, email, senha, endereco);
        if (cpf == null || cpf.length() != 14) {
            throw new CpfInvalidoException();
        } else {
            this.cpf = cpf;
        }
    }

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean isDonoRestaurante(){
        return true;
    }
}
