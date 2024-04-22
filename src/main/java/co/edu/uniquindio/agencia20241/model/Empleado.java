package co.edu.uniquindio.agencia20241.model;

import java.util.ArrayList;

public class Empleado extends Persona {

    private String eventosAsiganados;

    ArrayList<Empleado> listaEmpleados = new ArrayList<>();

    public Empleado() {


    }

    public String getEventosAsiganados() {
        return eventosAsiganados;
    }

    public void setEventosAsiganados(String eventosAsiganados) {
        this.eventosAsiganados = eventosAsiganados;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
}