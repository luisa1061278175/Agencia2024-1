package co.edu.uniquindio.agencia20241.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Eventos {
    private String nombreEvento;
    private String descripcionEvento;
    private LocalDate fechaEvento;
    private LocalTime horaEvento;
    private String ubicacionEvento;
    private int capacidadMaximaEvento;

    public Eventos(String nombreEvento, String descripcionEvento, LocalDate fechaEvento, LocalTime horaEvento, String ubicacionEvento, int capacidadMaximaEvento) {
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.ubicacionEvento = ubicacionEvento;
        this.capacidadMaximaEvento = capacidadMaximaEvento;
    }

    public Eventos() {
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public LocalTime getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(LocalTime horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getUbicacionEvento() {
        return ubicacionEvento;
    }

    public void setUbicacionEvento(String ubicacionEvento) {
        this.ubicacionEvento = ubicacionEvento;
    }

    public int getCapacidadMaximaEvento() {
        return capacidadMaximaEvento;
    }

    public void setCapacidadMaximaEvento(int capacidadMaximaEvento) {
        this.capacidadMaximaEvento = capacidadMaximaEvento;
    }
}
