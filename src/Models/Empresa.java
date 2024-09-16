package Models;

import java.util.List;

public class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private DonoRestaurante dono;


    public Empresa(){}

    public Empresa(int id, String nome, String endereco, String tipoCozinha, DonoRestaurante dono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.dono = dono;
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

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public DonoRestaurante getDono() {
        return dono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDono(DonoRestaurante dono) {
        this.dono = dono;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
