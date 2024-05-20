package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.model.Reserva;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstadoReservaViewController {

    private ModelFactoryController modelFactoryController = new ModelFactoryController();
    private InicioSesionController inicioSesionController= new InicioSesionController();
    private ReservaViewController reservaViewController= new ReservaViewController();
    private Reserva reserva= new Reserva();

    @FXML
    private TableView<ReservaDto> tabla;

    @FXML
    private TableColumn<ReservaDto, String> colEstado;

    @FXML
    private TableColumn<ReservaDto, String> colNombreEvento;

    @FXML
    private TableColumn<ReservaDto, String> colIdUsuario;

    @FXML
    private TableColumn<ReservaDto, String> colIdReserva;

    @FXML
    private TableColumn<ReservaDto, String> colFechaSolicitud;

    @FXML
    void initialize() {
        initView();
    }

    private void initView() {
        initDataBinding();
        cargarReservasEnTabla();
    }

    private void initDataBinding() {
        colEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().estadoReserva()));
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().evento().getNombreEvento()));

        colIdReserva.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
        colFechaSolicitud.setCellValueFactory(cell -> {
            String fechaSolicitud = cell.getValue().fechaSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return new SimpleStringProperty(fechaSolicitud);
        });


        colIdUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().usuario().getId()));

    }

    private void cargarReservasEnTabla() {
        List<ReservaDto> reservas = modelFactoryController.obtenerReservasDto();
        ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList(reservas);
        tabla.setItems(listaReservasDto);
    }
}
