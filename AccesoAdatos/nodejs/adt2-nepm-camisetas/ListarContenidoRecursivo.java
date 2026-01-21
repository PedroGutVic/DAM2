package com.iesvdc.acceso;

import java.io.File;

public class ListarContenidoRecursivo {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ListarContenidoRecursivo <ruta_de_la_carpeta>");
            System.exit(1);
        }

        String rutaCarpeta = args[0];
        listarContenidoRecursivo(new File(rutaCarpeta),0);
    }

// Al tener recursividad podemos añadir una variable nueva que se llame nivel la cual va a controlar las tabulaciones 

/*
*para las tabulaciones hacemos un for que me diga cuantas tabulaciones necesito sabiendo el nivel , 
*/

    public static void listarContenidoRecursivo(File carpeta , int nivel) {
        for(int i = 0 ; i<nivel;i++){
            System.out.print(" ");
        }

// Busqueda de ascii simbolo de un archivo
        if (carpeta.isDirectory()) {
            System.out.println("🗀: " + carpeta.getName());

            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    listarContenidoRecursivo(archivo,nivel+1);
                    
                }
            }
        } else if (carpeta.isFile()) {
            System.out.println("-"+carpeta.getName());
        }
    }
}
