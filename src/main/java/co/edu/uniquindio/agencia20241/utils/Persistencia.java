package co.edu.uniquindio.agencia20241.utils;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class Persistencia {
    private static final String BASE_PATH = "";

    public static Object cargarRecursoSerializadoXML(String nombreArchivo) throws IOException {
        XMLDecoder decodificadorXML = null;
        Object objetoXML = null;
        try {
            decodificadorXML = new XMLDecoder(new FileInputStream(BASE_PATH + nombreArchivo));
            objetoXML = decodificadorXML.readObject();
        } finally {
            if (decodificadorXML != null) {
                decodificadorXML.close();
            }
        }
        return objetoXML;
    }

    public static void salvarRecursoSerializadoXML(String nombreArchivo, Object objeto) throws IOException {
        XMLEncoder codificadorXML = null;
        try {
            codificadorXML = new XMLEncoder(new FileOutputStream(BASE_PATH + nombreArchivo));
            codificadorXML.writeObject(objeto);
        } finally {
            if (codificadorXML != null) {
                codificadorXML.close();
            }
        }
    }
}
