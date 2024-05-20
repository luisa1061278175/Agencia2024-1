package co.edu.uniquindio.agencia20241.controller;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.exception.EventoException;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.*;
import co.edu.uniquindio.agencia20241.utils.AgenciaUtils;

import java.time.LocalDate;
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
    public boolean actualizarEmpleado(String cedulaActual, String nombre, String correo, String eventos) throws EmpleadoException {
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
    public Usuario crearUsuario(String nombre, String id, String correoElectronico, List eventosAsiganados,String contrasenia) throws UsuarioException {
        return agencia.crearUsuario(nombre, id, correoElectronico, eventosAsiganados, contrasenia);
    }

    @Override
    public boolean eliminarUsuario(String id) throws UsuarioException {
        return agencia.eliminarUsuario(id);
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedulaActual, String correo,String contrasenia) throws UsuarioException {
        return agencia.actualizarUsuario(nombre, cedulaActual, correo, contrasenia);
    }

    @Override
    public boolean verificarUsuarioExistente(String cedula) throws UsuarioException {
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


    // EVENTOS


    @Override
    public Eventos crearEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        return null;
    }

    @Override
    public boolean eliminarEvento(String nombre) throws EventoException {
        return agencia.eliminarEvento(nombre);
    }

    @Override
    public boolean actualizarEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        return agencia.actualizarEvento(nombreEvento, descripcionEvento, fechaEvento, horaEvento, ubicacionEvento, capacidadMaximaEvento);
    }

    @Override
    public boolean verificarEventoExistente(String nombre) throws EventoException {
        return agencia.verificarEventoExistente(nombre);
    }

    @Override
    public Eventos obtenerEvento(String nombre) {
        return agencia.obtenerEvento(nombre);
    }

    @Override
    public ArrayList<Eventos> obtenerEventos() {
        return agencia.obtenerEventos();
    }

    public Usuario buscarUsuario(String id){
        return agencia.buscarUsuario(id);
    }



    @Override
    public List<Usuario> obtenerUsuarioId(String id) {
        return agencia.obtenerUsuarioId(id);
    }


    @Override
    public boolean validarUsuarioProperties(String usuario, String contrasena) {

        return agencia.validarUsuarioProperties(usuario, contrasena);
    }


    //RESERVA
    @Override
    public void agregarReserva(String id, Usuario usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        agencia.agregarReserva( id,  usuario,  evento,  fechaSolicitud, estadoReserva);
    }

    @Override
    public ArrayList<Reserva> obtenerReservas() {

        return agencia.obtenerReservas();
    }

    public List<ReservaDto> obtenerReservasDto(){
        return agencia.obtenerReservasDto();
    }


    //METODOS ADICIONALES



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
