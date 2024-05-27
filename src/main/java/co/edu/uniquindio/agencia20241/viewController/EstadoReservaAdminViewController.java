package co.edu.uniquindio.agencia20241.viewController;


import co.edu.uniquindio.agencia20241.controller.ControllerManager;
import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.controller.service.ActionObserver;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstadoReservaAdminViewController implements ActionObserver {

    private ControllerManager controllerManager = ControllerManager.getInstance();
    private ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    @FXML
    private TableView<ReservaDto> tabla;
    @FXML
    private Button btnExportar;
    @FXML
    private Button btnRefrescar;

    @FXML
    private Button btnSalir;

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
        initDataBinding();
        obtenerReservas();
        tabla.setItems(listaReservasDto);

        btnExportar.setOnAction(this::exportarCSV);
    }

    private void obtenerReservas() {
        listaReservasDto.addAll(AgenciaMapper.INSTANCE.getReservasDto(modelFactoryController.obtenerReservas()));
    }


    private void initDataBinding() {

        colEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().estadoReserva()));
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().evento().getNombreEvento()));
        colIdReserva.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
        colFechaSolicitud.setCellValueFactory(cell -> new SimpleStringProperty(LocalDate.now().toString()));

        colIdUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().usuario()));
    }

    private void exportarCSV(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como archivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("ID Reserva,Nombre Evento,Estado Reserva,Fecha Solicitud,ID Usuario\n"); // Agregar encabezados
                for (ReservaDto reserva : tabla.getItems()) {
                    writer.write(reserva.id() + "," + reserva.evento().getNombreEvento() + "," +
                            reserva.estadoReserva() + "," + reserva.fechaSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," +
                            reserva.usuario() + "\n");
                }
                writer.close();
                System.out.println("Datos exportados a CSV correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
public void salir(){
        System.exit(0);
}
@FXML
void refrescar() {
    listaReservasDto.clear();
    listaReservasDto.addAll(modelFactoryController.obtenerReservasDto());
    tabla.setItems(listaReservasDto);
}
    @Override
    public void onActionPerformed() {
       // obtenerReservas();
      //  tabla.refresh();
    }
}
