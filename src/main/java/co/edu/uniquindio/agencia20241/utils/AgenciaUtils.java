package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgenciaUtils {


    public static Agencia inicializarDatos() {

        Agencia agencia = new Agencia();

        // Crear un usuario
        Usuario usuario = new Usuario();
        usuario.setId("00");
        usuario.setNombre("Maria");
        usuario.setCorreoElectronico("Maria@gmail");
        usuario.setContrasenia("00");
        agencia.obtenerUsuarios().add(usuario);


        // Crear un usuario
        usuario = new Usuario();
        usuario.setId("11");
        usuario.setNombre("Juana");
        usuario.setCorreoElectronico("Juana@gmail");
        usuario.setContrasenia("11");
        agencia.obtenerUsuarios().add(usuario);

        // Crear un empleado
        Empleado empleado = new Empleado();
        empleado.setNombre("juan");
        empleado.setCorreoElectronico("juan@");
        empleado.setId("123");
        empleado.setEventosAsiganados("Cocina");
        empleado.setContrasenia("123");
        agencia.obtenerEmpleados().add(empleado);

        // Crear un evento
        Eventos evento = new Eventos();
        evento.setCapacidadMaximaEvento(100);
        evento.setDescripcionEvento("Baile");
        evento.setFechaEvento("2023-01-30");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Bailando");
        evento.setUbicacionEvento("Bolo Club");
        agencia.obtenerEventos().add(evento);


        evento = new Eventos();
        evento.setCapacidadMaximaEvento(12100);
        evento.setDescripcionEvento("Comedor");
        evento.setFechaEvento("2023-02-20");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Comida");
        evento.setUbicacionEvento("Universidad");
        agencia.obtenerEventos().add(evento);


        evento = new Eventos();
        evento.setCapacidadMaximaEvento(3100);
        evento.setDescripcionEvento("Comedor");
        evento.setFechaEvento("2023-02-20");
        evento.setHoraEvento("02:43");
        evento.setNombreEvento("Comida");
        evento.setUbicacionEvento("Universidad");

        Reserva reserva = new Reserva();
        reserva.setEstadoReserva("Aceptado");
        reserva.setId("121");
        reserva.setFechaSolicitud(LocalDate.now());

        Eventos eventoReserva = new Eventos();
        eventoReserva.setNombreEvento("Tango");


        reserva.setEvento(eventoReserva);
        reserva.setUsuario("1231");

        agencia.obtenerReservas().add(reserva);

        return agencia;
    }

}
