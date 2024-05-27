package co.edu.uniquindio.agencia20241.controller;

import co.edu.uniquindio.agencia20241.controller.service.IAgenciaService;
import co.edu.uniquindio.agencia20241.controller.service.IModelFactoryService;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.exception.EventoException;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.*;
import co.edu.uniquindio.agencia20241.utils.AgenciaUtils;
import co.edu.uniquindio.agencia20241.utils.Persistencia;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static co.edu.uniquindio.agencia20241.utils.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ModelFactoryController implements IAgenciaService, Runnable, IModelFactoryService {
    Agencia agencia;
    AgenciaMapper mapper = AgenciaMapper.INSTANCE;
    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    private ControllerManager controllerManager = ControllerManager.getInstance();

    BoundedSemaphore semaphore = new BoundedSemaphore(1);
    String mensaje = "";
    int nivel = 0;
    String accion = "";

    Thread hilo1GuardarXml;
    Thread hilo2GuardarLog;

    // Para utilizar RabbitMQ
    private Thread hiloServicioConsumer1;

    private static final String QUEUE_RESERVA = "reservas";
    private static final String QUEUE_RESULTADO = "resultados_reservas";

    //------------------------------  Singleton ------------------------------------------------
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {

        System.out.println("Invocación clase singleton");
        initRabbitConnection();
        System.out.println("RabbitMQ iniciado");
        consumirSolicitudesReserva();
        cargarResourceXML();
        System.out.println("Agencia cargada: " + (agencia != null));

        if (agencia == null) {
            cargarDatosBase();
            System.out.println("Datos base cargados");
            guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("Conexión establecida");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NUEVA_PUBLICACION, false, false, false, null);
            channel.queueDeclare(QUEUE_RESULTADO, false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consumirMensajesServicio1() {
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }

    @Override
    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarResourceXML() {
        hilo1GuardarXml = new Thread(this);
        hilo1GuardarXml.start();
    }

    public void cargarResourceXML() {
        agencia = Persistencia.cargarRecursoAgenciaXML();
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

    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try {
            if (!verificarEmpleadoExistente(empleadoDto.id())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getAgencia().agregarEmpleado(empleado);
                registrarAccionesSistema("Se agregó el empleado " + empleado.getNombre(), 1, "agregarEmpleado");
                guardarResourceXML();
            }
            return true;
        } catch (EmpleadoException e) {
            e.getMessage();
            return false;
        }
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

    // USUARIO

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try {
            if (!verificarUsuarioExistente(usuarioDto.id())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                getAgencia().agregarUsuario(usuario);
                registrarAccionesSistema("Se agregó el usuario " + usuario.getNombre(), 1, "agregarEmpleado");
                guardarResourceXML();
            }
            return true;
        } catch (UsuarioException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public Usuario crearUsuario(String nombre, String id, String correoElectronico, List eventosAsiganados, String contrasenia) throws UsuarioException {
        return agencia.crearUsuario(nombre, id, correoElectronico, eventosAsiganados, contrasenia);
    }

    @Override
    public boolean eliminarUsuario(String id) throws UsuarioException {
        return agencia.eliminarUsuario(id);
    }

    @Override
    public boolean actualizarUsuario(String nombre, String cedulaActual, String correo, String contrasenia) throws UsuarioException {
        return agencia.actualizarUsuario(nombre, cedulaActual, correo, contrasenia);
    }

    public ObservableList<UsuarioDto> obtenerUsuariosDto() {
        List<Usuario> usuarios = agencia.obtenerUsuarios();
        List<UsuarioDto> usuariosDto = mapper.usuariosToUsuariosDto(usuarios);
        return FXCollections.observableArrayList(usuariosDto);
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

    public boolean agregarEventos(EventoDto eventoDto) {
        try {
            if (!verificarEventoExistente(eventoDto.nombreEvento())) {
                Eventos eventos = mapper.eventoDtoToEvento(eventoDto);
                getAgencia().agregarEvento(eventos);
                registrarAccionesSistema("Se agregó el evento " + eventoDto.nombreEvento(), 1, "agregarEmpleado");
                guardarResourceXML();
            }
            return true;
        } catch (EmpleadoException e) {
            e.getMessage();
            return false;
        } catch (EventoException e) {
            throw new RuntimeException(e);
        }
    }

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

    public Usuario buscarUsuario(String id) {
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

    // RESERVA

    public boolean agregarReserva(ReservaDto reservaDto) {
        try {
            Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
            getAgencia().agregarReservas(reserva);
            registrarAccionesSistema("Se agregó una reserva para: " + reserva.getEvento(), 1, "agregarReserva");
            guardarResourceXML();
            return true;
        } catch (EmpleadoException e) {
            e.getMessage();
            return false;
        }
    }

    public ObservableList<ReservaDto> obtenerReservasDto() {
        List<Reserva> reservas = agencia.obtenerReservas();
        List<ReservaDto> reservaDtos = mapper.reservasToReservasDto(reservas);
        return FXCollections.observableArrayList(reservaDtos);
    }

    @Override
    public void agregarReserva(String id, String usuario, Eventos evento, LocalDate fechaSolicitud, String estadoReserva) {
        agencia.agregarReserva(id, usuario, evento, fechaSolicitud, estadoReserva);
    }

    @Override
    public ArrayList<Reserva> obtenerReservas() {
        return agencia.obtenerReservas();
    }

    // METODOS ADICIONALES

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        this.mensaje = mensaje;
        this.nivel = nivel;
        this.accion = accion;
        hilo2GuardarLog = new Thread(this);
        hilo2GuardarLog.start();
    }

    @Override

    public void run() {
        try {
            Thread hiloActual = Thread.currentThread();
            ocupar();

            if (hiloActual == hilo1GuardarXml) {
                Persistencia.guardarRecursoAgenciaXML(agencia);
                liberar();
            } else if (hiloActual == hilo2GuardarLog) {
                Persistencia.guardaRegistroLog(mensaje, nivel, accion);
                liberar();
            } else if (hiloActual == hiloServicioConsumer1) {
                try (Connection connection = connectionFactory.newConnection();
                     Channel channel = connection.createChannel()) {
                    channel.basicConsume(QUEUE_RESERVA, true, (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                        System.out.println("Operación recibida del cliente: " + message);

                        String[] parts = message.split(";");
                        String cantidadReservas = parts[0];
                        String nombreEvento = parts[1];

                        boolean resultado = procesarOperacion(cantidadReservas, nombreEvento);
                        enviarResultado(resultado);
                    }, consumerTag -> {});
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarSolicitudReserva(String cantidadReservas, String nombreEvento) {
        try {
            String message = cantidadReservas + ";" + nombreEvento;
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_RESERVA, false, false, false, null);
            channel.basicPublish("", QUEUE_RESERVA, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consumirSolicitudesReserva() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_RESERVA, false, false, false, null);
            channel.queueDeclare(QUEUE_RESULTADO, false, false, false, null);
            channel.basicConsume(QUEUE_RESERVA, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                String[] parts = message.split(";");
                String cantidadReservas = parts[0];
                String nombreEvento = parts[1];
                boolean resultado = procesarOperacion(cantidadReservas, nombreEvento);
                enviarResultado(resultado);
            }, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean procesarOperacion(String cantidadReservasStr, String nombreEvento) {
        try {
            int cantidadReservas = Integer.parseInt(cantidadReservasStr);
            Eventos evento = this.obtenerEvento(nombreEvento);

            if (evento == null) {
                System.out.println("Evento no encontrado: " + nombreEvento);
                return false;
            }

            if (evento.getCapacidadMaximaEvento() < cantidadReservas) {
                System.out.println("No hay suficientes plazas disponibles para el evento: " + nombreEvento);
                return false;
            }


            evento.setCapacidadMaximaEvento(evento.getCapacidadMaximaEvento() - cantidadReservas);

            System.out.println("Reserva creada con éxito para el evento: " + nombreEvento);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Cantidad de reservas inválida: " + cantidadReservasStr);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    private void enviarResultado(boolean resultado) {
        try {
            String res = resultado ? "Aceptado" : "Rechazado";
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicPublish("", QUEUE_RESULTADO, null, res.getBytes(StandardCharsets.UTF_8));
            System.out.println("Resultado enviado al cliente: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ocupar() {
        try {
            semaphore.ocupar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void liberar() {
        try {
            semaphore.liberar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
