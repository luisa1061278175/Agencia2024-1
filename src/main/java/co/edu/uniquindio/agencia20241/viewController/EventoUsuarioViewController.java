package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class EventoUsuarioViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    private EventoDto eventoSeleccionado;

    @FXML
    private Button btnReservar;

    @FXML
    private TableColumn<EventoDto, String> colCantidadMaximaEvento;

    @FXML
    private TableColumn<EventoDto, String> colDescripcionEvento;

    @FXML
    private TableColumn<EventoDto, String> colFechaEvento;

    @FXML
    private TableColumn<EventoDto, String> colHoraEvento;

    @FXML
    private TableColumn<EventoDto, String> colNombreEvento;

    @FXML
    private TableColumn<EventoDto, String> colReservas;

    @FXML
    private TableColumn<EventoDto, String> colUbicacionEvento;

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
        initDataBindig();
        obtenerEventos();
        tabla.getItems().clear();
        tabla.setItems(listaEventosDto);
        listenerSelection();
    }

    private void initDataBindig() {
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombreEvento()));
        colDescripcionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().descripcionEvento()));
        colFechaEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaEvento().toString()));
        colHoraEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().horaEvento().toString()));
        colUbicacionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().ubicacionEvento()));
        colCantidadMaximaEvento.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().capacidadMaximaEvento())));

    }

    private void obtenerEventos() {
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(modelFactoryController.obtenerEventos()));
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }
    public void reservar(){
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

        int reservasDisponibles = eventoSeleccionado.capacidadMaximaEvento() - cantidadReservas;
        if (cantidadReservas > reservasDisponibles) {
            mostrarMensaje("Error", "No hay suficientes reservas disponibles.", "La cantidad de reservas disponibles es: " + reservasDisponibles, Alert.AlertType.ERROR);
            return;
        }

    }

    @FXML
    void reservar(ActionEvent event) {
        reservar();

//
//        // Crear y guardar la reserva
//        String usuarioId = "usuarioEjemplo"; // Obtener el ID del usuario autenticado
//        Reserva nuevaReserva = new Reserva(usuarioId, eventoSeleccionado, cantidadReservas);
//        modelFactoryController.agregarReserva(nuevaReserva);
//
//        // Actualizar las reservas del evento
//        eventoSeleccionado.setReservas(eventoSeleccionado.reservas() + cantidadReservas);
//        tabla.refresh();
//
//        mostrarMensaje("Reserva Exitosa", "Reserva realizada con éxito.", "Se han reservado " + cantidadReservas + " espacios para el evento " + eventoSeleccionado.nombreEvento() + ".", Alert.AlertType.INFORMATION);
//    }
    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
