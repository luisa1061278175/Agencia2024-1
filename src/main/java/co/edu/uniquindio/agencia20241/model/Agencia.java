package co.edu.uniquindio.agencia20241.model;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;

import java.util.ArrayList;

public class Agencia implements IAgenciaService {
    private static final long serialVersionUID = 1L;
    ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Reserva> listaReservas = new ArrayList<>();
    ArrayList<Eventos> listaEventos = new ArrayList<>();

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

    public void agregarEmpleado(Empleado nuevoEmpleado) throws EmpleadoException{
        getListaEmpleados().add(nuevoEmpleado);
    }

    @Override
    public boolean actualizarEmpleado(String cedulaActual, Empleado empleado) throws EmpleadoException {
        Empleado empleadoActual = obtenerEmpleado(cedulaActual);
        if(empleadoActual == null)
            throw new EmpleadoException("El empleado a actualizar no existe");
        else{
            empleadoActual.setNombre(empleado.getNombre());
            empleadoActual.setId(empleado.getId());
            empleadoActual.setEventosAsiganados(empleado.getEventosAsiganados());
            empleadoActual.setCorreoElectronico(empleado.getCorreoElectronico());

            return true;
        }
    }

    @Override
    public Empleado crearEmpleado(String nombre, String id, String correoElectronico, String eventosAsiganados, ArrayList<Empleado> listaEmpleados) throws EmpleadoException {
        Empleado nuevoEmpleado = null;
        boolean empleadoExiste = verificarEmpleadoExistente(id);
        if(empleadoExiste){
            throw new EmpleadoException("El empleado con cedula: "+id+" ya existe");
        }else{
            nuevoEmpleado = new Empleado();
            nuevoEmpleado.setNombre(nombre);
            nuevoEmpleado.setId(id);

            getListaEmpleados().add(nuevoEmpleado);
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
            getListaEmpleados().remove(empleado);
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
        for (Empleado empleado : getListaEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(cedula)){
                empleadoEncontrado = empleado;
                break;
            }
        }
        return empleadoEncontrado;
    }

    @Override
    public ArrayList<Empleado> obtenerEmpleados() {
        // TODO Auto-generated method stub
        return getListaEmpleados();
    }

    public boolean empleadoExiste(String cedula) {
        boolean empleadoEncontrado = false;
        for (Empleado empleado : getListaEmpleados()) {
            if(empleado.getId().equalsIgnoreCase(cedula)){
                empleadoEncontrado = true;
                break;
            }
        }
        return empleadoEncontrado;
    }
}
