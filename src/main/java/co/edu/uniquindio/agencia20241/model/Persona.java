package co.edu.uniquindio.agencia20241.model;

public class Persona {

    private String Nombre;
    private String Id;
    private String CorreoElectronico;

    public Persona(String nombre, String id, String correoElectronico) {
        Nombre = nombre;
        Id = id;
        CorreoElectronico = correoElectronico;
    }

    public Persona() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }
}
