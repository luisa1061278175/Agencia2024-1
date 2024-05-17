package co.edu.uniquindio.agencia20241.model;

import java.util.ArrayList;

public class Usuario extends Persona {
    private String reservasRealizadas;

    private String contrasenia;

    public Usuario(String nombre, String id, String correoElectronico, String reservasRealizadas, String contrasenia) {
        super(nombre, id, correoElectronico);
        this.reservasRealizadas = reservasRealizadas;
        this.contrasenia = contrasenia;
    }

    public Usuario(){


    }

    public String getReservasRealizadas() {
        return reservasRealizadas;
    }

    public void setReservasRealizadas(String reservasRealizadas) {
        this.reservasRealizadas = reservasRealizadas;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}