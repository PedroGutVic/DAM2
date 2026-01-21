package ies.generador_nombres;


import java.util.regex.Pattern;

/**
 * 
 * nombre (lo vamos a generar del archivo)
apellidos (lo vamos a generar del archivo)
teléfono (regex + o no, mínimo 5 dígitos, máximo 12)
email (regex loquesea@loquesea.dos_letras_o_más)
empresa  (puede ser NULL)
genero (hombre, mujer, prefiero_no_decirlo)

953 123 456
+34 953 12 34 56
+1 800 213 1235
1 2214 000 9689
 
*/

public class Persona {
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private Genero genero;
    
    private final Pattern PATRON_EMAIL = 
        Pattern.compile(
            "^[A-Za-z0-9+_.-]" +
            "+@[A-Za-z0-9-]" +
            "+\\.[A-Za-z]{2,}$");

    private final Pattern PATRON_TELEFONO =
        Pattern.compile("^[+]?[0-9]{5,12}$");
        

    public Persona() {
    }

    public Persona(
        String nombre, String apellidos, 
        String email, String telefono, Genero genero) {

        if (!PATRON_EMAIL.matcher(email).matches()){
            throw new IllegalArgumentException(
                "Formato de email incorrecto: " + email
            );
        }

         if (!PATRON_TELEFONO.matcher(telefono).matches()){
            throw new IllegalArgumentException(
                "Formato de teléfono incorrecto: "+ telefono);
        }
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    


    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", genero='" + getGenero() + "'" +
            "}";
    }

}

