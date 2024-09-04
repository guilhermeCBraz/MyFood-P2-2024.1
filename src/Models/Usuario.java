package Models;

import Exceptions.*;

import java.util.HashMap;

public abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario(int id, String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {

        verificaNome(nome);
        verificaSenha(senha);
        verificaEndereco(endereco);
        verificaEmail(email);

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.id = id;
        this.endereco = endereco;

    }

    public int getId() {return id;}

    public String getEmail() {return email;}

    public String getNome() {return nome;}

    public String getSenha() {return senha;}

    public String getEndereco(){return endereco;}


    public static boolean buscaUsuarioPorEmail(HashMap<Integer, Usuario> mapUsuarios, String email) throws EmailJaExisteException {
        for (Usuario usuario : mapUsuarios.values()) {
            if (usuario.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public  void verificaNome(String nome) throws NomeInvalidoException{
        if(nome == null || nome.isEmpty()) {throw new NomeInvalidoException();}
    }

    public  void verificaSenha(String senha) throws SenhaInvalidoException{
        if(senha == null || senha.isEmpty()) {throw new SenhaInvalidoException();}
    }

    public void verificaEndereco(String endereco) throws EnderecoInvalidoException{
        if(endereco == null || endereco.isEmpty()){throw new EnderecoInvalidoException();}
    }

    public void verificaEmail(String email) throws EmailInvalidoException{
        if(email == null || email.isEmpty()){throw new EmailInvalidoException();}
        if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new EmailInvalidoException();
        }

    }

    public abstract boolean isDonoRestaurante();
}
