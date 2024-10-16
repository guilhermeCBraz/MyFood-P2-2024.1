package Models;

public class Restaurante extends Empresa {
    private String tipoCozinha;


    public Restaurante(){}
    @Override
    public String getTipoEmpresa() {
        return "Restaurante";
    }

    public Restaurante(int id, String nome, String endereco, String tipoCozinha, Dono dono){
        super(id, nome, endereco, dono);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }



    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }





}
