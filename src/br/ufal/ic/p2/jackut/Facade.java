package br.ufal.ic.p2.jackut;

import Exceptions.*;
import Models.Sistema;
import easyaccept.EasyAccept;

public class Facade {
    Sistema sistema = new Sistema();

        public void zerarSistema() {
            sistema.zerarSistema();
        }

        public void criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailJaExisteException {
            sistema.criarUsuario(nome, email, senha, endereco);
        }

        public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException, EmailJaExisteException {
            sistema.criarUsuario(nome, email, senha, endereco, cpf);
        }

        public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
            return sistema.getAtributoUsuario(id, atributo);
        }


        public void encerrarSistema() {
            sistema.encerrarSistema();
        }
}
