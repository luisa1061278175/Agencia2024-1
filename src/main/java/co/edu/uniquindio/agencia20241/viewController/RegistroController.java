package co.edu.uniquindio.agencia20241.viewController;


import co.edu.uniquindio.agencia20241.HelloApplication;
import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.UsuarioDto;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;


public class RegistroController {
    private final ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();


    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField txtContraseña;

    @FXML
    private TextField txtContraseñaVerificacion;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {

    }

    @FXML
    void guardar(ActionEvent event) throws IOException, UsuarioException {
        Stage stage = new Stage();
        String nombre = txtNombre.getText();
        String contrasenia = txtContraseña.getText();
        String correo = txtCorreo.getText();
        String id = txtId.getText();

        UsuarioDto usuarioDto= new UsuarioDto(id,nombre,correo,contrasenia);

        modelFactoryController.agregarUsuario(usuarioDto);

        cargarInicioSesion(stage);
    }

    @FXML
    void regresar(ActionEvent event) {

        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }



    public void cargarInicioSesion(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("INICIAR SESIÓN");
        stage.setScene(scene);
        stage.show();
    }

}

