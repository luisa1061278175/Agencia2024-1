package co.edu.uniquindio.agencia20241.model;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.exception.*;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.utils.ArchivoUtil;
import co.edu.uniquindio.agencia20241.viewController.UsuariousuarioViewController;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Agencia implements IAgenciaService, Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Reserva> listaReservas = new ArrayList<>();
    ArrayList<Eventos> listaEventos = new ArrayList<>();
    private UsuariousuarioViewController usuariousuarioViewController= new UsuariousuarioViewController();


    public Usuario idUsuarioAutenticado;

    public Agencia() {

    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public ArrayList<Eventos> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @Override

    public boolean actualizarEmpleado(String cedulaActual, String nombre, String correo, String eventos) throws EmpleadoException {
        Empleado empleadoActual = obtenerEmpleado(cedulaActual);
        if(empleadoActual == null)
            throw new EmpleadoException("El empleado a actualizar no existe");
        else{
            empleadoActual.setNombre(nombre);
            empleadoActual.setId(cedulaActual);
            empleadoActual.setEventosAsiganados(eventos);
            empleadoActual.setCorreoElectronico(correo);

            return true;
        }
    }


    public Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados) throws EmpleadoException {
        Empleado nuevoEmpleado = null;
        boolean empleadoExiste = verificarEmpleadoExistente(id);
        if(empleadoExiste){
            throw new EmpleadoException("El empleado con cedula: "+id+" ya existe");
        }else{
            nuevoEmpleado = new Empleado();
            nuevoEmpleado.setNombre(nombre);
            nuevoEmpleado.setId(id);
            nuevoEmpleado.setCorreoElectronico(correoElectronico);
            nuevoEmpleado.setEventosAsiganados(eventosAsiganados);

            obtenerEmpleados().add(nuevoEmpleado);
        }
        return nuevoEmpleado;
    }

    public void agregarEmpleado(Empleado nuevoEmpleado) throws EmpleadoException{
        getListaEmpleados().add(nuevoEmpleado);
    }



    @Override
    public boolean eliminarEmpleado(String cedula) throws EmpleadoException {
        Empleado empleado = null;
        boolean flagExiste = false;
        empleado = obtenerEmpleado(cedula);
        if(empleado == null)
            throw new EmpleadoException("El empleado a eliminar no existe");
        else{
            obtenerEmpleados().remove(empleado);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean verificarEmpleadoExistente(String cedula) throws EmpleadoException {
        if(empleadoExiste(cedula)){
            throw new EmpleadoException("El empleado con cedula: "+cedula+" ya existe");
        }else{
            return false;
        }
    }


    @Override
    public Empleado obtenerEmpleado(String cedula) {
        Empleado empleadoEncontrado = null;
        for (Empleado empleado : obtenerEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(cedula)){
                empleadoEncontrado = empleado;
                break;
            }
        }
        return empleadoEncontrado;
    }

    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
        return listaEmpleados;
    }


    public boolean empleadoExiste(String cedula) {
        boolean empleadoEncontrado = false;
        for (Empleado empleado : obtenerEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(cedula)){
                empleadoEncontrado = true;
                break;
            }
        }
        return empleadoEncontrado;
    }


//Metodos del usuario






    public Usuario crearUsuario(String nombre, String id, String correoElectronico, List reservasRealizadas, String contrasenia) throws UsuarioException {
        boolean usuarioExiste = verificarUsuarioExistente(id);
        if (usuarioExiste) {
            throw new UsuarioException("El Usuario con cédula: " + id + " ya existe");
        } else {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setId(id);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setListaReservas(reservasRealizadas);
            usuario.setContrasenia(contrasenia);
            obtenerUsuarios().add(usuario);
            return usuario;
        }
    }
    public void agregarUsuario(Usuario nuevoUsuario) {
        getListaUsuarios().add(nuevoUsuario);
    }

    @Override
    public boolean eliminarUsuario(String id) throws UsuarioException {
        Usuario usuario = obtenerUsuario(id);
        if (usuario == null) {
            throw new UsuarioException("El usuario a eliminar no existe");
        } else {
            obtenerUsuarios().remove(usuario);
            return true;
        }
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedulaActual, String correo,String contrasenia) throws UsuarioException {
        Usuario usuario = obtenerUsuario(cedulaActual);
        if(usuario == null) {
            System.out.println("lista Usuarios: "+usuario);
            throw new UsuarioException("El usuario a actualizar no existe");
        } else {

            usuario.setNombre(nombre);
            usuario.setId(cedulaActual);
            usuario.setCorreoElectronico(correo);
            usuario.setContrasenia(contrasenia);
            return true;
        }
    }


    @Override
    public boolean verificarUsuarioExistente(String cedula) throws UsuarioException {
        if(UsuarioExiste(cedula)){
            throw new UsuarioException("El Usuario con cedula: "+cedula+" ya existe");
        }else{
            return false;
        }
    }


    public boolean UsuarioExiste(String cedula) {
        boolean usuarioEncontrado = false;
        for (Usuario usuario : obtenerUsuarios()) {
            if(usuario.getId().equalsIgnoreCase(cedula)){
                usuarioEncontrado = true;
                break;
            }
        }
        return usuarioEncontrado;
    }


    @Override
    public Usuario obtenerUsuario(String cedula) {
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : obtenerUsuarios()) {
            if(usuario.getId().equalsIgnoreCase(cedula)){
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }

//para imprimir el usuario en la tabla





    @Override
    public ArrayList<Usuario> obtenerUsuarios() {

        return listaUsuarios;
    }

    //EVENTOS



@Override
    public Eventos crearEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        Eventos nuevoEvento = null;
        boolean eventoExiste = verificarEventoExistente(nombreEvento);
        if(eventoExiste){
            throw new EventoException("El evento ya existe");
        } else {
            nuevoEvento = new Eventos(nombreEvento, descripcionEvento, fechaEvento, horaEvento, ubicacionEvento, capacidadMaximaEvento,null);
            obtenerEventos().add(nuevoEvento);
        }
        return nuevoEvento;
    }

    public void agregarEvento(Eventos nuevoEvento) throws EmpleadoException{
        getListaEventos().add(nuevoEvento);
    }

    @Override
    public boolean eliminarEvento(String nombre) throws EventoException {
        Eventos evento = null;
        boolean flagExiste = false;
        evento = obtenerEvento(nombre);
        if(evento == null)
            throw new EventoException("El evento a eliminar no existe");
        else{
            obtenerEventos().remove(evento);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EventoException {
        Eventos evento = obtenerEvento(nombreEvento);
        if(evento == null)
            throw new EventoException("El evento a actualizar no existe");
        else{
            evento.setDescripcionEvento(descripcionEvento);
            evento.setFechaEvento(fechaEvento);
            evento.setHoraEvento(horaEvento);
            evento.setUbicacionEvento(ubicacionEvento);
            evento.setCapacidadMaximaEvento(capacidadMaximaEvento);
            return true;
        }
    }

    @Override
    public boolean verificarEventoExistente(String nombre) throws EventoException {
        for (Eventos evento : obtenerEventos()) {
            if(evento.getNombreEvento().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Eventos obtenerEvento(String nombre) {
        for (Eventos evento : obtenerEventos()) {
            if(evento.getNombreEvento().equalsIgnoreCase(nombre)){
                return evento;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Eventos> obtenerEventos() {
        return listaEventos;
    }


    @Override

    public List<Usuario> obtenerUsuarioId(String id) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();

        for (Usuario u : obtenerUsuarios()) {
            if (u.getId().equals(id)) {
                usuariosEncontrados.add(u);
            }
        }

        return usuariosEncontrados;
    }

    //validar datos para archivo properties
    @Override

    public boolean validarUsuarioProperties(String usuario, String contrasena){
        Login datosArchivo = ArchivoUtil.leerArchivos();

        if( datosArchivo.getUsername().equals(usuario) && datosArchivo.getContrasena().equals(contrasena) ){
            return true;
        }else {
            return false;
        }}

    public Usuario buscarUsuario(String id) {
        for (Usuario u :obtenerUsuarios()) {
            if (u.getId().equals(id)) {
                UsuarioDto usuarioDto = new UsuarioDto(u.getId(), u.getNombre(), u.getCorreoElectronico(),u.getContrasenia());
                // Agregar el UsuarioDto al ObservableList
                usuariousuarioViewController.usuarioAutenticado.add(usuarioDto);
                System.out.println(""+usuariousuarioViewController.usuarioAutenticado);
                return u;
            }
        }
        return null;
    }




//reservas

    public void agregarReservas(Reserva nuevaReserva) throws EmpleadoException{
        getListaReservas().add(nuevaReserva);
        System.out.println("reserva creada (agencia)"+nuevaReserva);
    }

    @Override
public void agregarReserva(String id, String usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        Reserva nuevaReserva = null;

            nuevaReserva = new Reserva(id, usuario, evento, fechaSolicitud, estadoReserva);

            obtenerReservas().add(nuevaReserva);

    }




@Override
    public ArrayList<Reserva> obtenerReservas() {
        return listaReservas;
    }


    public List<ReservaDto> obtenerReservasDto() {
        List<Reserva> reservas = obtenerReservas();

        // Convertir las reservas a ReservaDto utilizando el mapper AgenciaMapper
        return reservas.stream()
                .map(AgenciaMapper.INSTANCE::reservasToReservasDto)
                .collect(Collectors.toList());
    }


}
