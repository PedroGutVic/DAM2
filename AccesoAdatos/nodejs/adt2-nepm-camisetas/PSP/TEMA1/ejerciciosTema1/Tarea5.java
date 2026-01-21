/**
 * 
 * @author Pedro Gutiérrez Vico
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class Tarea5 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner (System.in);
        
    //----------------------------------------------
    //               Entrada de datos 
    //----------------------------------------------
        if (args.length > 1){
            System.out.println("No se puede ingresar tantos parametros");
        }else if (args.length < 1){
            System.out.println("No se puede ingresar parametros vacios");
        }else if (args.length == 1){

            Process process =  Runtime.getRuntime().exec("ls -l "+args[0]);
            InputStream in = process.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));


            for (int i = 0 ; i < br.read() ; i++){
                System.out.println(br.readLine());
            }




        }

        
    //----------------------------------------------
    //                 Procesamiento 
    //----------------------------------------------

        
    //----------------------------------------------
    //              Salida de resultados 
    //----------------------------------------------
    }
}