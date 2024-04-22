package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.model.Empleado;

import java.util.ArrayList;

public interface IAgenciaService {
    public Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados, ArrayList<Empleado> listaEmpleados) throws EmpleadoException;
    public boolean eliminarEmpleado(String id) throws EmpleadoException;
    boolean actualizarEmpleado(String cedulaActual, Empleado empleado) throws EmpleadoException;
    public boolean  verificarEmpleadoExistente(String cedula) throws EmpleadoException;
    public Empleado obtenerEmpleado(String cedula);
    public ArrayList<Empleado> obtenerEmpleados();
}
