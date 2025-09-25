package ies.generador_nombres;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.runtime.Static;

public class App {

    public static final int CARAS = 6;
    public static int[] numeros;
    public static void main(String[] args) {
        numeros = new int[CARAS];
        Persona p = new Persona("Pepe", "Garcia", "pepe@incorreo.com", "12345", Genero.OTRO);
        System.out.println(p.toString());
        System.out.println("Cargando datos de persona");
        ListaPersona lp = new ListaPersona();
        lp.loadData("/home/mio/Escritorio/dam2/AccesoAdatos/nombre_mujeres.txt", 
        "/home/mio/Escritorio/dam2/AccesoAdatos/nombre_hombres.txt", 
        "/home/mio/Escritorio/dam2/AccesoAdatos/apellidos.txt",
        "/home/mio/Escritorio/dam2/AccesoAdatos/all_email.txt");

        Arrays.fill(numeros , 0);

       /* for(int i =0 ; i<CARAS;i++){
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


         
    }
}
