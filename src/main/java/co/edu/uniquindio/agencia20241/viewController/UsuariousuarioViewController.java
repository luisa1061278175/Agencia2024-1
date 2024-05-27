package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class UsuariousuarioViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();

    public static ObservableList<UsuarioDto> usuarioAutenticado = FXCollections.observableArrayList();

    private UsuarioDto usuarioSeleccionado;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private Button btnEliminarUsuario;

    @FXML
    private Button btnModificarUsuario;

    @FXML
    private Button btnRegresarUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> colCorreoUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> colIdentificacionUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> colNombreUsuario;

    @FXML
    private TableView<UsuarioDto> tabla;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtIdentificacionUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    void initialize() {
        initView();
        listenerSelection();
    }

    private void initView() {
        initDataBinding();

        tabla.getItems().clear();

        if (!usuarioAutenticado.isEmpty()) {
            tabla.getItems().addAll(usuarioAutenticado);
        }
    }

    private void initDataBinding() {
        colCorreoUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().correoElectronico()));
        colIdentificacionUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
        colNombreUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombre()));
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            txtNombreUsuario.setText(usuarioSeleccionado.nombre());
            txtCorreoUsuario.setText(usuarioSeleccionado.correoElectronico());
            txtIdentificacionUsuario.setText(usuarioSeleccionado.id());
        }
    }

    @FXML
    void nuevoUsuarioAction(ActionEvent event) {
        txtNombreUsuario.setText("Ingrese el nombre");
        txtIdentificacionUsuario.setText("Ingrese la identificación");
        txtCorreoUsuario.setText("Ingrese el correo");
    }

    @FXML
    void eliminarUsuarioAction(ActionEvent event) throws UsuarioException {
        eliminarUsuario();
    }

    @FXML
    void actualizarUsuarioAction(ActionEvent event) throws UsuarioException {
        actualizarUsuario();
    }

    private void eliminarUsuario() throws UsuarioException {
        boolean usuarioEliminado = false;
        if (usuarioSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar al usuario?")) {
                usuarioEliminado = modelFactoryController.eliminarUsuario(usuarioSeleccionado.id());
                if (usuarioEliminado) {
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tabla.getSelectionModel().clearSelection();
                    limpiarCamposUsuario();
                    mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Selecciona un usuario de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarUsuario() throws UsuarioException {
        boolean usuarioActualizado = false;
        UsuarioDto usuarioDto = construirUsuarioDto();
        if (usuarioSeleccionado != null) {
            if (datosValidos(usuarioDto)) {
                usuarioActualizado = modelFactoryController.actualizarUsuario(usuarioDto.nombre(), usuarioDto.id(), usuarioDto.correoElectronico(), usuarioDto.contrasenia());
                if (usuarioActualizado) {
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    listaUsuariosDto.add(usuarioDto);
                    tabla.refresh();
                    mostrarMensaje("Notificación usuario", "Usuario actualizado", "El usuario se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuario();
                } else {
                    mostrarMensaje("Notificación usuario", "Usuario no actualizado", "El usuario no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
            }
        }
    }

    private UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(
                txtCorreoUsuario.getText(),
                txtIdentificacionUsuario.getText(),
                txtNombreUsuario.getText(),
                "*****"
        );
    }

    private void limpiarCamposUsuario() {
        txtNombreUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtIdentificacionUsuario.setText("");
    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        String mensaje = "";
        if (usuarioDto.nombre() == null || usuarioDto.nombre().isEmpty()) {
            mensaje += "El nombre es inválido\n";
        }
        if (usuarioDto.id() == null || usuarioDto.id().isEmpty()) {
            mensaje += "La identificación es inválida\n";
        }
        if (usuarioDto.correoElectronico() == null || usuarioDto.correoElectronico().isEmpty()) {
            mensaje += "El correo es inválido\n";
        }

        if (mensaje.isEmpty()) {
            return true;
        } else {
            mostrarMensaje("Notificación usuario", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent() && action.get() == ButtonType.OK;
    }
}
