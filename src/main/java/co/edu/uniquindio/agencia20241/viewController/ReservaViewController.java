package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Eventos;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.UUID;

public class ReservaViewController extends Application {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    private EventoDto eventoSeleccionado;
    private InicioSesionController inicioSesionController = new InicioSesionController();

    @FXML
    private Button btnReservar;

    @FXML
    private TableColumn<EventoDto, String> colCantidadMaximaEventos;

    @FXML
    private TableColumn<EventoDto, String> colDescripcionEventos;

    @FXML
    private TableColumn<EventoDto, String> colFechaEventos;

    @FXML
    private TableColumn<EventoDto, String> colHoraEventos;

    @FXML
    private TableColumn<EventoDto, String> colUbicacionEventos;

    @FXML
    private TableView<EventoDto> tabla;

    @FXML
    private TextField txtCantidadReservas;

    @FXML
    void initialize() {
        initView();
    }

    @FXML
    private void initView() {
        initDataBinding();
        obtenerEventos();
        tabla.getItems().clear();
        tabla.setItems(listaEventosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCantidadMaximaEventos.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().capacidadMaximaEvento())));
        colDescripcionEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().descripcionEvento()));
        colFechaEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaEvento()));
        colHoraEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().horaEvento()));
        colUbicacionEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().ubicacionEvento()));
    }

    private void obtenerEventos() {
        listaEventosDto.clear();
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(modelFactoryController.obtenerEventos()));
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }

    @FXML
    private void reservar(ActionEvent event) throws EmpleadoException {
        crearReserva();
    }

    private void crearReserva() throws EmpleadoException {
        if (eventoSeleccionado == null) {
            mostrarMensaje("Error", "No se ha seleccionado ningún evento.", "Por favor, seleccione un evento para reservar.", Alert.AlertType.ERROR);
            return;
        }

        String cantidadReservasStr = txtCantidadReservas.getText();
        if (cantidadReservasStr.isEmpty()) {
            mostrarMensaje("Error", "Cantidad de reservas vacía.", "Por favor, ingrese la cantidad de reservas que desea realizar.", Alert.AlertType.ERROR);
            return;
        }

        int cantidadReservas;
        try {
            cantidadReservas = Integer.parseInt(cantidadReservasStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Cantidad de reservas inválida.", "Por favor, ingrese un número válido.", Alert.AlertType.ERROR);
            return;
        }

        if (cantidadReservas <= 0) {
            mostrarMensaje("Error", "Cantidad de reservas inválida.", "La cantidad de reservas debe ser mayor a cero.", Alert.AlertType.ERROR);
            return;
        }

        if (cantidadReservas > eventoSeleccionado.capacidadMaximaEvento()) {
            mostrarMensaje("Error", "No hay suficientes reservas disponibles.", "La cantidad de reservas disponibles es: " + eventoSeleccionado.capacidadMaximaEvento(), Alert.AlertType.ERROR);
            return;
        }

        // Crear reserva
        String idReserva = UUID.randomUUID().toString();
        String usuarioId = inicioSesionController.idUsuarioAutenticado;

        if (usuarioId == null) {
            mostrarMensaje("Error", "Usuario no autenticado.", "Por favor, inicie sesión para realizar una reserva.", Alert.AlertType.ERROR);
            return;
        }

        // Se debe mapear para poder enviar un evento de tipo Evento y no de tipo EventoDto
        Eventos evento = AgenciaMapper.INSTANCE.eventoDtoToEvento(eventoSeleccionado);

        modelFactoryController.agregarReserva(idReserva, modelFactoryController.obtenerUsuario(usuarioId), evento, LocalDate.now(), mensajeReserva(eventoSeleccionado.capacidadMaximaEvento(), cantidadReservas));

        // Eliminar el evento antiguo
        modelFactoryController.eliminarEvento(evento.getNombreEvento());

        // Crear un nuevo evento con la capacidad actualizada
        evento.setCapacidadMaximaEvento(evento.getCapacidadMaximaEvento() - cantidadReservas);
        modelFactoryController.crearEvento(evento.getNombreEvento(), evento.getDescripcionEvento(), evento.getFechaEvento(), evento.getHoraEvento(), evento.getUbicacionEvento(), evento.getCapacidadMaximaEvento());

        // Actualizar la tabla y limpiar el campo de texto
        actualizarListaEventosConNuevoEvento(evento);
        txtCantidadReservas.clear();

        mostrarMensaje("Éxito", "Reserva realizada.", "Su reserva ha sido realizada con éxito.", Alert.AlertType.INFORMATION);
    }

    private void actualizarListaEventosConNuevoEvento(Eventos evento) {
        // Mapear el nuevo evento a EventoDto
        EventoDto nuevoEventoDto = AgenciaMapper.INSTANCE.eventoToEventoDto(evento);
        // Remover el evento antiguo de la lista
        listaEventosDto.removeIf(eventoDto -> eventoDto.nombreEvento().equals(nuevoEventoDto.nombreEvento()));
        // Agregar el nuevo evento a la lista
        listaEventosDto.add(nuevoEventoDto);
        // Refrescar la tabla
        tabla.refresh();
    }

    private String mensajeReserva(int capacidadMaxima, int cantidadReservas) {
        return cantidadReservas == 1 ? "Se ha realizado 1 reserva." : "Se han realizado " + cantidadReservas + " reservas.";
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Código para iniciar la aplicación JavaFX, si es necesario
    }
}
