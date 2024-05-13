package co.edu.uniquindio.agencia20241.controller.service;

import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Usuario;

import java.util.ArrayList;

public interface IUsuarioService {
    Usuario crearUsuario(String nombre, String id, String correoElectronico, String reservasRealizadas) throws EmpleadoException;
    boolean eliminarUsuario(String id) throws EmpleadoException;
    boolean actualizarUsuario(String cedulaActual, String nombre, String correo) throws EmpleadoException;
    boolean  verificarUsuarioExistente(String cedula) throws EmpleadoException;
    Usuario obtenerUsuario(String cedula);
    ArrayList<Usuario> obtenerUsuarios();
}
