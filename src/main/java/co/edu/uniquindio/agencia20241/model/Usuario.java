package co.edu.uniquindio.agencia20241.model;

import java.util.ArrayList;

public class Usuario extends Persona{
    private String reservasRealizadas;

    ArrayList<Usuario>listaUsuarios= new ArrayList<>();

    public Usuario(String nombre, String id, String correoElectronico, String reservasRealizadas, ArrayList<Usuario> listaUsuarios) {
        super(nombre, id, correoElectronico);
        this.reservasRealizadas = reservasRealizadas;
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario() {

    }

    public String getReservasRealizadas() {
        return reservasRealizadas;
    }

    public void setReservasRealizadas(String reservasRealizadas) {
        this.reservasRealizadas = reservasRealizadas;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
