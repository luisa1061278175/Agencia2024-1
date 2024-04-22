package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.Agencia;
import co.edu.uniquindio.agencia20241.model.Empleado;

public class AgenciaUtils {
    public static Agencia inicializarDatos() {
        Agencia agencia = new Agencia();

        Empleado empleado = new Empleado();
        empleado.setNombre("juan");
        empleado.setCorreoElectronico("juan@");
        empleado.setId("123");
        empleado.setEventosAsiganados("Cocina");

        agencia.getListaEmpleados().add(empleado);

        empleado = new Empleado();
        empleado.setNombre("nicolas");
        empleado.setCorreoElectronico("nicolas@");
        empleado.setId("456");
        empleado.setEventosAsiganados("Cocina");

        return agencia;
    }
}
