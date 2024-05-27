package co.edu.uniquindio.agencia20241.model;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private String id;
    private String usuario;
    private Eventos evento;
    private LocalDate fechaSolicitud;
    private String estadoReserva;

    public Reserva(String id, String usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.fechaSolicitud = LocalDate.now();
        this.estadoReserva = estadoReserva;
    }

    public Reserva(){

    }

    public Reserva(String idReserva, String s, Usuario usuario, LocalDate now, String estado) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
