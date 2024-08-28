package Models;

import Exceptions.EmailInvalidoException;
import Exceptions.EnderecoInvalidoException;
import Exceptions.NomeInvalidoException;
import Exceptions.SenhaInvalidoException;

public class Usuario {
    int id;
    String nome;
    String email;
    String senha;

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {return id;}

    public String getEmail() {return email;}

    public String getNome() {return nome;}

    public String getSenha() {return senha;}

    public static void validaUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {
        if (nome == null || nome.isEmpty()) throw new NomeInvalidoException();
        if (email == null || email.isEmpty()) throw new EmailInvalidoException();
        if (senha == null || senha.isEmpty()) throw new SenhaInvalidoException();
        if (endereco == null || endereco.isEmpty()) throw new EnderecoInvalidoException();

    }
}
