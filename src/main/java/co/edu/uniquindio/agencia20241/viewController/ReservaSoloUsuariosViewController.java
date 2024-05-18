package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.reservasevento.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservaSoloUsuariosViewController extends Application {
    @FXML
    private Button btnRegresarEventos;

    @FXML
    private TableColumn<?, ?> colCantidadMaximaEventos;

    @FXML
    private TableColumn<?, ?> colDescripcionEventos;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colFechaEventos;

    @FXML
    private TableColumn<?, ?> colHoraEventos;

    @FXML
    private TableColumn<?, ?> colUbicacionEventos;

    @FXML
    private TableView<?> tabla;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ReservaSoloUsuarios.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setWidth(700);
        stage.setHeight(550);
        stage.setScene(scene);
        URL url = getClass().getResource("\\src\\main\\resources\\co\\edu\\uniquindio\\reservasevento");
        stage.setTitle("");
        stage.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
