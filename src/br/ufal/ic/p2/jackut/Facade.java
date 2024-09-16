package br.ufal.ic.p2.jackut;

import Exceptions.*;
import Models.Produto;
import Models.Sistema;
import easyaccept.EasyAccept;

import java.util.Collections;
import java.util.List;

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

        public int login(String email, String senha)throws LoginSenhaInvalidosException{return sistema.login(email, senha);}

        public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarEmpresaException, MesmoNomeELocalExcepition, EmpresaNomeJaExisteException{
            return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
        }

        public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoPodeCriarEmpresaException {
            return sistema.getEmpresasDoUsuario(idDono);
        }
        public int getIdEmpresa (int idDono, String nome, int indice) throws IndiceInvalidoExcepition, NomeInvalidoException, IndiceMaiorQueEsperadoException, NaoExisteEmpresaComEsseNomeExcepition
        {return sistema.getIdEmpresa(idDono, nome, indice);}

        public String getAtributoEmpresa(int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException{
            return sistema.getAtributoEmpresa(idEmpresa, atributo);
        }

        public int criarProduto(int empresaId, String nome, float valor, String categoria) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException, ProJaExisteException {
            return sistema.criarProduto(empresaId, nome, valor, categoria);
        }
        public void editarProduto(int produtoId, String nome, float valor, String categoria) throws ProdutoNaoCadastrado, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException {
            sistema.editarProduto(produtoId, nome, valor, categoria);
        }
        public String listarProdutos(int empresaId) throws EmpresaNaoEncontradaException {
            return sistema.listarProdutos(empresaId);
        }
        public String getProduto(String  nome, int empresa, String atributo) throws ProdutoNaoEncontradoException, AtributoNaoExisteException {
            return sistema.getProduto(nome, empresa, atributo);
        }

        public int criarPedido(int cliente, int empresa) throws DonoNaoPodePedirExcepition, DoisPedidosAbertosException{
            return sistema.criarPedido(cliente, empresa);
        }

        public void adicionarProduto(int numero, int produto) throws NaoExistePedidoAbertoException, ProdutoNaoPertenceEmpresaException, PedidoFechadoException, NaoPossivelAdicionarFechadoException{
            sistema.adicionarProduto(numero, produto);
        }
        public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException{
            sistema.fecharPedido(numeroPedido);
        }

        public void removerProduto(int pedido, String produto) throws PedidoFechadoException, ProdutoInvalidoException, ProdutoNaoEncontradoException{
            sistema.removerProduto(pedido, produto);
        }

        public String getPedidos(int numero, String atributo) throws AtributoInvalidoException, AtributoNaoExisteException{
            return sistema.getPedidos(numero, atributo);
        }




        public void encerrarSistema() {
            sistema.encerrarSistema();
        }
}
