package ies.generador_nombres;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import   javax.xml.bind.JAXBContext;

import javax.xml.bind.XmlAccessType;

@XMLRootElement(name="lista-personas")
@XMLAccessorType(XmlAccessType.FIELD)

public class ListaPersona {

    // atributo con la lista "actual" de personas
    // esta es la información que me interesa
    private List<Persona> personas;

    // atributos para el generador
    // es algo temporal para generar personas aleatorias
    private List<String> nombresMujer;
    private List<String> nombresHombre;
    private List<String> apellidos;
    private List<String> dominiosEmail;


    /**
     * Carga en una lista las líneas de un archivo
     * @param filename
     * @return
     */
    private List<String> loadStrings(String filename){
        List<String> lista = new ArrayList<String>();
        Path pathArchivo = Paths.get(filename);
        
        if (!Files.exists(pathArchivo)) {
            throw new IllegalArgumentException(
                "El archivo: "+ filename +" no existe.");
        }
        if (!Files.isReadable(pathArchivo)) {
            throw new IllegalArgumentException(
                "No tiene permiso para leer el archivo: " +
                filename
            );
        }

        return lista;
    }


    public void loadData(
        String nombreArchivoMujeres,
        String nombreArchivoHombres,
        String nombreArchivoApellidos,
        String nombreArchivoEmails
        ){
            this.nombresMujer = 
                loadStrings(nombreArchivoMujeres);
            this.nombresHombre = 
                loadStrings(nombreArchivoHombres);
            this.apellidos = 
                loadStrings(nombreArchivoApellidos);
            this.dominiosEmail = 
                loadStrings(nombreArchivoEmails);
    }

    public int dado(int caras){
        return (int) Math.floor(Math.random()*(caras));
    }

    public void generaPersonas(int nPersonas){
        if(this.dominiosEmail == null ||
            this.nombresHombre == null || 
            this.nombresMujer == null || 
            this.apellidos == null ) {
                throw new IllegalStateException(
                    "Generador de personas: primero "+
                    "hay que cargar las listas de datos");
            }

        if (nPersonas<1 || nPersonas> 1000000){
            throw new IllegalArgumentException("El número de personas debe estar entre 1 y un millón.");
        }
        this.personas = new ArrayList<Persona>();
        for (int i = 0; i < nPersonas; i++) {
            personas.add(generaPersona());
        }            
    }

    Persona generaPersona(){
        Persona p = new Persona();
        p.setApellidos(
            apellidos.get(dado(apellidos.size()-1))+
            " "+
            apellidos.get(dado(apellidos.size())-1));
        
        Genero[] generos = Genero.values();
        p.setGenero(generos[dado(generos.length)]);
        
        switch (p.getGenero()) {
            case MASCULINO:
                p.setNombre(
                    nombresHombre.get(
                        dado(nombresHombre.size())
                    ));
                break;
            case FEMENINO:
                p.setNombre(
                    nombresMujer.get(
                        dado(nombresMujer.size())
                    )
                );
                break;
            default:
                int numeroNombre = dado(
                    nombresMujer.size()+nombresHombre.size());                

                p.setNombre(
                    numeroNombre < nombresMujer.size() ?
                    nombresMujer.get(numeroNombre) :
                    nombresHombre.get(numeroNombre-nombresMujer.size())
                );
                break;
        }
        p.setEmail(generaEmail(p));

        p.setTelefono(
            String.valueOf(
                600000000+
                dado(200000000)));

        return p;
    }


    /**
         * Email un substring con 1 primera letras del nombre y 
         * tres primeras letras de los dos apellidos.
         * Todas las letras en minúscula. Se quitan los acentos y
         * los caracteres especiales se cambian, ejemplo:
         * ñ -> n
         * ç -> c
         * 
         */
    private String generaEmail(Persona p){
        String email;
        String nombre = p.getNombre();
                
        String[] apellidos = 
            Normalizer.normalize(
                p.getApellidos(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .split(" ");


        email = nombre.substring(0,1);
        for (String apellido : apellidos) {
            email+=apellido.substring(0, 3);
        }

        email+=dominiosEmail.get(
            dado(dominiosEmail.size()));
        
        return email;
    }



    public List<Persona> getPersonas() {
        return this.personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public List<String> getNombresMujer() {
        return this.nombresMujer;
    }

    public void setNombresMujer(List<String> nombresMujer) {
        this.nombresMujer = nombresMujer;
    }

    public List<String> getNombresHombre() {
        return this.nombresHombre;
    }

    public void setNombresHombre(List<String> nombresHombre) {
        this.nombresHombre = nombresHombre;
    }

    public List<String> getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(List<String> apellidos) {
        this.apellidos = apellidos;
    }

    public List<String> getDominiosEmail() {
        return this.dominiosEmail;
    }

    public void setDominiosEmail(List<String> dominiosEmail) {
        this.dominiosEmail = dominiosEmail;
    }

}

