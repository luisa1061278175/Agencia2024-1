package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.Login;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(co.edu.uniquindio.agencia20241.utils.Utils.class.getName());

    public static Login leerArchvos(){
        ResourceBundle resourceBundle;
        resourceBundle = ResourceBundle.getBundle("usuario");//nombre del archivo properties
        String usuario=resourceBundle.getString("usuario");
        String contrasena=resourceBundle.getString("contrasena");

        return new Login(usuario, contrasena);
    }
}
