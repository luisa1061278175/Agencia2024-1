package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.Login;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtil {
    private static final Logger LOGGER = Logger.getLogger(ArchivoUtil.class.getName());

    public static Login leerArchivos() {
        ResourceBundle resourceBundle;
        resourceBundle = ResourceBundle.getBundle("usuario"); // nombre del archivo properties
        String usuario = resourceBundle.getString("usuario");
        String contrasena = resourceBundle.getString("contrasena");

        return new Login(usuario, contrasena);
    }

    static String fechaSistema = "";

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        Logger logger = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();
        try {
            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            switch (nivel) {
                case 1:
                    logger.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 2:
                    logger.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 3:
                    logger.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                default:
                    break;
            }

        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }

    private static void cargarFechaSistema() {
        Calendar cal1 = Calendar.getInstance();
        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int año = cal1.get(Calendar.YEAR);

        String diaN = (dia < 10) ? "0" + dia : "" + dia;
        String mesN = (mes < 10) ? "0" + mes : "" + mes;

        fechaSistema = año + "-" + mesN + "-" + diaN;
    }

    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {
        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;
    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {
        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();
    }
}
