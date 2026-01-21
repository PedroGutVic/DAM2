package ies.generador_nombres;

import java.text.Normalizer;
import java.util.Arrays;

public class App {

    public static final int CARAS = 6;
    public static int[] numeros;
    public static void main(String[] args) {
        numeros = new int[CARAS];
        Persona p = new Persona("Pepe", "Garcia", "pepe@incorreo.com", "12345", Genero.OTRO);
        System.out.println(p.toString());
        System.out.println("Cargando datos de persona");
        ListaPersona lp = new ListaPersona();

        try {
            lp.loadData("nombre_mujeres.txt", "nombre_hombres.txt", "apellidos.txt", "all_email.txt");
        } catch (Exception e) {
            System.out.println("No se han podido cargar los ficheros");
            e.printStackTrace();
        }
        try {
            
        } catch (Exception e) {
            System.out.println("No se han podido cargar los ficheros");
            e.printStackTrace();
        }

        Arrays.fill(numeros , 0);

       /* Aor(int i =0 ; i<CARAS;i++){
            numeros[i] = 0;
        }
            */ 

        //for(int i =0 ; i<100 ; i++){
        //    System.out.println("dado de 6 sale : " +  lp.dado(CARAS));
        // }

        for(int i =0 ; i<1000 ; i++){
            numeros[lp.dado(CARAS)]++;
         } 

         for (int i =0 ; i<CARAS ; i++ ) {
             System.out.printf("Han salido %d numeros de %d \n" , numeros[i] ,i);
         }

         String cadena ="Canción, alegría, barÇa y piña";
            System.out.println(Normalizer.normalize(cadena, Normalizer.Form.NFKD));
            //cadena = Normalizer.normalize(cadena, Normalizer.Form.NFKD);
        
        cadena = cadena.replaceAll("[Ç]", "c");
        cadena = cadena.replaceAll("[^\\p{ASCII}]", "");
            System.out.println(cadena);
    }
}
