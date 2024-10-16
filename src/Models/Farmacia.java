package Models;

public class Farmacia extends Empresa{
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(){}
    public Farmacia(int id, String nome, String endereco, Boolean aberto24Horas, Dono dono, int numeroFuncionarios){
        super(id, nome, endereco, dono);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public String getTipoEmpresa() {
        return "Farmacia";
    }

    public boolean isAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }
}
