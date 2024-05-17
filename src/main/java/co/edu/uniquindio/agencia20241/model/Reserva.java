package co.edu.uniquindio.agencia20241.model;

import java.time.LocalDate;

public class Reserva {
    private String id;
    private Usuario usuario;
    private Eventos evento;
    private String  fechaSolicitud;
    private String estadoReserva;

    public Reserva(String id, Usuario usuario, Eventos evento, String fechaSolicitud, String estadoReserva) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.fechaSolicitud = fechaSolicitud;
        this.estadoReserva = estadoReserva;
    }

    public Reserva(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
