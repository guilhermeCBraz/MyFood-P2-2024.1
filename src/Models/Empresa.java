package Models;

import java.util.ArrayList;
import java.util.List;

public abstract class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private Dono dono;
    private List<Entregador> entregadorList;



    public Empresa(){}


    public abstract String getTipoEmpresa();

    public Empresa(int id, String nome, String endereco, Dono dono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.dono = dono;
        this.entregadorList = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<Entregador> getEntregadorList() {
        return entregadorList;
    }

    public void setEntregadorList(List<Entregador> entregadorList) {
        this.entregadorList = entregadorList;
    }

    public void adicionarEntregador(Entregador entregador){
        entregadorList.add(entregador);
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public Dono getDono() {
        return dono;
    }
    public void setDono(Dono dono) {
        this.dono = dono;
    }

    static String formatarEmpresas(List<String> empresas) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < empresas.size(); i++) {
            sb.append(empresas.get(i));
            if (i < empresas.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
