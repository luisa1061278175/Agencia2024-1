package co.edu.uniquindio.agencia20241.model;

import java.util.ArrayList;

public class Empleado extends Persona {

    private String eventosAsiganados;

    private String contrasenia;

    public Empleado() {


    }

    public Empleado(String nombre, String id, String correoElectronico, String eventosAsiganados, String contrasenia) {
        super(nombre, id, correoElectronico);
        this.eventosAsiganados = eventosAsiganados;
        this.contrasenia = contrasenia;
    }

    public String getEventosAsiganados() {
        return eventosAsiganados;
    }

    public void setEventosAsiganados(String eventosAsiganados) {
        this.eventosAsiganados = eventosAsiganados;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}