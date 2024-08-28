package Models;

import Exceptions.*;

import java.util.ArrayList;

public class Sistema {
    ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    int contadorID = 1;

    public void zerarSistema() {

    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UsuarioNaoCadastradoException, NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {
        Usuario.validaUsuario(nome, email, senha, endereco);
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        listaUsuarios.add(cliente);
        contadorID++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UsuarioNaoCadastradoException, NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException {
        DonoRestaurante.validaUsuario(nome, email, senha, endereco, cpf);
        DonoRestaurante donoRestaurante = new DonoRestaurante(contadorID, nome, email, senha, endereco, cpf);
        listaUsuarios.add(donoRestaurante);
        contadorID++;
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
        if (false) {
            return "";
        } else {
            throw new UsuarioNaoCadastradoException();
        }
    }
}
