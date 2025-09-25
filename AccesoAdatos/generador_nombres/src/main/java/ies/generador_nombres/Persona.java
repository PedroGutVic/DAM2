package ies.generador_nombres;


import java.util.regex.Pattern;

public class Persona {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Genero genero;

    public Persona(String nombre, String apellido, String email, String telefono, Genero genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
    }

    public Persona() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Persona{" +
            "nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", email='" + email + '\'' +
            ", telefono='" + telefono + '\'' +
            ", genero=" + genero +
            '}';
    }


    private final Pattern PATRON_EMAIL= Pattern.compile(
        "^[A-Za-z0-9+_,-]" +
            "@[A-Za-z0-9-]"+
            "+.[a-zA-Z]{2,}$");

    
            private final Pattern PATRON_TELEFONO= Pattern.compile(
                "^[+]?[0-9]{5,12}$"
            );

    public static void main(String[] args) {

    }
    
}
