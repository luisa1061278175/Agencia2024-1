package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IReservasService {

    void agregarReserva(String id, Usuario usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva);

    ArrayList<Reserva> obtenerReservas();

    boolean  verificarEventoExistente(String nombre) throws EmpleadoException;
    Eventos obtenerEvento(String nombre);
    ArrayList<Eventos> obtenerEventos();
}
