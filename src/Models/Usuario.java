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
        this.id = id;

        if(nome == null || nome.isEmpty()) {throw new NomeInvalidoException();}
        this.nome = nome;

//        if(email == null || email.isEmpty()) {throw new EmailInvalidoException();}
        this.email = email;

//        if(senha == null || senha.isEmpty()) {throw new SenhaInvalidoException();}
        this.senha = senha;

//        if(endereco == null || endereco.isEmpty()) {throw new EnderecoInvalidoException();}
        this.endereco = endereco;

    }

    public int getId() {return id;}

    public String getEmail() {return email;}

    public String getNome() {return nome;}

    public String getSenha() {return senha;}


    public static boolean buscaUsuarioPorEmail(HashMap<Integer, Usuario> mapUsuarios, String email) throws EmailJaExisteException {
        for (Usuario usuario : mapUsuarios.values()) {
            if (usuario.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }
}
