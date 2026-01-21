package com.iesvdc.acceso;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        System.out.println("Leer Ficheros de Propiedades");
        Properties p = getProperties("db.properties");
        Enumeration e = p.propertyNames();
        while (e.hasMoreElements()) {
            String nombre = (String) e.nextElement();
            System.out.println("Propiedad : "+ nombre + " valor : "+ p.getProperty(nombre));

        }
        try (Conexion )
    }
    static public Properties getProperties(String NombreArchivo) {
        Properties props = new Properties();
        try (FileInputStream is = new FileInputStream(NombreArchivo)) {
            props.load(is);
        } catch (Exception e) {
            System.out.println("Imposible cargar el archivo de Propiedades");
        }
        return props;
    }
}
