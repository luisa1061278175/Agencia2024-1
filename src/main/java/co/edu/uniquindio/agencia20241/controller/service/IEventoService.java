package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface IEventoService {

    Eventos crearEvento(String nombreEvento,
                        String descripcionEvento,
                        String fechaEvento,
                        String horaEvento,
                        String ubicacionEvento,
                        int capacidadMaximaEvento) throws EmpleadoException;
    boolean eliminarEvento(String nombre) throws EmpleadoException;
    boolean actualizarEvento(String nombreEvento,
                             String descripcionEvento,
                             String fechaEvento,
                             String horaEvento,
                             String ubicacionEvento,
                             int capacidadMaximaEvento) throws EmpleadoException;
    boolean  verificarEventoExistente(String nombre) throws EmpleadoException;
    Eventos obtenerEvento(String nombre);
    ArrayList<Eventos> obtenerEventos();
}
