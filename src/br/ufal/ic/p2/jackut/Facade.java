package br.ufal.ic.p2.jackut;

import Exceptions.*;
import Models.Sistema;
import Models.Usuario;
import easyaccept.EasyAccept;

import java.util.ArrayList;

public class Facade {
    Sistema sistema = new Sistema();

        public void zerarSistema() {
            sistema.zerarSistema();
        }

        public void criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, UsuarioNaoCadastradoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {
            sistema.criarUsuario(nome, email, senha, endereco);
        }

        public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UsuarioNaoCadastradoException, NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException {
            sistema.criarUsuario(nome, email, senha, endereco, cpf);
        }

        public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
            return sistema.getAtributoUsuario(id, atributo);
        }
}
