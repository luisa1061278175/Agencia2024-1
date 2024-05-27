package co.edu.uniquindio.agencia20241.utils;

import co.edu.uniquindio.agencia20241.model.Agencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Persistencia {
    private static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/AgenciaLog.txt";
    private static final String RUTA_ARCHIVO_MODELO_AGENCIA_XML = "src/main/resources/persistencia/model.xml";

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

    public static Agencia cargarRecursoAgenciaXML() {
        Agencia agencia = null;
        try {
            agencia = (Agencia) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agencia;
    }

    public static void guardarRecursoAgenciaXML(Agencia agencia) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_AGENCIA_XML, agencia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
