package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.EmpleadoException;
import co.edu.uniquindio.agencia20241.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class EmpleadoViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EmpleadoDto>listaEmpleadosDto= FXCollections.observableArrayList();
    private EmpleadoDto empleadoSeleccionado;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<EmpleadoDto, String> colCorreoEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colIdentificacionEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colNombreEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colRolEmpleado;

    @FXML
    private TableView<EmpleadoDto> tabla;

    @FXML
    private TextField txtCorreoEmpleados;

    @FXML
    private TextField txtIdentificacionEmpleados;

    @FXML
    private TextField txtNombreEmpleados;

    @FXML
    private TextField txtRolEmpleados;

    @FXML
    void initialize() {
        initView();
    }
 @FXML
    private void initView(){

     initDataBindig();
     obtenerEmpleado();
     tabla.getItems().clear();
     tabla.setItems(listaEmpleadosDto);
     listenerSelection();

 }
 private void initDataBindig(){

     colCorreoEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().correoElectronico()));
     colIdentificacionEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
     colNombreEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombre()));
     colRolEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().eventosAsiganados()));



 }

    private void obtenerEmpleado() {
        listaEmpleadosDto.addAll(AgenciaMapper.INSTANCE.getEmpleadosDto(  modelFactoryController.obtenerEmpleados()) );
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
            mostrarInformacionEmpleado(empleadoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(EmpleadoDto empleadoSeleccionado) {
        if(empleadoSeleccionado != null){
            txtNombreEmpleados.setText(empleadoSeleccionado.nombre());
            txtRolEmpleados.setText(empleadoSeleccionado.eventosAsiganados());
            txtIdentificacionEmpleados.setText(empleadoSeleccionado.id());
            txtCorreoEmpleados.setText(empleadoSeleccionado.correoElectronico());

        }
    }

    @FXML
    void nuevoEmpleadoAction(ActionEvent event) {
        txtNombreEmpleados.setText("Ingrese el nombre");
        txtIdentificacionEmpleados.setText("Ingrese la identificación");
        txtCorreoEmpleados.setText("Ingrese el correo");
        txtRolEmpleados.setText("Ingrese el rol");

    }

    @FXML
    void agregarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        crearEmpleado();
    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        eliminarEmpleado();
    }

    @FXML
    void actualizarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        actualizarEmpleado();
    }

    private void crearEmpleado() throws EmpleadoException {
        //1. Capturar los datos
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. Validar la información
        if(datosValidos(empleadoDto)){
            if(modelFactoryController.crearEmpleado(empleadoDto.nombre(), empleadoDto.id(), empleadoDto.correoElectronico(), empleadoDto.eventosAsiganados()) != null){
                listaEmpleadosDto.add(empleadoDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEmpleado();
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarEmpleado() throws EmpleadoException {
        boolean empleadoEliminado = false;
        if(empleadoSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al empleado?")){
                empleadoEliminado = modelFactoryController.eliminarEmpleado(empleadoSeleccionado.id());
                if(empleadoEliminado == true){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    empleadoSeleccionado = null;
                    tabla.getSelectionModel().clearSelection();
                    limpiarCamposEmpleado();
                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarEmpleado() throws EmpleadoException {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String cedulaActual = empleadoSeleccionado.id();
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. verificar el empleado seleccionado
        if(empleadoSeleccionado != null){
            //3. Validar la información
            if(datosValidos(empleadoSeleccionado)){
                clienteActualizado = modelFactoryController.actualizarEmpleado(cedulaActual,empleadoDto.nombre(), empleadoDto.correoElectronico(), empleadoDto.eventosAsiganados());
                if(clienteActualizado){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    listaEmpleadosDto.add(empleadoDto);
                    tabla.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposEmpleado();
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private EmpleadoDto construirEmpleadoDto() {

        return new EmpleadoDto(
                txtNombreEmpleados.getText(),
                txtIdentificacionEmpleados.getText(),
                txtCorreoEmpleados.getText(),
                txtRolEmpleados.getText()
        );
    }

    private void limpiarCamposEmpleado() {
        txtNombreEmpleados.setText("");
        txtCorreoEmpleados.setText("");
        txtIdentificacionEmpleados.setText("");
        txtRolEmpleados.setText("");

    }

    private boolean datosValidos(EmpleadoDto empleadoDto) {
        String mensaje = "";
        if(empleadoDto.nombre() == null || empleadoDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(empleadoDto.id() == null || empleadoDto.id() .equals(""))
            mensaje += "El documento es invalido \n" ;
        if(empleadoDto.correoElectronico() == null || empleadoDto.correoElectronico().equals(""))
            mensaje += "El correo es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}