package Models;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import Exceptions.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import Models.Empresa;
import Database.PersistenciaUsuario;
import Database.PersistenciaEmpresas;
import Database.PersistenciaProdutos;
public class Sistema {

    //HashMap<Integer, Usuario> mapUsuarios = new HashMap<Integer, Usuario>(); /// Usado para armazenar
    HashMap<Integer, Usuario> mapUsuarios2 = PersistenciaUsuario.carregarDados();// Tem o conteúdo armazenado
    //HashMap<Integer, Empresa> mapEmpresas = new HashMap<Integer, Empresa>();
    HashMap<Integer, Empresa> mapEmpresas = PersistenciaEmpresas.carregarDados();
    HashMap<Integer, Produto> mapProdutos = PersistenciaProdutos.carregarDados();
    HashMap<Integer, Pedido> mapPedidos = new HashMap<Integer, Pedido>();



    int contadorID = 1;
    int contadorEmpresaID = 1;
    int contadorProdutoID = 1;
    int contadorPedidoID = 1;

    public void zerarSistema() {
        // zerarSistema
        // descrição: Apaga todos os dados no banco de dados do sistema.
        // retorno: Sem retorno
        mapUsuarios2 = null;
        mapEmpresas = null;
        mapProdutos = null;

        PersistenciaUsuario.apagarDados();
        PersistenciaEmpresas.apagarDados();
        PersistenciaProdutos.apagarDados();


    }

    public static Usuario buscarUsuarioPorId(HashMap<Integer, Usuario> mapaUsuarios, int id) throws UsuarioNaoCadastradoException {
        if(mapaUsuarios.containsKey(id)){
            return mapaUsuarios.get(id);
        }
        else{
            throw new UsuarioNaoCadastradoException();
        }
    }

    //MÉTODO PARA COLOCAR AS EMPRESAS DE ACORDO COM ID DONO NUM ARRAYLIST

    public List<String> obterEmpresas(int idDono){
        List<String> empresas = new ArrayList<>();
        for (Empresa empresa : mapEmpresas.values()) {
            if (empresa.getDono().getId() == idDono) {
                empresas.add("[" + empresa.getNome() + ", " + empresa.getEndereco()+ "]");
            }
        }

        return empresas;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailJaExisteException {
        if(mapUsuarios2 == null){
            mapUsuarios2 = new HashMap<Integer, Usuario>();
        }
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        if (Usuario.buscaUsuarioPorEmail(mapUsuarios2, email)) {
            throw new EmailJaExisteException();
        }


        mapUsuarios2.put(contadorID ,cliente);
        contadorID++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException, EmailJaExisteException {
        if(mapUsuarios2 == null){
            mapUsuarios2 = new HashMap<Integer, Usuario>();
        }
        DonoRestaurante donoRestaurante = new DonoRestaurante(contadorID, nome, email, senha, endereco, cpf);
        //if (Usuario.buscaUsuarioPorEmail(mapUsuarios, email)) {
          //  throw new EmailJaExisteException();
        //}

        mapUsuarios2.put(contadorID ,donoRestaurante);
        contadorID++;
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException
    {
        if (mapUsuarios2 == null ||!mapUsuarios2.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }
        Usuario usuario = mapUsuarios2.get(id);
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
        }

        else if (atributo.equals("cpf"))
        {
            DonoRestaurante d1 = (DonoRestaurante) mapUsuarios2.get(id);
            res = d1.getCpf();
        }
        else if (atributo.equals("senha"))
        {
            res = usuario.getSenha();
        }

        return res;


    }

    public int login(String email, String senha) throws LoginSenhaInvalidosException{

        //Verificando se está no mapUsuários
        for(Usuario usuario: mapUsuarios2.values()){
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                return usuario.getId();
            }

        }
        throw new LoginSenhaInvalidosException();

    }


    ////MÉTODOS RELACIONADOS A EMPRESA
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarEmpresaException, MesmoNomeELocalExcepition, EmpresaNomeJaExisteException{
        if(mapEmpresas == null){
            mapEmpresas = new HashMap<>();
        }




        Usuario usuario = mapUsuarios2.get(dono);

        // Verificar se o dono é válido e do tipo certo
        if (usuario == null || !usuario.getTipoUsuario().equals("DonoRestaurante")) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        DonoRestaurante dono1 = (DonoRestaurante) usuario;

        // Verificar duplicidade de nome e endereço
        for (Empresa empresa : mapEmpresas.values()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new EmpresaNomeJaExisteException();
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new MesmoNomeELocalExcepition();
            }
        }

        Empresa novaEmpresa = new Empresa(contadorEmpresaID, nome, endereco, tipoCozinha, dono1);
        mapEmpresas.put(contadorEmpresaID, novaEmpresa);
        int x = contadorEmpresaID;
        contadorEmpresaID++;
        return x;
    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoPodeCriarEmpresaException {

        if (!mapUsuarios2.containsKey(idDono)) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }
        else{
            Usuario usuario = mapUsuarios2.get(idDono);

            // Verificar se o dono é válido e do tipo certo
            if (usuario == null || !usuario.getTipoUsuario().equals("DonoRestaurante")) {
                throw new UsuarioNaoPodeCriarEmpresaException();
            }
        }

        List<String> empresas = obterEmpresas(idDono);

        // Verifica se a lista está vazia
        if (empresas.isEmpty()) {
            return "{[]}";
        }

        // Junta os elementos da lista com ", " e coloca dentro de chaves
        return "{[" + String.join(", ", empresas) + "]}";

    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws IndiceInvalidoExcepition, NomeInvalidoException, IndiceMaiorQueEsperadoException, NaoExisteEmpresaComEsseNomeExcepition {
        if (indice < 0) {
            throw new IndiceInvalidoExcepition();
        }
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }

        // Obtém todas as empresas do dono com o nome fornecido
        List<Empresa> empresasDoDono = new ArrayList<>();
        for (Empresa empresa : mapEmpresas.values()) {
            if (empresa.getDono().getId() == idDono && empresa.getNome().equals(nome)) {
                empresasDoDono.add(empresa);
            }
        }

        // Verifica se não há empresas com o nome fornecido
        if (empresasDoDono.isEmpty()) {
            throw new NaoExisteEmpresaComEsseNomeExcepition();
        }

        // Verifica se o índice é maior do que o número de empresas disponíveis
        if (indice >= empresasDoDono.size()) {
            throw new IndiceMaiorQueEsperadoException();
        }

        // Retorna o ID da empresa correspondente ao índice
        return empresasDoDono.get(indice).getId();
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        Empresa empresa = mapEmpresas.get(idEmpresa);

        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        }
        if(atributo == null || atributo.isEmpty()){throw new AtributoInvalidoException();}

        switch (atributo) {
            case "nome":
                return empresa.getNome();
            case "endereco":
                return empresa.getEndereco();
            case "tipoCozinha":
                return empresa.getTipoCozinha();
            case "dono":
                return empresa.getDono().getNome();
            default:
                throw new AtributoInvalidoException();
        }
    }

    //////MÉTODOS DOS PRODUTOS
   public int criarProduto(int empresaId, String nome, float valor, String categoria) throws NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException, ProJaExisteException {
        // Validações
       if(mapProdutos == null){
           mapProdutos = new HashMap<Integer, Produto>();
       }
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
        if (valor < 0) throw new ValorInvalidoException();
        if (categoria == null || categoria.trim().isEmpty()) throw new CategoriaInvalidoException();

        // Verifica duplicidade de nome de produto
        for (Produto produto : mapProdutos.values()) {
            if (produto.getNome().equalsIgnoreCase(nome) && produto.getEmpresaId() == empresaId) {
                throw new ProJaExisteException();
            }
        }

        // Criação do produto
        Produto novoProduto = new Produto(contadorProdutoID, empresaId, nome, valor, categoria);
        mapProdutos.put(contadorProdutoID, novoProduto);
        int x = contadorProdutoID;
        contadorProdutoID++;

        return x;
    }
    public void editarProduto(int produtoId, String nome, float valor, String categoria) throws ProdutoNaoCadastrado, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
        if (valor < 0) throw new ValorInvalidoException();
        if (categoria == null || categoria.trim().isEmpty()) throw new CategoriaInvalidoException();

        Produto produto = mapProdutos.get(produtoId);
        if (produto == null) throw new ProdutoNaoCadastrado();

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String listarProdutos(int empresaId) throws EmpresaNaoEncontradaException{
        if(!mapEmpresas.containsKey(empresaId)){throw  new EmpresaNaoEncontradaException();}
        StringBuilder sb = new StringBuilder("{[");
        boolean first = true;

        for (Produto produto : mapProdutos.values()) {
            if (produto.getEmpresaId() == empresaId) {
                if (!first) sb.append(", ");
                sb.append(produto.getNome());
                first = false;
            }
        }

        sb.append("]}");
        return sb.toString();
    }
    public String getProduto(String nome, int empresaId, String atributo) throws ProdutoNaoEncontradoException, AtributoNaoExisteException {
        // Obtém a lista de produtos da empresa especificada


        List<Produto> produtosDaEmpresa = new ArrayList<>();
        for (Produto produto : mapProdutos.values()) {
            if (produto.getEmpresaId() == empresaId) {
                produtosDaEmpresa.add(produto);
            }
        }
        if (produtosDaEmpresa.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        // Busca o produto pelo nome na lista de produtos da empresa
        Produto produtoEncontrado = null;
        for (Produto produto : produtosDaEmpresa) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                produtoEncontrado = produto;
                break;
            }
        }

        // Se o produto não foi encontrado, lança a exceção
        if (produtoEncontrado == null) {
            throw new ProdutoNaoEncontradoException();
        }

        // Retorna o valor do atributo solicitado
        // Configurar o formato com ponto como separador decimal
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.00", symbols);

        switch (atributo.toLowerCase()) {
            case "nome":
                return produtoEncontrado.getNome();
            case "valor":
                return decimalFormat.format(produtoEncontrado.getValor());  // Usar DecimalFormat para garantir o formato
            case "categoria":
                return produtoEncontrado.getCategoria();
            case "empresa":
                return mapEmpresas.get(empresaId).getNome();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    //MÉTODOS DO PEDIDO

    public int criarPedido(int cliente, int empresa) throws DonoNaoPodePedirExcepition, DoisPedidosAbertosException{
        //Verificar se cliente não é Dono de Restaurante
        Usuario user = mapUsuarios2.get(cliente);
        Empresa empre = mapEmpresas.get(empresa);
        int x = contadorPedidoID;
        contadorPedidoID++;
        if(user.getTipoUsuario().equals("DonoRestaurante")){
            throw new DonoNaoPodePedirExcepition();
        }
        String nomeCilente = user.getNome();
        String nomeEmpresa = empre.getNome();
        //Verificar se o pedido já foi aberto
        for(Pedido pedido: mapPedidos.values()){
            if(pedido.getCliente().equals(nomeCilente) && pedido.getEmpresa().equals(nomeEmpresa) && pedido.getEstado().equals("aberto")){
                throw new DoisPedidosAbertosException();
            }
        }

        Pedido p1 = new Pedido(x, nomeCilente, nomeEmpresa);
        mapPedidos.put(x, p1);

        return x;
    }

    public void adicionarProduto(int numero, int produto) throws NaoExistePedidoAbertoException, ProdutoNaoPertenceEmpresaException, PedidoFechadoException, NaoPossivelAdicionarFechadoException{
        if (!mapPedidos.containsKey(numero)) {
            throw new NaoExistePedidoAbertoException();
        }
        Pedido pe1 = mapPedidos.get(numero);
        Produto pro1 = mapProdutos.get(produto);
        if(pe1.getEstado().equals("preparando")){
            throw new NaoPossivelAdicionarFechadoException();

        }
        int empresaId = pro1.getEmpresaId();
        String nomeEmpresaProduto = pe1.getEmpresa();
        int id = 0;

        for(Empresa empresa: mapEmpresas.values()){
            if(empresa.getNome().equals(nomeEmpresaProduto)){
                id = empresa.getId();
            }
        }

        if(empresaId != id){
            throw new ProdutoNaoPertenceEmpresaException();
        }
        pe1.adicionarProduto(pro1);



    }
    public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException {
        Pedido pedido = mapPedidos.get(numeroPedido);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.fecharPedido();
    }

    public void removerProduto(int pedido, String produto) throws PedidoFechadoException, ProdutoInvalidoException, ProdutoNaoEncontradoException{
        Pedido pe1 = mapPedidos.get(pedido);
        if(produto == null || produto.isEmpty()){
            throw new ProdutoInvalidoException();
        }

        if (pe1.getEstado().equals("preparando")) {
            throw new PedidoFechadoException();
        }
        boolean x = false;
        for(Produto p1: mapProdutos.values()){
            if(p1.getNome().equals(produto)){
                x = true;

            }
        }
        if(!x){
            throw new ProdutoNaoEncontradoException();

        }
        pe1.removerProduto(produto);


    }

    public String getPedidos(int numero, String atributo) throws AtributoInvalidoException, AtributoNaoExisteException{
        if(atributo == null || atributo.isEmpty()){
            throw new AtributoInvalidoException();
        }
        Pedido pedido = mapPedidos.get(numero);
        // Configurar o formato com ponto como separador decimal
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("#.00", symbols);


        switch (atributo) {
            case "cliente":
                return pedido.getCliente();
            case "empresa":
                return pedido.getEmpresa();
            case "estado":
                return pedido.getEstado();
            case "produtos":
                String x = pedido.getProdutos();
                return x;
            case "valor":
                return decimalFormat.format(pedido.calcularValor());
                //return String.format("%.2f",);
            default:
                throw new AtributoNaoExisteException();
        }

    }








    public void encerrarSistema() {
        // encerrarSistema
        // descrição: Finaliza a execução do programa
        // retorno: Sem retorno

        PersistenciaUsuario.gravarDados(mapUsuarios2);
        PersistenciaEmpresas.gravarDados(mapEmpresas);
        PersistenciaProdutos.gravarDados(mapProdutos);








    }
}
