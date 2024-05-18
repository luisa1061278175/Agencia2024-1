package co.edu.uniquindio.agencia20241.model;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import co.edu.uniquindio.agencia20241.utils.Utils;
import co.edu.uniquindio.agencia20241.viewController.UsuariousuarioViewController;

import java.util.ArrayList;
import java.util.List;

public class Agencia implements IAgenciaService {
    private static final long serialVersionUID = 1L;
    ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Reserva> listaReservas = new ArrayList<>();
    ArrayList<Eventos> listaEventos = new ArrayList<>();
    private UsuariousuarioViewController usuariousuarioViewController= new UsuariousuarioViewController();


    public Usuario idUsuarioAutenticado;

    public Agencia() {

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

    @Override
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




    @Override
    public Usuario crearUsuario(String nombre, String id, String correoElectronico, String reservasRealizadas) throws EmpleadoException {
        Usuario usuario = null;
        boolean usuarioExiste = verificarUsuarioExistente(id);
        if(usuarioExiste){
            throw new EmpleadoException("El Usuario con cedula: "+id+" ya existe");
        }else{
            usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setId(id);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setReservasRealizadas(reservasRealizadas);

            obtenerUsuarios().add(usuario);
        }
        return usuario;
    }

    @Override
    public boolean eliminarUsuario(String id) throws EmpleadoException {
        Usuario usuario = null;
        boolean flagExiste = false;
        usuario = obtenerUsuario(id);
        if(usuario == null)
            throw new EmpleadoException("El empleado a eliminar no existe");
        else{
            obtenerUsuarios().remove(usuario);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedulaActual, String correo) throws EmpleadoException {
        Usuario usuario = obtenerUsuario(cedulaActual);
        if(usuario == null) {
            throw new EmpleadoException("El usuario a actualizar no existe");
        } else {
            // Realiza la actualización del usuario
            usuario.setNombre(nombre);
            usuario.setId(cedulaActual);
            usuario.setCorreoElectronico(correo);
            return true;
        }
    }


    @Override
    public boolean verificarUsuarioExistente(String cedula) throws EmpleadoException {
        if(UsuarioExiste(cedula)){
            throw new EmpleadoException("El Usuario con cedula: "+cedula+" ya existe");
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




    public Eventos crearEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EmpleadoException {
        Eventos nuevoEvento = null;
        boolean eventoExiste = verificarEventoExistente(nombreEvento);
        if(eventoExiste){
            throw new EmpleadoException("El evento ya existe");
        } else {
            nuevoEvento = new Eventos(nombreEvento, descripcionEvento, fechaEvento, horaEvento, ubicacionEvento, capacidadMaximaEvento);
            obtenerEventos().add(nuevoEvento);
        }
        return nuevoEvento;
    }

    @Override
    public boolean eliminarEvento(String nombre) throws EmpleadoException {
        Eventos evento = null;
        boolean flagExiste = false;
        evento = obtenerEvento(nombre);
        if(evento == null)
            throw new EmpleadoException("El evento a eliminar no existe");
        else{
            obtenerEventos().remove(evento);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEvento(String nombreEvento, String descripcionEvento, String fechaEvento, String horaEvento, String ubicacionEvento, int capacidadMaximaEvento) throws EmpleadoException {
        Eventos evento = obtenerEvento(nombreEvento);
        if(evento == null)
            throw new EmpleadoException("El evento a actualizar no existe");
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
    public boolean verificarEventoExistente(String nombre) throws EmpleadoException {
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
        Login datosArchivo = Utils.leerArchvos();

        if( datosArchivo.getUsername().equals(usuario) && datosArchivo.getContrasena().equals(contrasena) ){
            return true;
        }else {
            return false;
        }}


//reservas

    public Usuario buscarUsuario(String id) {
        for (Usuario u :obtenerUsuarios()) {
            if (u.getId().equals(id)) {
                UsuarioDto usuarioDto = new UsuarioDto(u.getId(), u.getNombre(), u.getCorreoElectronico());
                // Agregar el UsuarioDto al ObservableList
                usuariousuarioViewController.usuarioAutenticado.add(usuarioDto);
                System.out.println(""+usuariousuarioViewController.usuarioAutenticado);
                return u;
            }
        }
        return null;
    }

    @Override
public void agregarReserva(Reserva reserva) {
    listaReservas.add(reserva);
}
@Override
    public ArrayList<Reserva> obtenerReservas() {
        return listaReservas;
    }


}
