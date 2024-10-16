package Models;

import Exceptions.FormatoHoraInvalidoException;
import Exceptions.HorarioInvalidoException;
import Exceptions.MercadoHorariosInvalidosException;

public class Mercado extends Empresa {
    private String abre;
    private String fecha;
    private String tipoMercado;


    public Mercado(){}
    public Mercado(int id, String nome, String endereco, String abre, String fecha, String tipoMercado, Dono dono){
        super(id,nome,endereco,dono);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    @Override
    public String getTipoEmpresa() {
        return "Mercado";
    }

    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) throws FormatoHoraInvalidoException {
        this.tipoMercado = tipoMercado;
    }
    public static void validarHorarios(String abre, String fecha) throws FormatoHoraInvalidoException, HorarioInvalidoException, MercadoHorariosInvalidosException {
            if(abre.isEmpty() || fecha.isEmpty()){
                throw new FormatoHoraInvalidoException();

            }
            // Primeiro, verifica o formato dos horários usando regex
            String regex = "^\\d{2}:\\d{2}$";

            if (!abre.matches(regex) || !fecha.matches(regex)) {
                throw new FormatoHoraInvalidoException();
            }

            // Extrair horas e minutos de "abre"
            //String[] abreParts = abre.split(":");
            //int abreHora = Integer.parseInt(abreParts[0]);
            //int abreMinuto = Integer.parseInt(abreParts[1]);

            // Extrair horas e minutos de "fecha"
            String[] fechaParts = fecha.split(":");
            int fechaHora = Integer.parseInt(fechaParts[0]);
            int fechaMinuto = Integer.parseInt(fechaParts[1]);

            // Verificar se o "fecha" não passa de 23:59
            if (fechaHora > 23 || (fechaHora == 23 && fechaMinuto > 59)) {
                throw new MercadoHorariosInvalidosException();
            }
            if(fechaHora < 5){
                throw new MercadoHorariosInvalidosException();
            }

            // Verificar se o horário de fechamento é posterior ao de abertura
            //if (fechaHora < abreHora || (fechaHora == abreHora && fechaMinuto <= abreMinuto)) {
              //  return false; // Horário de fechamento não pode ser antes ou igual ao de abertura
            //}


    }

}
