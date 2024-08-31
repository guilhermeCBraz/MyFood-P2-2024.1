package Models;

import Exceptions.*;

import java.util.HashMap;

public class Sistema {
    HashMap<Integer, Usuario> mapUsuarios = new HashMap<Integer, Usuario>();

    int contadorID = 1;

    public void zerarSistema() {

    }

    public static boolean buscarUsuarioPorId(HashMap<Integer, Usuario> mapaUsuarios, int id) throws UsuarioNaoCadastradoException {
        if (!mapaUsuarios.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        } else {
            return true;
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailJaExisteException {
        if (Usuario.buscaUsuarioPorEmail(mapUsuarios, email)) {
            throw new EmailJaExisteException();
        }
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        mapUsuarios.put(contadorID ,cliente);
        contadorID++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException, EmailJaExisteException {
        Usuario.buscaUsuarioPorEmail(mapUsuarios, email);
        DonoRestaurante donoRestaurante = new DonoRestaurante(contadorID, nome, email, senha, endereco, cpf);
        mapUsuarios.put(contadorID ,donoRestaurante);
        contadorID++;
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
        if (false) {
            return "";
        } else {
            throw new UsuarioNaoCadastradoException();
        }
    }

    public void encerrarSistema() {

    }
}
