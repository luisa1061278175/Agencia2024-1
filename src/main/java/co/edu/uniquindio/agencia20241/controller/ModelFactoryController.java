package co.edu.uniquindio.agencia20241.controller;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Agencia;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Usuario;
import co.edu.uniquindio.agencia20241.utils.AgenciaUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IAgenciaService {
    Agencia agencia;
    AgenciaMapper mapper = AgenciaMapper.INSTANCE;

    @Override
    public Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados) throws EmpleadoException {
        return agencia.crearEmpleado(nombre, id, correoElectronico, eventosAsiganados);
    }

    @Override
    public boolean eliminarEmpleado(String id) throws EmpleadoException {
        return agencia.eliminarEmpleado(id);
    }

    @Override
    public boolean actualizarEmpleado(String cedulaActual,  String nombre, String correo, String eventos) throws EmpleadoException {
        return agencia.actualizarEmpleado(cedulaActual, nombre, correo, eventos);
    }

    @Override
    public boolean verificarEmpleadoExistente(String cedula) throws EmpleadoException {
        return agencia.verificarEmpleadoExistente(cedula);
    }

    @Override
    public Empleado obtenerEmpleado(String cedula) {
        return agencia.obtenerEmpleado(cedula);
    }

    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
        return agencia.obtenerEmpleados();
    }


    //USUARIO

    @Override
    public Usuario crearUsuario(String nombre, String id, String correoElectronico, String eventosAsiganados) throws EmpleadoException {
        return agencia.crearUsuario(nombre, id, correoElectronico, eventosAsiganados);
    }

    @Override
    public boolean eliminarUsuario(String id) throws EmpleadoException {
        return agencia.eliminarUsuario(id);
    }

    @Override
    public boolean actualizarUsuario(String cedulaActual,  String nombre, String correo) throws EmpleadoException {
        return agencia.actualizarUsuario(cedulaActual, nombre, correo);
    }

    @Override
    public boolean verificarUsuarioExistente(String cedula) throws EmpleadoException {
        return agencia.verificarUsuarioExistente(cedula);
    }

    @Override
    public Usuario obtenerUsuario(String cedula) {
        return agencia.obtenerUsuario(cedula);
    }

    @Override
    public ArrayList<Usuario> obtenerUsuarios() {
        return agencia.obtenerUsuarios();
    }

    @Override
    public Eventos crearEvento(String nombreEvento, String descripcionEvento, LocalDate fechaEvento, LocalTime horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EmpleadoException {
        return null;
    }

    @Override
    public boolean eliminarEvento(String nombre) throws EmpleadoException {
        return false;
    }

    @Override
    public boolean actualizarEvento(String nombreEvento, String descripcionEvento, LocalDate fechaEvento, LocalTime horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EmpleadoException {
        return false;
    }

    @Override
    public boolean verificarEventoExistente(String nombre) throws EmpleadoException {
        return false;
    }

    @Override
    public Eventos obtenerEvento(String nombre) {
        return null;
    }

    @Override
    public ArrayList<Eventos> obtenerEventos() {
        return null;
    }

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("invocación clase singleton");
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        agencia = AgenciaUtils.inicializarDatos();
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }


}
