package Models;

import Exceptions.*;

import java.util.HashMap;

public class Sistema {
    HashMap<Integer, Usuario> mapUsuarios = new HashMap<Integer, Usuario>();

    int contadorID = 1;

    public void zerarSistema() {

    }

    public static Usuario buscarUsuarioPorId(HashMap<Integer, Usuario> mapaUsuarios, int id) throws UsuarioNaoCadastradoException {
        if(mapaUsuarios.containsKey(id)){
            return mapaUsuarios.get(id);
        }
        else{
            throw new UsuarioNaoCadastradoException();
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailJaExisteException {
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        if (Usuario.buscaUsuarioPorEmail(mapUsuarios, email)) {
            throw new EmailJaExisteException();
        }


        mapUsuarios.put(contadorID ,cliente);
        contadorID++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException, EmailJaExisteException {
        DonoRestaurante donoRestaurante = new DonoRestaurante(contadorID, nome, email, senha, endereco, cpf);
        //if (Usuario.buscaUsuarioPorEmail(mapUsuarios, email)) {
          //  throw new EmailJaExisteException();
        //}

        mapUsuarios.put(contadorID ,donoRestaurante);
        contadorID++;
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException
    {
        if (!mapUsuarios.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = mapUsuarios.get(id);
        String res = "";

        if (atributo.equals("nome"))
        {
            res =  usuario.getNome();
        } else if (atributo.equals("endereco"))
        {
            res = usuario.getEndereco();
        } else if (atributo.equals("email"))
        {
            res = usuario.getEmail();
        } else if (atributo.equals("cpf"))
        {
            DonoRestaurante d1 = (DonoRestaurante) mapUsuarios.get(id);
            res = d1.getCpf();
        }

        return res;


    }

    public int login(String email, String senha) throws LoginSenhaInvalidosException{

        //Verificando se está no mapUsuários
        for(Usuario usuario: mapUsuarios.values()){
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                return usuario.getId();
            }

        }
        throw new LoginSenhaInvalidosException();







    }




    public void encerrarSistema() {

    }
}
