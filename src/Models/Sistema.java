package Models;

import Database.*;
import Exceptions.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static Models.Mercado.validarHorarios;

public class Sistema {

    HashMap<Integer, Usuario> mapUsuarios2 = PersistenciaUsuario.carregarDados();
    HashMap<Integer, Empresa> mapEmpresas = PersistenciaEmpresas.carregarDados();
    HashMap<Integer, Produto> mapProdutos = PersistenciaProdutos.carregarDados();
    HashMap<Integer, Pedido> mapPedidos = PersistenciaPedidos.carregarDados();
    HashMap<Integer, Entrega> MapEntregas = PersistenciaEntregas.carregarDados();



    int contadorID = 1;
    int contadorEmpresaID = 1;
    int contadorProdutoID = 1;
    int contadorPedidoID = 1;
    int contadorEntregaID = 1;

    public void zerarSistema() {
        
        mapUsuarios2 = null;
        mapEmpresas = null;
        mapProdutos = null;
        mapPedidos = null;
        MapEntregas = null;

        PersistenciaUsuario.apagarDados();
        PersistenciaEmpresas.apagarDados();
        PersistenciaProdutos.apagarDados();
        PersistenciaPedidos.apagarDados();
        PersistenciaEntregas.apagarDados();


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

    //Método para criação de um usuário do tipo Cliente.
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
    //Método para criação de um usuário do tipo Dono.
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException, CpfInvalidoException, EmailJaExisteException {
        if(mapUsuarios2 == null){
            mapUsuarios2 = new HashMap<Integer, Usuario>();
        }
        Dono dono = new Dono(contadorID, nome, email, senha, endereco, cpf);

        mapUsuarios2.put(contadorID , dono);
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
            Dono d1 = (Dono) mapUsuarios2.get(id);
            res = d1.getCpf();
        }
        else if (atributo.equals("senha"))
        {
            res = usuario.getSenha();
        }
        else if(atributo.equals("veiculo")){
            Entregador entre1 = (Entregador) usuario;
            res = entre1.getVeiculo();
        }
        else if(atributo.equals("placa")){
            Entregador entre2 = (Entregador) usuario;
            res = entre2.getPlaca();
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
        if (usuario == null || !usuario.getTipoUsuario().equals("Dono")) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }

        Dono dono1 = (Dono) usuario;

        // Verificar duplicidade de nome e endereço
        for (Empresa empresa : mapEmpresas.values()) {
            if (empresa.getNome().equals(nome)) {
                Restaurante r1 = (Restaurante) empresa;
                if(r1.getDono().getId() != dono) {
                throw new EmpresaNomeJaExisteException();
            }}
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new MesmoNomeELocalExcepition();
            }
        }

        Restaurante novaEmpresa = new Restaurante(contadorEmpresaID, nome, endereco, tipoCozinha, dono1);
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
            if (usuario == null || !usuario.getTipoUsuario().equals("Dono")) {
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
                Restaurante r1 = (Restaurante) empresa;
                return r1.getTipoCozinha();
            case "dono":
                return empresa.getDono().getNome();
            case "numeroFuncionarios":
                Farmacia f1 = (Farmacia) empresa;
                return String.valueOf(f1.getNumeroFuncionarios());
            case "aberto24Horas":
                Farmacia f2 = (Farmacia) empresa;
                boolean x = f2.isAberto24Horas();
                if(x){return "true";}
                else{return "false";}
            case "abre":
                Mercado m1 = (Mercado) empresa;
                return m1.getAbre();
            case "fecha":
                Mercado m2 = (Mercado) empresa;
                return m2.getFecha();
            case "tipoMercado":
                Mercado m3 = (Mercado) empresa;
                return m3.getTipoMercado();
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
        if(mapPedidos == null){
            mapPedidos = new HashMap<Integer, Pedido>();
        }
        //Verificar se cliente não é Dono de Restaurante
        Usuario user = mapUsuarios2.get(cliente);
        Empresa empre = mapEmpresas.get(empresa);
        int x = contadorPedidoID;
        contadorPedidoID++;
        if(user.getTipoUsuario().equals("Dono")){
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
                break;
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
                List<Produto> x = pedido.getProdutos();
                StringBuilder sb = new StringBuilder("{[");
                boolean first = true;

                for (Produto produto : x) {
                    if (!first){ sb.append(", ");}
                    sb.append(produto.getNome());
                    first = false;
                }
                sb.append("]}");
                return sb.toString();
            case "valor":
                return decimalFormat.format(pedido.calcularValor());
                //return String.format("%.2f",);
            default:
                throw new AtributoNaoExisteException();
        }

    }
    public int getNumeroPedido(int cliente, int empresa, int indice){
        Usuario user = mapUsuarios2.get(cliente);
        Empresa empre = mapEmpresas.get(empresa);
        List<Pedido> pedidosDoCliente = new ArrayList<>();

        for (Pedido pedido : mapPedidos.values()) {
            if (pedido.getCliente().equals(user.getNome()) && pedido.getEmpresa().equals(empre.getNome())) {
                pedidosDoCliente.add(pedido);
            }
        }
        // Ordenar a lista pelo mais antigo (baseado no ID, assumindo que IDs são incrementais)
        pedidosDoCliente.sort(Comparator.comparingInt(Pedido::getId));
        return pedidosDoCliente.get(indice).getId();
    }

    //MÉTODOS DO MERCADO

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws MesmoNomeELocalExcepition, EmpresaNomeJaExisteException, UsuarioNaoPodeCriarEmpresaException, TipoEmpresaInvalidoException, NomeInvalidoException, EnderecoInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException, FormatoHoraInvalidoException, EnderecoEmpresaInvalidoException, MercadoHorariosInvalidosException {

        if(mapEmpresas == null){
            mapEmpresas = new HashMap<>();
        }
        if(abre == null || fecha == null){
            throw new HorarioInvalidoException();
        }
        validarHorarios(abre, fecha);
        Usuario usuario = mapUsuarios2.get(dono);
        if (usuario == null || !usuario.getTipoUsuario().equals("Dono")) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }
        if(tipoEmpresa == null || tipoEmpresa.isEmpty()){
            throw new TipoEmpresaInvalidoException();
        }
        if(nome == null || nome.isEmpty()){
            throw new NomeInvalidoException();
        }
        if(endereco == null || endereco.isEmpty()){
            throw new EnderecoEmpresaInvalidoException();
        }
        if(tipoMercado == null || tipoMercado.isEmpty()){
            throw new TipoMercadoInvalidoException();
        }


        for(Empresa empresa: mapEmpresas.values()){
            if(empresa.getNome().equals(nome)){
                if(empresa.getDono().getId() == usuario.getId()){
                    if(empresa.getEndereco().equals(endereco)){
                        throw new MesmoNomeELocalExcepition();
                    }
                }
                else{throw new EmpresaNomeJaExisteException();}
            }
        }

        Dono dono1 = (Dono) usuario;
        int x = contadorEmpresaID;
        Mercado mercado = new Mercado(x, nome, endereco, abre, fecha ,tipoMercado, dono1);
        mapEmpresas.put(x, mercado);
        contadorEmpresaID++;


        return x;

    }
    public void alterarFuncionamento(int mercado, String abre, String fecha) throws HorarioInvalidoException, MercadoHorariosInvalidosException, FormatoHoraInvalidoException, NaoMercadoValidoException {

        if(abre == null || fecha == null){
            throw new MercadoHorariosInvalidosException();

        }
        validarHorarios(abre, fecha);
        Empresa empresa = mapEmpresas.get(mercado);

        if(!empresa.getTipoEmpresa().equals("Mercado")){
            throw new NaoMercadoValidoException();
        }

        Mercado m1 = (Mercado) empresa;
        m1.setAbre(abre);
        m1.setFecha(fecha);

    }


    //MÉTODOS FARMÁCIA

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarEmpresaException, TipoEmpresaInvalidoException, NomeInvalidoException, EnderecoInvalidoException, MesmoNomeELocalExcepition, EmpresaNomeJaExisteException, EnderecoEmpresaInvalidoException {
        if(mapEmpresas == null){
            mapEmpresas = new HashMap<>();
        }

        //Verificar se o usuário é dono de Restaurante
        Usuario usuario = mapUsuarios2.get(dono);
        if (usuario == null || !usuario.getTipoUsuario().equals("Dono")) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }


        if(nome == null || nome.isEmpty()){
            throw new NomeInvalidoException();}
        if(endereco == null || endereco.isEmpty()){
            throw new EnderecoEmpresaInvalidoException();}
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()){
            throw new TipoEmpresaInvalidoException();
        }

        Dono dono1 = (Dono) usuario;

        // Verificar duplicidade de nome e endereço
        for (Empresa empresa : mapEmpresas.values()) {
            if (empresa.getNome().equals(nome)) {
                Farmacia r1 = (Farmacia) empresa;
                if(r1.getDono().getId() != dono) {
                    throw new EmpresaNomeJaExisteException();
                }}
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new MesmoNomeELocalExcepition();
            }
        }
        int x = contadorEmpresaID;
        Farmacia farmacia = new Farmacia(x, nome, endereco, aberto24Horas,dono1, numeroFuncionarios);
        mapEmpresas.put(x, farmacia);
        contadorEmpresaID++;
        return x;


    }

    ///MÉTODOS ENTREGADOR
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailJaExisteException, VeiculoInvalidoException, PlacaInvalidoException {
        if(mapUsuarios2 == null){
            mapUsuarios2 = new HashMap<Integer, Usuario>();
        }
        Entregador entregador = new Entregador(contadorID, nome, email, senha, endereco, veiculo, placa);
        if(veiculo == null || veiculo.isEmpty()){throw new VeiculoInvalidoException();}
        if(placa == null || placa.isEmpty()){throw new PlacaInvalidoException();}
        if (Usuario.buscaUsuarioPorEmail(mapUsuarios2, email)) {
            throw new EmailJaExisteException();
        }

        entregador.verificaNome(nome);
        entregador.verificaEmail(email);
        entregador.verificaSenha(senha);
        entregador.verificaEndereco(endereco);

        mapUsuarios2.put(contadorID ,entregador);
        contadorID++;
    }

    public void cadastrarEntregador(int empresaId, int entregadorId) throws UsuarioNaoEntregadorException {
        Usuario usuario = mapUsuarios2.get(entregadorId);

        // Verifica se o usuário é entregador
        if (!usuario.getTipoUsuario().equals("Entregador")) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;
        Empresa empresa = mapEmpresas.get(empresaId);
        entregador.adicionarEmpresa(empresa);
        empresa.adicionarEntregador(entregador);
    }


    public String getEntregadores(int empresaId) {
        Empresa empre = mapEmpresas.get(empresaId);
        List<Entregador> entregadores = empre.getEntregadorList();
        if (entregadores == null || entregadores.isEmpty()) {
            return "{[]}";
        }

        StringBuilder sb = new StringBuilder("{[");
        boolean first = true;

        for (Entregador entregador : entregadores) {
            if (!first) sb.append(", ");
            sb.append(entregador.getEmail());
            first = false;
        }

        sb.append("]}");
        return sb.toString();
    }
    public String getEmpresas(int entregadorId) throws UsuarioNaoEntregadorException {
        Usuario usuario = mapUsuarios2.get(entregadorId);
        if (!usuario.getTipoUsuario().equals("Entregador")) {
            throw new UsuarioNaoEntregadorException();
        }
        Entregador entre = (Entregador) usuario;

        List<Empresa> empresas = entre.getEmpresaslist();
        if (empresas == null || empresas.isEmpty()) {
            return "{[]}";
        }

        StringBuilder sb = new StringBuilder("{[");
        boolean first = true;

        for (Empresa empresa : empresas) {
            if (!first) sb.append(", ");
            sb.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
            first = false;
        }

        sb.append("]}");
        return sb.toString();
    }


    //MÉTODOS ENTREGA
    public void liberarPedido(int numero) throws NaoPossivelLiberarPedidoAbertoException, PedidoJaLiberadoException{

        Pedido pedido = mapPedidos.get(numero);
        //if(pedido == null){throw new NaoPossivelLiberarPedidoAbertoException();}
        if(pedido.getEstado().equals("aberto")){throw new NaoPossivelLiberarPedidoAbertoException();}
        if(pedido.getEstado().equals("pronto")){throw new PedidoJaLiberadoException();}
        pedido.liberarPedido();
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws NaoEntregadorValidoException, PedidoNaoProntoEntregaException, EntregadorAindaEntregaException {
        if(mapPedidos == null){
            mapPedidos = new HashMap<Integer, Pedido>();
        }
        if(MapEntregas == null){
            MapEntregas = new HashMap<>();
        }
        Usuario user = mapUsuarios2.get(entregador);
        Pedido pedido1 = mapPedidos.get(pedido);
        String estado = pedido1.getEstado();
        String nome = pedido1.getCliente();
        if(pedido1 == null ||!estado.equals("pronto")){throw new PedidoNaoProntoEntregaException();}
        if(!user.getTipoUsuario().equals("Entregador")){throw new NaoEntregadorValidoException();}
        Entregador entregad = (Entregador) user;
        if(entregad.getStatus().equals("ocupado")){
            throw new EntregadorAindaEntregaException();

        }
        pedido1.entregandoPedido();
        entregad.entregadorOcupado();
        if(destino == null || destino.isEmpty()){
            for(Usuario usuario: mapUsuarios2.values()){
                if(usuario.getNome().equals(nome)){
                    destino = usuario.getEndereco();
                }
            }
        }
        Entrega entrega = new Entrega(contadorEntregaID, pedido1.getCliente(), pedido1.getEmpresa(), pedido1.getId(), entregad.getId(), destino, pedido1.getProdutos());

        int x = contadorEntregaID;
        MapEntregas.put(x, entrega);

        contadorEntregaID++;

        return x;
    }

    public int obterPedido(int entregador) throws UsuarioNaoEntregadorException, EntregadorNenhumaEmpresaException, NaoExistePedidoEntregaException{
        Usuario user = mapUsuarios2.get(entregador);
        if(!user.getTipoUsuario().equals("Entregador")){throw new UsuarioNaoEntregadorException();}
        Entregador entre = (Entregador) user;
        int id = 0;
        //List<Integer> pedidosFila = List.of();
        List<Empresa> empresasEntregador = entre.getEmpresaslist();
        if(empresasEntregador.isEmpty()){throw new EntregadorNenhumaEmpresaException();}
        for(Empresa empresa: empresasEntregador){
            for(Pedido pedido: mapPedidos.values()){
                if(pedido.getEmpresa().equals(empresa.getNome()) && pedido.getEstado().equals("pronto")){
                    if(empresa.getTipoEmpresa().equals("Farmacia")){
                        id = pedido.getId();
                        break;
                    }
                    else{
                        id = pedido.getId();
                        break;
                    }
                }




            }
        }
        if(id == 0){throw new NaoExistePedidoEntregaException();}

        return id;
    }
    public String getEntrega(int id, String atributo) throws AtributoInvalidoException, AtributoNaoExisteException {
        if(MapEntregas == null){
            MapEntregas = new HashMap<>();
        }
        Entrega entrega = MapEntregas.get(id);
        if(atributo == null || atributo.isEmpty()){throw new AtributoInvalidoException();}
        switch (atributo) {
            case "cliente":
                return entrega.getCliente();
            case "empresa":
                return entrega.getEmpresa();
            case "pedido":
                return String.valueOf(entrega.getPedido());
            case "entregador":
                int x = entrega.getEntregador();
                Usuario user = mapUsuarios2.get(x);
                Entregador entre = (Entregador) user;
                return entre.getNome();
            case "destino":
                return entrega.getDestino();
            case "produtos":
                List<Produto> y = entrega.getProdutos();
                StringBuilder sb = new StringBuilder("{[");
                boolean first = true;

                for (Produto produto : y) {
                    if (!first){ sb.append(", ");}
                    sb.append(produto.getNome());
                    first = false;
                }
                sb.append("]}");
                return sb.toString();
            default:
                throw new AtributoNaoExisteException();
        }
    }


    public void entregar(int entrega) throws NaoExisteNadaEntregueException{
        if(!MapEntregas.containsKey(entrega)){throw new NaoExisteNadaEntregueException();}
        Entrega entre = MapEntregas.get(entrega);
        int pedido = entre.getPedido();
        Pedido ped = mapPedidos.get(pedido);
        ped.entregarPedido();
        int entregador = entre.getEntregador();
        Usuario user = mapUsuarios2.get(entregador);
        Entregador entre1 = (Entregador) user;
        entre1.entregadorLivre();

    }
    public int getIdEntrega(int pedido) throws NaoExisteNadaEntregueException, NaoExisteEntregaComEsseIdException {
        for(Entrega entrega: MapEntregas.values()){
            if(entrega.getPedido() == pedido){
                return entrega.getId();
            }
        }
        throw new NaoExisteEntregaComEsseIdException();


    }


    public void encerrarSistema() {

        PersistenciaUsuario.gravarDados(mapUsuarios2);
        PersistenciaEmpresas.gravarDados(mapEmpresas);
        PersistenciaProdutos.gravarDados(mapProdutos);
        PersistenciaPedidos.gravarDados(mapPedidos);
        PersistenciaEntregas.gravarDados(MapEntregas);
    }
}
