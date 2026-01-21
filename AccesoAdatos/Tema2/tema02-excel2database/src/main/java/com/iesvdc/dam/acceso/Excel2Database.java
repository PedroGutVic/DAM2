/**
 * Paquete principal de la aplicación
 */
package com.iesvdc.dam.acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import com.iesvdc.dam.acceso.conexion.Conexion;
import com.iesvdc.dam.acceso.conexion.Config;
import com.iesvdc.dam.acceso.excelutil.ExcelReader;

/**
 * Este programa genérico en java (proyecto Maven) es un ejercicio
 * simple que vuelca un libro Excel (xlsx) a una base de datos (MySQL)
 * y viceversa. El programa lee la configuración de la base de datos
 * de un fichero "properties" de Java y luego, con apache POI, leo
 * las hojas, el nombre de cada hoja será el nombre de las tablas,
 * la primera fila de cada hoja será el nombre de los atributos de
 * cada tabla (hoja) y para saber el tipo de dato, tendré que
 * preguntar a la segunda fila qué tipo de dato tiene.
 * 
 * Procesamos el fichero Excel y creamos una estructura de datos
 * con la información siguiente: La estructura principal es el libro,
 * que contiene una lista de tablas y cada tabla contiene tuplas
 * nombre del campo y tipo de dato.
 *
 */
/**
 * Clase principal que gestiona la conversión entre archivos Excel y bases de datos
 */
public class Excel2Database {
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos. Se espera el nombre de la base de datos en args[3]
     */
    public static void main(String[] args) {
        // Obtener el nombre de la base de datos desde los argumentos
        String db = args[3];
        // Cargar la configuración desde el archivo properties
        Properties props = Config.getProperties("config.properties");
        // Crear un scanner para leer la entrada del usuario
        Scanner sc = new Scanner(System.in);

        props.get("file");
        System.out.println(props.getProperty("file"));
        System.out.println(props.getProperty("action"));

        // Crear la base de datos

        

        try (Connection conexion = Conexion.getConnection(null)) {

            System.out.println("Que quieres hacer ?");

            System.out.println("1- Escribir en un excel apartir de una base de datos");
            System.out.println("2- Escribir en una base de datos desde excel");
            int opcion = sc.nextInt();
            sc.close();
            if(opcion==1){
            EscribirExcel(conexion);
            }else{
                EscribirBaseDeDatos(conexion , db);
            }

            
        } catch (Exception e) {
            System.err.println("No se pudo conectar.");
        }

        ExcelReader reader = new ExcelReader();

        reader.loadWorkBook(props.getProperty("file"), args[3]);
        System.out.println("Hojas en el libro: ");

        
    }

    //metodo para pasar de base de datos a excel
    public static void EscribirExcel(Connection conexion){
        try {
            Statement stmt = conexion.createStatement();
            String sql = "SHOW DATABASES";

            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                System.out.println(result.getString("Database"));
            }
        } catch (SQLException ex) {
        }
        System.out.println("Que base de datos quieres pasar a un excel? ");
        System.out.println("");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void EscribirBaseDeDatos(Connection conexion , String db){
        if (conexion != null) {
                System.out.println("Conectado correctamente.");
                
                String sql = "CREATE DATABASE IF NOT EXISTS " + db;
                String sql2 = "USE " + db;
                try {
                    Statement stmt = conexion.createStatement();
                    stmt.execute(sql);
                    System.out.println("Base de datos creada: " + db);
                    stmt.execute(sql2);
                    System.out.println("Base de datos seleccionada: " + db);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else
                System.out.println("Imposible conectar");
    }

}
