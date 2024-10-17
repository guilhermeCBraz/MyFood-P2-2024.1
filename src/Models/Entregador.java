package Models;

import Exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;
    private List<Empresa> empresaslist;
    private String status = "livre";

    public Entregador(){}

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidoException {

        super(id, nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
        this.empresaslist = new ArrayList<>();
    }

    @Override
    public String getTipoUsuario() {
        return "Entregador";
    }


    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<Empresa> getEmpresaslist() {
        return empresaslist;
    }

    public void setEmpresaslist(List<Empresa> empresaslist) {
        this.empresaslist = empresaslist;
    }

    public void adicionarEmpresa(Empresa empresa){
        empresaslist.add(empresa);
    }

    public String getStatus() {
        return status;
    }

    public void entregadorLivre() {
        this.status = "livre";
    }
    public void entregadorOcupado() {
        this.status = "ocupado";
    }

}
