package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.Agencia;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgenciaUtils {


    public static Agencia inicializarDatos() {

        Agencia agencia = new Agencia();
//USUARIO

        Usuario usuario= new Usuario();

        usuario.setId("00");
        usuario.setNombre("Maria");
        usuario.setCorreoElectronico("Maria@gmail");
        usuario.setContrasenia("00");

        agencia.obtenerUsuarios().add(usuario);


       usuario= new Usuario();

        usuario.setId("12");
        usuario.setNombre("Sandra");
        usuario.setCorreoElectronico("Sandra@gmail");
        usuario.setContrasenia("12");

        agencia.obtenerUsuarios().add(usuario);


        //EMPLEADO

        Empleado empleado = new Empleado();
        empleado.setNombre("juan");
        empleado.setCorreoElectronico("juan@");
        empleado.setId("123");
        empleado.setEventosAsiganados("Cocina");
        empleado.setContrasenia("123");

        agencia.obtenerEmpleados().add(empleado);

        empleado = new Empleado();
        empleado.setNombre("nicolas");
        empleado.setCorreoElectronico("nicolas@");
        empleado.setId("456");
        empleado.setEventosAsiganados("Cocina");

        agencia.obtenerEmpleados().add(empleado);

//EVENTO

        Eventos eventos= new Eventos();
        eventos.setCapacidadMaximaEvento(100);
        eventos.setDescripcionEvento("Baile");
        eventos.setFechaEvento("2023, 1, 30");
        eventos.setHoraEvento("02:43");
        eventos.setNombreEvento("Bailando ");
        eventos.setUbicacionEvento("Bolo Club");

agencia.obtenerEventos().add(eventos);




        return agencia;
    }
}
