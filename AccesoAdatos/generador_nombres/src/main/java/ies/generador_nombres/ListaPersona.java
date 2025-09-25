package ies.generador_nombres;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ListaPersona {
    private List<Persona> persona;
    private List<String> nombreMujer;
    private List<String> nombreHombre;
    private List<String> apellidos;
    private List<String> emails;
    




    private List<String> loadList (String Filename){
        List<String> lista = new ArrayList<String>();

        Path pathArchivo = Paths.get(Filename);

        if(!Files.exists(pathArchivo)){
            throw new IllegalArgumentException("El archivo " + Filename + "no existe");

        }
        if (!Files.isReadable(pathArchivo)) {
            throw new  IllegalArgumentException("No tiene permiso para leer el archivo: " + Filename);
        }
        return lista ;
    }



    public void loadData(
        String ArchivonombreMujer,
        String ArchivonombreHombre,
        String Archivoapellidos,
        String Archivoemails
     ){
        this.nombreHombre = loadList(ArchivonombreHombre);
        this.nombreMujer = loadList(ArchivonombreMujer);
        this.apellidos = loadList(Archivoapellidos);
        this.emails = loadList(Archivoemails);
    }


    public int dado(int caras){
        return (int) Math.floor(Math.random()*(caras));
    }


    public int generadorPersona(int nPersonas) throws IllegalAccessException {
            if (this.emails == null || this.nombreHombre == null || this.nombreMujer == null || this.apellidos == null) {
                
                throw new IllegalArgumentException("Generador de personas : primero hay que cargar la lista de personas");
            }
            if (nPersonas<1 || nPersonas > 100000000){
                throw new IllegalAccessException("El numero debe estar comprendido entre 1 y 100000000");

            }
            this.persona = new ArrayList<Persona>();
            for(int i = 0; i<nPersonas ; i++){
                persona.add(generarPersona());
            }

        return 0;
        };
        Persona generarPersona(){
            Persona p = new Persona();
            p.setApellido(
                apellidos.get(dado(apellidos.size())) + " " + apellidos.get(dado(apellidos.size()))
            );
            Genero [] generos = Genero.values();

            p.setGenero(generos[dado(generos.length)]);
            
            switch (p.getGenero()) {
                case Genero.FEMININO:
                    p.setNombre(nombreMujer.get(dado(nombreMujer.size())));
                    break;
                case Genero.MASCULINO:
                    p.setNombre(nombreHombre.get(dado(nombreHombre.size())));
                    break;

                default:
                int numeroNombre = dado(nombreMujer.size() + nombreHombre.size());
                if (numeroNombre<nombreMujer.size()) {
                    nombreMujer.get(numeroNombre);                    
                }
                else{
                    nombreHombre.get(numeroNombre-nombreMujer.size());
                }
                p.setNombre(numeroNombre<nombreMujer.size() ? nombreMujer.get(numeroNombre) : nombreHombre.get(numeroNombre-nombreMujer.size()));   
                break;
            }



            /**
             * Email un substring con 3 primeras letras del nombre y tres primeras letras de los dos apellidos . Todas las letras en minuscula
             * Se quitan los acentos y los caracteres especiales se cambian, ejemplo
             * ñ->n
             * Ç-> c
             * 
             */
            return  p;
        }
}
